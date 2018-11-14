package laboflieven.learchy.webcrawler;

import laboflieven.learchy.index.IndexCreator;
import laboflieven.learchy.robots.RobotsProcessor;
import laboflieven.learchy.urlprocessing.PageResults;
import laboflieven.learchy.urlprocessing.UrlProcessor;
import org.jsoup.HttpStatusException;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

public class ParallelHostThreadWebCrawler implements WebCrawler {
    private final UrlProcessor processor;
    private final IndexCreator creator;
    Map<String, Thread> threads = new HashMap<>();

    Logger logger = Logger.getLogger(ParallelHostThreadWebCrawler.class.getName());
    private int maxIterations;

    public ParallelHostThreadWebCrawler(final UrlProcessor processor, final IndexCreator creator, int maxIterations)
    {
        this.processor = processor;
        this.creator = creator;
        this.maxIterations = maxIterations;
    }

    @Override
    public void crawl(Set<String> pagesTodo, Set<String> visitedPages) throws IOException {
        ToVisitPagesForHost visitMap = new ToVisitPagesForHost();
        for (String pageTodo : pagesTodo)
        {
            visitMap.addUrl(new URL(pageTodo));
        }

        for (String host : visitMap.getHosts())
        {
            HostThread thread = new HostThread(processor, creator, host, visitedPages, visitMap);
            thread.start();
            threads.put(host, thread);
        }
        boolean hasThreadsRunning = true;
        while(hasThreadsRunning)
        {
            for (String host : visitMap.getHostsWithValues())
            {
                if (!(threads.keySet().contains(host) && threads.get(host).isAlive()))
                {
                    HostThread thread = new HostThread(processor, creator, host, visitedPages, visitMap);
                    thread.start();
                    threads.put(host, thread);
                }
            }
            //start threads if you can.
            try {

                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hasThreadsRunning = false;
            for (String host : threads.keySet() )
            {
                if (threads.get(host).isAlive())
                {
                    hasThreadsRunning = true;
                }
            }
        }

    }
}
