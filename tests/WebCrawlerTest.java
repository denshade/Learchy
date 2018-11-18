import laboflieven.learchy.anchor.NameFilterAnchorDetector;
import laboflieven.learchy.contentfilter.FrequentWordFilter;
import laboflieven.learchy.index.NullIndexCreator;

import laboflieven.learchy.urlprocessing.JsoupPageSummaryProcessor;
import laboflieven.learchy.webcrawler.SequentialWebCrawler;
import laboflieven.learchy.webcrawler.WebCrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

class WebCrawlerTest {
    @org.junit.jupiter.api.Test
    void crawl() throws MalformedURLException {
        WebCrawler crawler = new SequentialWebCrawler(new JsoupPageSummaryProcessor(new FrequentWordFilter(), new NameFilterAnchorDetector()), new NullIndexCreator());
        Set<String> urls = new HashSet<>();
        urls.add("http://www.e-try.com/black.htm");
        try {
            crawler.crawl(urls, new HashSet<>());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}