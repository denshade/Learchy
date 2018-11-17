package laboflieven.learchy.webcrawler;

import laboflieven.learchy.index.IndexCreator;
import laboflieven.learchy.robots.RobotsProcessor;
import laboflieven.learchy.urlprocessing.PageSummary;
import laboflieven.learchy.urlprocessing.PageSummaryProcessor;
import org.jsoup.HttpStatusException;

import java.net.URL;
import java.util.Set;
import java.util.logging.Logger;

public class PoliteSequentialWebCrawler implements WebCrawler {
    private final PageSummaryProcessor processor;
    private final IndexCreator creator;
    Logger logger = Logger.getLogger(ParallelStreamWebCrawler.class.getName());
    int maxIterations = 10000;


    public PoliteSequentialWebCrawler(final PageSummaryProcessor processor, final IndexCreator creator, int maxIterations)
    {
        this.processor = processor;
        this.creator = creator;
        this.maxIterations = maxIterations;
    }

    @Override
    public void crawl(Set<String> pagesTodo, Set<String> visitedPages)  {
        RobotsProcessor robotsProc = new RobotsProcessor();
        long sitesDone = 0;
        long badStatusses = 0;
        while (pagesTodo.size()>0 && sitesDone < maxIterations)
        {
            String page = pagesTodo.iterator().next();
            pagesTodo.remove(page);
            logger.info("Page: " + page);

            if (!visitedPages.contains(page)) {
                try {
                    sitesDone++;
                    if (!robotsProc.isHostAllowed(new URL(page),page))
                    {
                        continue;
                    }
                    Thread.sleep(1000);
                    PageSummary results = processor.getFromUrl(new URL(page));
                    pagesTodo.addAll(results.urls);
                    creator.add(page, results.words);
                    logger.info(results.words.toString());
                } catch (HttpStatusException hse)
                {
                    logger.info("Bad status " + hse);
                    badStatusses++;
                }catch (Exception ioe)
                {
                    logger.warning(ioe.getMessage());
                    logger.info("Skipped " + page);
                }
                visitedPages.add(page);
            } else {
                logger.info("Skipped " + page + " already processed");
            }
            logger.info("Status so far Bad " + badStatusses + "/" + sitesDone);

        }
    }
}
