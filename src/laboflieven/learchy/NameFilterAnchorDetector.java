package laboflieven.learchy;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class NameFilterAnchorDetector implements AnchorDetector {
    @Override
    public void getAnchors(URL source, PageResults results, Document doc) {
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
