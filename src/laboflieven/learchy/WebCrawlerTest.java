package laboflieven.learchy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

class WebCrawlerTest {
    @org.junit.jupiter.api.Test
    void crawl() throws MalformedURLException {
        WebCrawler crawler = new WebCrawler(new JsoupUrlProcessor(new FrequentWordFilter()), new NullIndexCreator());
        Set<String> urls = new HashSet<>();
        urls.add("http://www.bimetra.be/");
        try {
            crawler.crawl(urls, new HashSet<>());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}