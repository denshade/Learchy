package laboflieven.learchy.urlprocessing;

import laboflieven.learchy.anchor.AnchorDetector;
import laboflieven.learchy.contentfilter.WordFilter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.StringTokenizer;

public class AnchorJsoupUrlProcessor implements UrlProcessor {
    private WordFilter wordFilter;
    private final AnchorDetector anchorDetector;

    public AnchorJsoupUrlProcessor(WordFilter wordFilter, AnchorDetector anchorDetector)
    {
        this.wordFilter = wordFilter;
        this.anchorDetector = anchorDetector;
    }

    @Override
    public PageResults getFromUrl(URL source) throws IOException {
        Document document = Jsoup.connect(source.toString()).get();
        PageResults results = new PageResults();
        results.urls = new HashSet<>();
        anchorDetector.getAnchors(source, results, document);
        return results;
    }

}
