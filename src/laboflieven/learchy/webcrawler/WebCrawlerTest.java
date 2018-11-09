package laboflieven.learchy.webcrawler;

import laboflieven.learchy.anchor.NameFilterAnchorDetector;
import laboflieven.learchy.contentfilter.FrequentWordFilter;
import laboflieven.learchy.index.NullIndexCreator;
import laboflieven.learchy.urlprocessing.JsoupUrlProcessor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

class WebCrawlerTest {
    @org.junit.jupiter.api.Test
    void crawl() throws MalformedURLException {
        WebCrawler crawler = new SequentialWebCrawler(new JsoupUrlProcessor(new FrequentWordFilter(), new NameFilterAnchorDetector()), new NullIndexCreator());
        Set<String> urls = new HashSet<>();
        urls.add("http://www.bimetra.be/");
        try {
            crawler.crawl(urls, new HashSet<>());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}