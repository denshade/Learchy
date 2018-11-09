package laboflieven.learchy.webcrawler;

import laboflieven.learchy.index.IndexCreator;
import laboflieven.learchy.robots.RobotsProcessor;
import laboflieven.learchy.urlprocessing.PageResults;
import laboflieven.learchy.urlprocessing.UrlProcessor;

import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.logging.Logger;

public class SequentialWebCrawler implements WebCrawler {
    private final UrlProcessor processor;
    private final IndexCreator creator;
    Logger logger = Logger.getLogger(ParallelWebCrawler.class.getName());


    public SequentialWebCrawler(final UrlProcessor processor, final IndexCreator creator)
    {
        this.processor = processor;
        this.creator = creator;
    }

    @Override
    public void crawl(Set<String> pagesTodo, Set<String> visitedPages) throws IOException {
        RobotsProcessor robotsProc = new RobotsProcessor();
        long sitesDone = 0;
        while (pagesTodo.size()>0 && sitesDone < 1000)
        {
            String page = pagesTodo.iterator().next();
            pagesTodo.remove(page);
            logger.info("Page: " + page);

            if (!visitedPages.contains(page)) {
                try {
                    if (!robotsProc.isHostAllowed(new URL(page),page))
                    {
                        continue;
                    }
                    PageResults results = processor.getFromUrl(new URL(page));
                    pagesTodo.addAll(results.urls);
                    creator.add(page, results.words);
                    logger.info(results.words.toString());
                    sitesDone++;
                } catch (IOException ioe)
                {
                    logger.warning(ioe.getMessage());
                    logger.info("Skipped " + page);
                }
                visitedPages.add(page);
            } else {
                logger.info("Skipped " + page + " already processed");
            }
        }
    }
}
