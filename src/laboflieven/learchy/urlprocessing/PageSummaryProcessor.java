package laboflieven.learchy.urlprocessing;

import java.io.IOException;
import java.net.URL;

public interface PageSummaryProcessor {
    PageSummary getFromUrl(URL source) throws IOException;
}
