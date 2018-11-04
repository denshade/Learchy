package laboflieven.learchy;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PageResults
{
    Set<String> words;
    Set<String> urls;

    public PageResults()
    {
        words = new HashSet<>();
        urls = new HashSet<>();
    }
}
