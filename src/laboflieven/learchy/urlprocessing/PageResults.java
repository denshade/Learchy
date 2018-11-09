package laboflieven.learchy.urlprocessing;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PageResults
{
    public Set<String> words;
    public Set<String> urls;

    public PageResults()
    {
        words = new HashSet<>();
        urls = new HashSet<>();
    }
}
