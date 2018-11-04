package laboflieven.learchy;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class RobotsProcessorTest {
    @Test
    void isHostAllowed() throws IOException {
        RobotsProcessor proc = new RobotsProcessor();
        assertFalse(proc.isHostAllowed("User-agent: *\nDisallow: /no/", "https://laboflieven.org/no/1"));
        assertTrue(proc.isHostAllowed("", "anything"));
        assertTrue(proc.isHostAllowed("User-agent: *\nDisallow: /modules", "https://laboflieven.org/yes"));
        assertTrue(proc.isHostAllowed("User-agent: *\nAllow: /yes", "https://laboflieven.org/yes"));
        assertFalse(proc.isHostAllowed("User-agent: *\nDisallow: /no", "https://laboflieven.org/no"));
        assertTrue(proc.isHostAllowed("User-agent: *\nAllow: /no/1\nDisallow: /no", "https://laboflieven.org/no/1"));
        assertTrue(proc.isHostAllowed("User-agent: *\nDisallow: /no\nAllow: /no/1", "https://laboflieven.org/no/1"));
        assertTrue(proc.isHostAllowed("User-agent: *\n#Hi, my name is McClure\n\n \nDisallow: /no\nAllow: /no/1", "https://laboflieven.org/no/1"));
        assertTrue(proc.isHostAllowed("User-agent: *\n#Hi, my name is : McClure\n\n \nDisallow: /no\nAllow: /no/1", "https://laboflieven.org/no/1"));
        assertFalse(proc.isHostAllowed("User-agent: *\nDisallow: /*.json", "https://laboflieven.org/bla.json"));
        /// /*.json
    }
    @Test
    void isHostGoogle() throws IOException {
        RobotsProcessor proc = new RobotsProcessor();
        proc.isHostAllowed(new URL("http://www.google.com"), "http://www.google.com/about");
    }

}