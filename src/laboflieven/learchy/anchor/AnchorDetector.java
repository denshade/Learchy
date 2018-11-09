package laboflieven.learchy.anchor;

import laboflieven.learchy.urlprocessing.PageResults;
import org.jsoup.nodes.Document;

import java.net.URL;

public interface AnchorDetector {
    void getAnchors(URL source, PageResults results, Document doc);
}
