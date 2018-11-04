package laboflieven.learchy;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

public class WebCrawler
{
    private final UrlProcessor processor;
    private final IndexCreator creator;

    public WebCrawler(final UrlProcessor processor, final IndexCreator creator)
    {
        this.processor = processor;
        this.creator = creator;
    }

    public void crawl(Set<String> pagesTodo, Set<String> visitedPages) throws IOException {
        RobotsProcessor robotsProc = new RobotsProcessor();
        long sitesDone = 0;
        while (pagesTodo.size()>0 && sitesDone < 1000)
        {
            String page = pagesTodo.iterator().next();
            pagesTodo.remove(page);
            System.out.println("Page: " + page);

            if (!visitedPages.contains(page)) {
                try {
                    if (!robotsProc.isHostAllowed(new URL(page),page))
                    {
                        continue;
                    }
                    PageResults results = processor.getFromUrl(new URL(page));
                    pagesTodo.addAll(results.urls);
                    creator.add(page, results.words);
                    System.out.println(results.words);
                    sitesDone++;
                } catch (IOException ioe)
                {
                    System.out.println("Skipped " + page);
                    //TODO warn.
                }
                visitedPages.add(page);
            } else {
                System.out.println("Skipped " + page + " already processed");
            }
        }
    }
}
