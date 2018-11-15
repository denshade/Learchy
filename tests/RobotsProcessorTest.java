import laboflieven.learchy.robots.RobotsProcessor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class RobotsProcessorTest {
    @Test
    void isHostAllowed() throws IOException {
        RobotsProcessor proc = new RobotsProcessor();
        assertFalse(proc.isHostAllowed("User-agent: *\nDisallow: /no/", "https://laboflieven.org/no/1"));
        assertTrue(proc.isHostAllowed("User-agent: Blubber\nDisallow: /no/", "https://laboflieven.org/no/1"));
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
        assertTrue(proc.isHostAllowed("User-agent: *\n" +
                "Crawl-delay: 10\n" +
                "# Directories\n" +
                "Disallow: /includes/\n" +
                "Disallow: /misc/\n" +
                "Disallow: /modules/\n" +
                "Disallow: /profiles/\n" +
                "Disallow: /scripts/\n" +
                "Disallow: /themes/\n" +
                "# Files\n" +
                "Disallow: /CHANGELOG.txt\n" +
                "Disallow: /cron.php\n" +
                "Disallow: /INSTALL.mysql.txt\n" +
                "Disallow: /INSTALL.pgsql.txt\n" +
                "Disallow: /install.php\n" +
                "Disallow: /INSTALL.txt\n" +
                "Disallow: /LICENSE.txt\n" +
                "Disallow: /MAINTAINERS.txt\n" +
                "Disallow: /update.php\n" +
                "Disallow: /UPGRADE.txt\n" +
                "Disallow: /xmlrpc.php\n" +
                "# Paths (clean URLs)\n" +
                "Disallow: /admin/\n" +
                "Disallow: /comment/reply/\n" +
                "Disallow: /filter/tips/\n" +
                "Disallow: /logout/\n" +
                "Disallow: /node/add/\n" +
                "Disallow: /search/\n" +
                "Disallow: /user/register/\n" +
                "Disallow: /user/password/\n" +
                "Disallow: /user/login/\n" +
                "# Paths (no clean URLs)\n" +
                "Disallow: /?q=admin/\n" +
                "Disallow: /?q=comment/reply/\n" +
                "Disallow: /?q=filter/tips/\n" +
                "Disallow: /?q=logout/\n" +
                "Disallow: /?q=node/add/\n" +
                "Disallow: /?q=search/\n" +
                "Disallow: /?q=user/password/\n" +
                "Disallow: /?q=user/register/\n" +
                "Disallow: /?q=user/login/", "http://www.bimetra.be/"));

    }
    @Test
    void isHostGoogle() throws IOException {
        RobotsProcessor proc = new RobotsProcessor();
        proc.isHostAllowed(new URL("http://www.google.com"), "http://www.google.com/about");
    }

}