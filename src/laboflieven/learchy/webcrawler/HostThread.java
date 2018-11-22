package laboflieven.learchy.webcrawler;

import laboflieven.learchy.index.IndexCreator;
import laboflieven.learchy.robots.RobotsProcessor;
import laboflieven.learchy.urlprocessing.PageSummary;
import laboflieven.learchy.urlprocessing.PageSummaryProcessor;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Logger;

public class HostThread extends Thread {
    private final PageSummaryProcessor processor;
    private final IndexCreator index;
    private final String host;
    private final Set<String> visitedPages;
    private ToVisitPagesForHost visitMap;
    public Set<String> errors;
    Logger logger = Logger.getLogger(HostThread.class.getName());


    public HostThread(final PageSummaryProcessor processor, final IndexCreator index, String host,
                      Set<String> visitedPages, ToVisitPagesForHost visitMap, Set<String> errors) {
        this.processor = processor;
        this.index = index;
        this.host = host;
        this.visitedPages = visitedPages;
        this.visitMap = visitMap;
        this.errors = errors;
    }

    public void run() {
        RobotsProcessor robotsProc = new RobotsProcessor();
        String nextUrl = visitMap.popNextForHost(host);
        logger.info("Started running for " + host + " " + nextUrl);
        while (nextUrl != null) {
            try {
                if (!visitedPages.contains(nextUrl)) {
                    visitedPages.add(nextUrl);

                    Thread.sleep(500);
                    PageSummary results = processor.getFromUrl(new URL(nextUrl));
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
            } catch (IOException e) {
                errors.add(nextUrl);
                logger.warning(e.getMessage() + " for " + nextUrl);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            nextUrl = visitMap.popNextForHost(host);
        }
        logger.info("FINISHED  for " + host);
    }

}
