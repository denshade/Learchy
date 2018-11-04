package laboflieven.learchy;

import java.util.Arrays;

public class FrequentWordFilter implements WordFilter {
    @Override
    public boolean isWordImportant(final String word)
    {
        if (word.matches(".*\\d+.*")) return false;
        if (isFrequent(word)) return false;
        return word.length() > 1;
    }

    public boolean isFrequent(String word)
    {
        String[] words = new String[] {"ik" ,
                "hallo" ,
                "heeft",
                "hoe",
                "vanaf",
                "je" ,
                "het" ,
                "een" ,
                "goed" ,
                "van" ,
                "en" ,
                "wie",
                "we",
                "wij",
                "is" ,
                "voor" ,
                "dat" ,
                "met" ,
                "in" ,
                "zijn" ,
                "mooi" ,
                "jij" ,
                "de" ,
                "leuk" ,
                "niet" ,
                "mijn" ,
                "gaan" ,
                "lief" ,
                "nog" ,
                "ook",
                "the",
                "be",
                "to",
                "of",
                "and",
                "a",
                "in",
                "that",
                "have",
                "I",
                "it",
                "because",
                "for",
        "not",
                "via",
                "how",
        "on",
        "with",
        "he",
        "as",
        "you",
        "do",
        "at",
        "this",
        "but",
        "his",
        "by",
        "from",
        "they",
        "we",
        "say",
        "her",
        "she",
        "or",
        "will",
        "an",
        "my",
        "one",
        "all",
        "would",
        "there",
        "their",
        "what",
        "so",
        "up",
        "out",
        "if",
        "about",
        "who",
        "get",
        "which",
        "go",
        "when",
        "me",
        "make",
        "can",
        "like",
        "time",
        "no",
        "just",
        "him",
        "know",
        "take",
        "person",
        "into",
        "year",
        "your",
        "good",
        "some",
        "could",
        "them",
        "see",
        "other",
        "than",
        "then",
        "now",
        "look",
        "only",
        "come",
        "its",
        "over",
        "think",
        "also"};
        return Arrays.asList(words).contains(word);
    }
}