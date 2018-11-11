package laboflieven.learchy.robots;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RobotsProcessor
{
    public boolean isHostAllowed(URL host, String candidateURL) throws IOException {
        URL robotUrl = new URL(host.getProtocol()+ "://" + host.getHost() + "/robots.txt");
        if (robotUrl.getContent() instanceof InputStream)
        {
            String robots = new String(((InputStream) robotUrl.getContent()).readAllBytes());
            return isHostAllowed(robots, candidateURL);
        }
        return true;
    }

    public boolean isHostAllowed(String robotsContent, String candidateURL) throws IOException {
        if (robotsContent == null || "".equals(robotsContent) ) return true; //If no robots.txt then anything goes.
        String bestAllowRule = "";
        String bestDisallowRule = "";
        URL url = new URL(candidateURL);
        candidateURL = url.getPath();
        String currentUserAgent = "";
        for (String keyVals : robotsContent.split("\n"))
        {
            if (keyVals.startsWith("#")) continue;//skip comments
            if (!keyVals.contains(":")) continue; //if it isn't a key rule then skip it all together.
            String key =  keyVals.substring(0, keyVals.indexOf(":"));
            String rule =  keyVals.substring(keyVals.indexOf(":") + 1).trim();
            if (key.equals("User-agent")) {
                currentUserAgent = rule.trim();
            }
            if (rule.endsWith("/")) rule += "*";
            boolean isCurrentAgent = currentUserAgent.equals("*") || currentUserAgent.equals("");
            if ("Disallow".equals(key) && isCurrentAgent) {
                if (candidateURL.equals(rule) || matches(rule, candidateURL) && bestDisallowRule.length() < rule.length()) {
                    bestDisallowRule = rule;
                };
            }
            if ("Allow".equals(key)  && isCurrentAgent) {
                if (candidateURL.equals(rule) || matches(rule, candidateURL) && bestAllowRule.length() < rule.length())  {
                    bestAllowRule = rule;
                }
            }
        }
        if (bestAllowRule.length() >= bestDisallowRule.length()) return true;
        return false;
    }

    public boolean matches(final String rule, final String subject)
    {
        Pattern regex = Pattern.compile(rule.replaceAll("[*]", ".*"));
        Matcher m = regex.matcher(subject);
        return m.matches();
    }
}
