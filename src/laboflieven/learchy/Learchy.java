package laboflieven.learchy;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Learchy
{
    public static void main(String[] args) throws IOException {
        CSVIndexCreator indexCreator = new CSVIndexCreator(new File("c:\\tmp\\files.csv"));
        WebCrawler crawler = new WebCrawler(new JsoupUrlProcessor(new FrequentWordFilter()), indexCreator);
        Set<String> urls = new HashSet<>();
        urls.add("https://www.reddit.com/");
        try {
            crawler.crawl(urls, new HashSet<>());
        } catch (IOException e) {
            e.printStackTrace();
        }
        indexCreator.close();

    }
}
