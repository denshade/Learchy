package laboflieven.learchy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class RobotsProcessor
{
    public boolean isHostAllowed(URL host) throws IOException {
        URL robotUrl = new URL(host.getProtocol()+host.getHost() + "/robots.txt");
        robotUrl.getContent();
        return true;
    }

    public boolean isHostAllowed(String robotsContent, String candidateURL) throws IOException {
        if (robotsContent == null || "".equals(robotsContent) ) return true; //If no robots.txt then anything goes.
        boolean matches = true;
        String bestAllowRule = "";
        String bestDisallowRule = "";
        for (String keyVals : robotsContent.split("\n"))
        {
            if (keyVals.startsWith("#")) continue;//skip comments
            if (!keyVals.contains(":")) continue; //if it isn't a key value then skip it all together.
            String key =  keyVals.substring(0, keyVals.indexOf(":"));
            String value =  keyVals.substring(keyVals.indexOf(":") + 1).trim();
            if ("Disallow".equals(key)) {
                if (candidateURL.equals(value) || candidateURL.startsWith(value) && bestDisallowRule.length() < value.length()) {
                    bestDisallowRule = value;
                };
            }
            if ("Allow".equals(key)) {
                if (candidateURL.equals(value) || candidateURL.startsWith(value) && bestAllowRule.length() < value.length())  {
                    bestAllowRule = value;
                }
            }
        }
        if (bestAllowRule.length() > bestDisallowRule.length()) return true;
        return false;
    }
}
