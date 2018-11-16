package laboflieven.learchy.webcrawler;

import laboflieven.learchy.index.IndexCreator;
import laboflieven.learchy.robots.RobotsProcessor;
import laboflieven.learchy.urlprocessing.PageResults;
import laboflieven.learchy.urlprocessing.UrlProcessor;

import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.logging.Logger;

public class HostThread extends Thread
{
    private final UrlProcessor processor;
    private final IndexCreator index;
    private final String host;
    private final Set<String> visitedPages;
    private ToVisitPagesForHost visitMap;
    private Long errors;
    Logger logger = Logger.getLogger(HostThread.class.getName());


    public HostThread(final UrlProcessor processor, final IndexCreator index, String host, Set<String> visitedPages, ToVisitPagesForHost visitMap, Long errors)
    {
        this.processor = processor;
        this.index = index;
        this.host = host;
        this.visitedPages = visitedPages;
        this.visitMap = visitMap;
        this.errors = errors;
    }

    public void run()
    {
        RobotsProcessor robotsProc = new RobotsProcessor();
        logger.info("Started running for " + host);
        String nextUrl = visitMap.popNextForHost(host);
        try {
        while(nextUrl != null) {
            if (!visitedPages.contains(nextUrl)) {
                logger.info("Loading " + nextUrl + " " + visitedPages.size());
                visitedPages.add(nextUrl);

                Thread.sleep(1000);
                PageResults results = processor.getFromUrl(new URL(nextUrl));
                for (String scannedResults : results.urls) {
                    if (!robotsProc.isHostAllowed(new URL(scannedResults), scannedResults)) {
                        continue;
                    }
                    URL scanned = new URL(scannedResults);
                    visitMap.addUrl(scanned);
                }
                index.add(nextUrl, results.words);
            }
            nextUrl = visitMap.popNextForHost(host);
        }
        } catch (IOException e) {
                logger.warning(e.getMessage());
                errors++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
