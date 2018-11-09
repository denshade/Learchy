package laboflieven.learchy.webcrawler;

import java.io.IOException;
import java.util.Set;

public interface WebCrawler {
    void crawl(Set<String> pagesTodo, Set<String> visitedPages) throws IOException;
}
