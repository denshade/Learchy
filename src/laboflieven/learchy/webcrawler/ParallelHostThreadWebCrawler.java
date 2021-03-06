package laboflieven.learchy.webcrawler;

import laboflieven.learchy.index.IndexCreator;
import laboflieven.learchy.urlprocessing.PageSummaryProcessor;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

public class ParallelHostThreadWebCrawler implements WebCrawler {
    private final PageSummaryProcessor processor;
    private final IndexCreator creator;
    Map<String, Thread> threads = new HashMap<>();

    Logger logger = Logger.getLogger(ParallelHostThreadWebCrawler.class.getName());
    private int maxIterations;
    private final int maxThreads;

    public ParallelHostThreadWebCrawler(final PageSummaryProcessor processor, final IndexCreator creator, int maxIterations, int maxThreads)
    {
        this.processor = processor;
        this.creator = creator;
        this.maxIterations = maxIterations;
        this.maxThreads = maxThreads;
    }

    @Override
    public void crawl(Set<String> pagesTodo, Set<String> visitedPages) throws IOException {
        long start = System.currentTimeMillis();
        ToVisitPagesForHost visitMap = new ToVisitPagesForHost();
        Set<String> errors = Collections.synchronizedSet(new HashSet<>());
        for (String pageTodo : pagesTodo)
        {
            visitMap.addUrl(new URL(pageTodo));
        }

        for (String host : visitMap.getHosts())
        {
            HostThread thread = new HostThread(processor, creator, host, visitedPages, visitMap, errors);
            thread.start();
            threads.put(host, thread);
        }
        int nrThreadsRunning = 1;
        while(nrThreadsRunning > 0 )
        {
            for (String host : visitMap.getHostsWithValues())
            {
                if (host.endsWith(".com"))
                {
                    visitMap.removeHost(host);
                }else {
                    if (!(threads.keySet().contains(host) && threads.get(host).isAlive()) && getNrThreadsRunning() < maxThreads)
                    {
                        HostThread thread = new HostThread(processor, creator, host, visitedPages, visitMap, errors);
                        thread.start();
                        threads.put(host, thread);
                    }
                }
            }
            //start threads if you can.
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nrThreadsRunning = getNrThreadsRunning();
            logger.info("Got " + nrThreadsRunning + " with Error/page: " + errors.size() + " / " + visitedPages.size() + " in " + (System.currentTimeMillis() - start)/1000);
        }

    }

    private int getNrThreadsRunning() {
        int nrThreadsRunning;
        nrThreadsRunning = 0;
        for (String host : threads.keySet() )
        {
            if (threads.get(host).isAlive())
            {
                nrThreadsRunning++;
            }
        }
        return nrThreadsRunning;
    }
}
