package laboflieven.learchy.urlprocessing;

import laboflieven.learchy.anchor.AnchorDetector;
import laboflieven.learchy.contentfilter.WordFilter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.StringTokenizer;

public class JsoupPageSummaryProcessor implements PageSummaryProcessor {
    private WordFilter wordFilter;
    private final AnchorDetector anchorDetector;

    public JsoupPageSummaryProcessor(WordFilter wordFilter, AnchorDetector anchorDetector)
    {
        this.wordFilter = wordFilter;
        this.anchorDetector = anchorDetector;
    }

    @Override
    public PageSummary getFromUrl(URL source) throws IOException {
        Document document = Jsoup.connect(source.toString()).timeout(0).get();
        PageSummary results = new PageSummary();
        results.urls = new HashSet<>();
        anchorDetector.getAnchors(source, results, document);
        if (document.body() == null)
            return results;//Problem with pages with frames.
        String text = document.body().text(); // "An example link"
        StringTokenizer tokenizer = new StringTokenizer(text, " \t\n\r\f,;_=!:<>/'\"()?[].");
        while(tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken().toLowerCase();

            if (wordFilter.isWordImportant(word)) {
                results.words.add(word);
            }
        }
        return results;
    }

}
