package laboflieven.learchy.urlprocessing;

import laboflieven.learchy.anchor.NameFilterAnchorDetector;
import laboflieven.learchy.contentfilter.FrequentWordFilter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class AnchorJsoupUrlProcessorTest {

    @Test
    void getFromUrl() throws IOException {
        AnchorJsoupUrlProcessor proc = new AnchorJsoupUrlProcessor(new FrequentWordFilter(), new NameFilterAnchorDetector());
        PageResults results = proc.getFromUrl(new URL("https://www.google.com"));
        assertNotNull(results.urls);
    }
}