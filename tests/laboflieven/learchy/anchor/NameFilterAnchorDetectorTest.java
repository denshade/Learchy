package laboflieven.learchy.anchor;

import laboflieven.learchy.urlprocessing.PageResults;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class NameFilterAnchorDetectorTest {

    @Test
    void getAnchors() throws MalformedURLException {
        NameFilterAnchorDetector det = new NameFilterAnchorDetector();
        Document document = Jsoup.parse("<html><a href=\"hello.html\"></a><a href=\"tel:012\"></a></html>");
        PageResults results = new PageResults();
        det.getAnchors(new URL("https://laboflieven.org"), results, document);
        assertTrue(results.urls.contains("https://laboflieven.org/hello.html"));
        assertFalse(results.urls.contains("https://laboflieven.org/tel:012"));
    }
}