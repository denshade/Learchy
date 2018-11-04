package laboflieven.learchy;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RobotsProcessorTest {
    @Test
    void isHostAllowed() throws IOException {
        RobotsProcessor proc = new RobotsProcessor();
        assertTrue(proc.isHostAllowed("", "anything"));
        assertTrue(proc.isHostAllowed("User-agent: *\nAllow: https://laboflieven.org/yes", "https://laboflieven.org/yes"));
        assertFalse(proc.isHostAllowed("User-agent: *\nDisallow: https://laboflieven.org/no", "https://laboflieven.org/no"));
        assertFalse(proc.isHostAllowed("User-agent: *\nDisallow: https://laboflieven.org/no/", "https://laboflieven.org/no/1"));
        assertTrue(proc.isHostAllowed("User-agent: *\nAllow: https://laboflieven.org/no/1\nDisallow: https://laboflieven.org/no", "https://laboflieven.org/no/1"));
        assertTrue(proc.isHostAllowed("User-agent: *\nDisallow: https://laboflieven.org/no\nAllow: https://laboflieven.org/no/1", "https://laboflieven.org/no/1"));
        assertTrue(proc.isHostAllowed("User-agent: *\n#Hi, my name is McClure\n\n \nDisallow: https://laboflieven.org/no\nAllow: https://laboflieven.org/no/1", "https://laboflieven.org/no/1"));
        assertTrue(proc.isHostAllowed("User-agent: *\n#Hi, my name is : McClure\n\n \nDisallow: https://laboflieven.org/no\nAllow: https://laboflieven.org/no/1", "https://laboflieven.org/no/1"));

    }

}