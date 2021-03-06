package laboflieven.learchy;

import laboflieven.learchy.anchor.AnchorDetector;
import laboflieven.learchy.anchor.NameFilterAnchorDetector;
import laboflieven.learchy.contentfilter.FrequentWordFilter;
import laboflieven.learchy.contentfilter.WordFilter;
import laboflieven.learchy.index.IndexCreator;
import laboflieven.learchy.index.MysqlIndex;
import laboflieven.learchy.index.NullIndexCreator;
import laboflieven.learchy.urlprocessing.AnchorJsoupPageSummaryProcessor;
import laboflieven.learchy.urlprocessing.JsoupPageSummaryProcessor;
import laboflieven.learchy.urlprocessing.PageSummaryProcessor;
import laboflieven.learchy.webcrawler.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Learchy
{
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        IndexCreator indexCreator = new MysqlIndex();
        WordFilter wordFilter = new FrequentWordFilter();
        AnchorDetector anchorDetector = new NameFilterAnchorDetector();
        PageSummaryProcessor processor = new JsoupPageSummaryProcessor(wordFilter, anchorDetector);
        //WebCrawler crawler = new SequentialWebCrawler(processor, indexCreator);
        //WebCrawler crawler = new PoliteSequentialWebCrawler(processor, indexCreator,1000000);
        //WebCrawler crawler = new ParallelStreamWebCrawler(processor, indexCreator,10000);
        WebCrawler crawler = new ParallelHostThreadWebCrawler(processor, indexCreator,10000,100);

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
