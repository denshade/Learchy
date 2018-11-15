package laboflieven.learchy.webcrawler;

import java.net.URL;
import java.util.*;

public class ToVisitPagesForHost
{
    Map<String, Set<String>> hostVisitPages = new HashMap<>();

    public void addUrl(URL url)
    {
        String host = url.getHost();
        if (!hostVisitPages.containsKey(host))
        {
            hostVisitPages.put(host, new HashSet<>());
        }
        hostVisitPages.get(host).add(url.toString());
    }


    public String popNextForHost(String host)
    {
        if (!hostVisitPages.containsKey(host)) return null;
        if (hostVisitPages.get(host).size() == 0) return null;
        String url = hostVisitPages.get(host).iterator().next();
        hostVisitPages.get(host).remove(url);
        return url;
    }

    public Set<String> getHosts()
    {
        return hostVisitPages.keySet();
    }

    public Set<String> getHostsWithValues()
    {
        Set<String> hostsWithValues = new HashSet<>();
        for (String host : hostVisitPages.keySet())
        {
            if (hostVisitPages.get(host).size() > 0)
            {
                hostsWithValues.add(host);
            }
        }
        return hostsWithValues;
    }

    public void removeHost(String host) {
        hostVisitPages.remove(host);
    }
}
