package laboflieven.learchy;

import laboflieven.learchy.anchor.AnchorDetector;
import laboflieven.learchy.anchor.NameFilterAnchorDetector;
import laboflieven.learchy.contentfilter.FrequentWordFilter;
import laboflieven.learchy.contentfilter.WordFilter;
import laboflieven.learchy.index.CSVIndexCreator;
import laboflieven.learchy.index.IndexCreator;
import laboflieven.learchy.index.NullIndexCreator;
import laboflieven.learchy.urlprocessing.AnchorJsoupUrlProcessor;
import laboflieven.learchy.urlprocessing.JsoupUrlProcessor;
import laboflieven.learchy.urlprocessing.UrlProcessor;
import laboflieven.learchy.webcrawler.ParallelWebCrawler;
import laboflieven.learchy.webcrawler.PoliteSequentialWebCrawler;
import laboflieven.learchy.webcrawler.SequentialWebCrawler;
import laboflieven.learchy.webcrawler.WebCrawler;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Learchy
{
    public static void main(String[] args) throws IOException {
        IndexCreator indexCreator = new NullIndexCreator();
        WordFilter wordFilter = new FrequentWordFilter();
        AnchorDetector anchorDetector = new NameFilterAnchorDetector();
        UrlProcessor processor = new AnchorJsoupUrlProcessor(wordFilter, anchorDetector);
        //WebCrawler crawler = new SequentialWebCrawler(processor, indexCreator);
        //WebCrawler crawler = new PoliteSequentialWebCrawler(processor, indexCreator,10000);
        WebCrawler crawler = new ParallelWebCrawler(processor, indexCreator,10000);

        Set<String> urls = new HashSet<>();
        urls.add("http://www.bimetra.be");
        try {
            crawler.crawl(urls, new HashSet<>());
        } catch (IOException e) {
            e.printStackTrace();
        }
        indexCreator.close();

    }
}
