package laboflieven.learchy.urlprocessing;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PageSummary
{
    public Set<String> words;
    public Set<String> urls;

    public PageSummary()
    {
        words = new HashSet<>();
        urls = new HashSet<>();
    }
}
