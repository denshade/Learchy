package laboflieven.learchy;

import laboflieven.learchy.anchor.AnchorDetector;
import laboflieven.learchy.anchor.NameFilterAnchorDetector;
import laboflieven.learchy.contentfilter.FrequentWordFilter;
import laboflieven.learchy.contentfilter.WordFilter;
import laboflieven.learchy.index.CSVIndexCreator;
import laboflieven.learchy.index.IndexCreator;
import laboflieven.learchy.urlprocessing.JsoupUrlProcessor;
import laboflieven.learchy.urlprocessing.UrlProcessor;
import laboflieven.learchy.webcrawler.SequentialWebCrawler;
import laboflieven.learchy.webcrawler.WebCrawler;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Learchy
{
    public static void main(String[] args) throws IOException {
        IndexCreator indexCreator = new CSVIndexCreator(new File("c:\\tmp\\files.csv"));
        WordFilter wordFilter = new FrequentWordFilter();
        AnchorDetector anchorDetector = new NameFilterAnchorDetector();
        UrlProcessor processor = new JsoupUrlProcessor(wordFilter, anchorDetector);
        WebCrawler crawler = new SequentialWebCrawler(processor, indexCreator);
        Set<String> urls = new HashSet<>();
        urls.add("http://www.bimetra.be/");
        try {
            crawler.crawl(urls, new HashSet<>());
        } catch (IOException e) {
            e.printStackTrace();
        }
        indexCreator.close();

    }
}
