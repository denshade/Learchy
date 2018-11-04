package laboflieven.learchy;

import java.io.IOException;
import java.net.URL;

public interface UrlProcessor {
    PageResults getFromUrl(URL source) throws IOException;
}
