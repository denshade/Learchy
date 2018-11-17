package laboflieven.learchy.anchor;

import laboflieven.learchy.urlprocessing.PageSummary;
import org.jsoup.nodes.Document;

import java.net.URL;

public interface AnchorDetector {
    void getAnchors(URL source, PageSummary results, Document doc);
}
