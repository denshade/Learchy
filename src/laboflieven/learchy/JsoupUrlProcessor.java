package laboflieven.learchy;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.StringTokenizer;

public class JsoupUrlProcessor implements UrlProcessor {
    private WordFilter wordFilter;

    public JsoupUrlProcessor(WordFilter wordFilter)
    {
        this.wordFilter = wordFilter;
    }

    @Override
    public  PageResults getFromUrl(URL source) throws IOException {
        if (!(source.getContent() instanceof InputStream))
        {
            return new PageResults();
        }
        InputStream inputStream = ((InputStream)source.getContent());
        String html = new String(inputStream.readAllBytes(), "UTF8");
        PageResults results = new PageResults();
        results.urls = new HashSet<>();
        Document doc = Jsoup.parse(html);
        getAnchors(source, results, doc);
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

    private void getAnchors(URL source, PageResults results, Document doc) {
        String baseUrl = source.getProtocol()+"://"+source.getHost();
        for (Element el : doc.getElementsByTag("a"))
        {
            String href = el.attr("href");
            if(href.contains("twitter") || href.contains("github"))
                continue;
            if (href.contains("mailto:"))
            {
                continue;
            }
            if (href.contains("?"))
            {
                continue;
            }
            if (href.endsWith(".pdf") || href.endsWith(".docx"))
            {
                continue;
            }

            if (href.contains("#"))
            {
                continue;
            }
            if (href.startsWith("/"))
            {
                href = baseUrl + href;
            }
            else if (!href.startsWith("http")) {
                href = source.toString() + "/" + href;
            }
            href = StringUtils.strip(href, "/");
            results.urls.add(href);
        }
    }
}
