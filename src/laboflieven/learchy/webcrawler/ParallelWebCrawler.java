package laboflieven.learchy.webcrawler;

import laboflieven.learchy.index.IndexCreator;
import laboflieven.learchy.robots.RobotsProcessor;
import laboflieven.learchy.urlprocessing.PageResults;
import laboflieven.learchy.urlprocessing.UrlProcessor;
import org.jsoup.HttpStatusException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class ParallelWebCrawler implements WebCrawler {
    private final UrlProcessor processor;
    private final IndexCreator creator;
    Logger logger = Logger.getLogger(ParallelWebCrawler.class.getName());

    public ParallelWebCrawler(final UrlProcessor processor, final IndexCreator creator)
    {
        this.processor = processor;
        this.creator = creator;
    }

    @Override
    public void crawl(Set<String> pagesTodo, Set<String> visitedPages) throws IOException {
        RobotsProcessor robotsProc = new RobotsProcessor();
        long sitesDone = 0;
        List<String> badSites = new ArrayList<>();
        while (pagesTodo.size()>0 && sitesDone < 1000)
        {
            long startMilis = System.currentTimeMillis();
            String[] newArray = Arrays.copyOfRange(pagesTodo.toArray(new String[100]), 0, 100);
            List<String> newList = Arrays.asList(newArray);
            pagesTodo.removeAll(newList);
            newList.parallelStream().forEach(page-> {
                if (page == null) return;
                logger.info("Page: " + page);
                if (!visitedPages.contains(page)) {
                    try {
                        if (robotsProc.isHostAllowed(new URL(page),page))
                        {
                            PageResults results = processor.getFromUrl(new URL(page));
                            pagesTodo.addAll(results.urls);
                            creator.add(page, results.words);
                            logger.info(results.words.toString());
                        }
                    } catch (HttpStatusException hse)
                    {
                        logger.info("Bad status " + hse);
                        badSites.add(page);
                    }catch (IOException ioe)
                    {
                        logger.warning(ioe.getMessage());
                        logger.info("Skipped " + page);
                    }
                    visitedPages.add(page);
                } else {
                    logger.info("Skipped " + page + " already processed");
                }

            });
            sitesDone += 100;
            long currentTime = System.currentTimeMillis();
            double msPer100 = (double)(currentTime - startMilis) / 100;
            double sitesPerSecond = (double)100 * 1000/(double)(currentTime - startMilis);
            logger.info(sitesDone + " sites done in " + (currentTime - startMilis) + " ms");
            logger.info(sitesPerSecond + " ms Per site" + (currentTime - startMilis) + " ms");
            logger.info("Bad sites so far" + badSites.size() + "/" + sitesDone);

        }
        logger.info("The bad sites were " + badSites);
    }
}
