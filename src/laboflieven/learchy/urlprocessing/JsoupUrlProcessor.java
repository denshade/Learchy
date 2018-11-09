package laboflieven.learchy.urlprocessing;

import laboflieven.learchy.anchor.AnchorDetector;
import laboflieven.learchy.contentfilter.WordFilter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.StringTokenizer;

public class JsoupUrlProcessor implements UrlProcessor {
    private WordFilter wordFilter;
    private final AnchorDetector anchorDetector;

    public JsoupUrlProcessor(WordFilter wordFilter, AnchorDetector anchorDetector)
    {
        this.wordFilter = wordFilter;
        this.anchorDetector = anchorDetector;
    }

    @Override
    public PageResults getFromUrl(URL source) throws IOException {
        if (!(source.getContent() instanceof InputStream))
        {
            return new PageResults();
        }
        InputStream inputStream = ((InputStream)source.getContent());
        String html = new String(inputStream.readAllBytes(), "UTF8");
        PageResults results = new PageResults();
        results.urls = new HashSet<>();
        Document doc = Jsoup.parse(html);
        anchorDetector.getAnchors(source, results, doc);
        if (doc.body() == null)
            return results;//Problem with pages with frames.
        String text = doc.body().text(); // "An example link"
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
