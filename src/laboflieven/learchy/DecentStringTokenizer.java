package laboflieven.learchy;

import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class DecentStringTokenizer
{
    public static List<String> tokenize(String source)
    {
        String[] matches = Pattern.compile("\\w+")
                .matcher(source)
                .results()
                .map(MatchResult::group)
                .toArray(String[]::new);
        return Arrays.asList(matches);
    }
}
