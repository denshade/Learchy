package laboflieven.learchy.anchor;

import laboflieven.learchy.urlprocessing.PageResults;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class NameFilterAnchorDetector implements AnchorDetector {

    private List<String> toSkip;

    public static String[] TYPICALSKIP = new String[]{
            "twitter",
            "github",
            "javascript:",
            "tel:",
            "mailto:"
    };

    public NameFilterAnchorDetector()
    {
        this.toSkip = Arrays.asList(NameFilterAnchorDetector.TYPICALSKIP);
    }

    public NameFilterAnchorDetector(List<String> toSkip)
    {
        this.toSkip = toSkip;
    }



    @Override
    public void getAnchors(URL source, PageResults results, Document doc) {
        String baseUrl = source.getProtocol()+"://"+source.getHost();
        here : for (Element el : doc.getElementsByTag("a"))
        {
            String href = el.attr("href").toLowerCase();
            for (String skippable : toSkip)
            {
                if (href.contains(skippable))
                {
                    continue here;
                }
            }

            if (href.contains("?"))
            {
                continue;
            }
            if (href.endsWith(".pdf") || href.endsWith(".docx") || href.endsWith(".png")
                     || href.endsWith(".jpg") || href.endsWith("doc") || href.endsWith("gif") || href.endsWith("ppt") || href.endsWith("pptx")
            || href.endsWith(".zip") )
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
