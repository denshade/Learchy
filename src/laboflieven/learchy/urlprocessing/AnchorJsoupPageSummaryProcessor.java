package laboflieven.learchy.urlprocessing;

import laboflieven.learchy.anchor.AnchorDetector;
import laboflieven.learchy.contentfilter.WordFilter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;

public class AnchorJsoupPageSummaryProcessor implements PageSummaryProcessor {
    private WordFilter wordFilter;
    private final AnchorDetector anchorDetector;

    public AnchorJsoupPageSummaryProcessor(WordFilter wordFilter, AnchorDetector anchorDetector)
    {
        this.wordFilter = wordFilter;
        this.anchorDetector = anchorDetector;
    }

    @Override
    public PageSummary getFromUrl(URL source) throws IOException {
        Document document = Jsoup.connect(source.toString()).timeout(10000).get();
        PageSummary results = new PageSummary();
        results.urls = new HashSet<>();
        anchorDetector.getAnchors(source, results, document);
        return results;
    }

}
