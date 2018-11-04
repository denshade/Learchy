package laboflieven.learchy;

import java.io.*;
import java.util.Collection;
import java.util.List;

public class CSVIndexCreator implements IndexCreator {

    private Writer filewriter;

    public CSVIndexCreator(File filename) throws IOException {
        filewriter = new BufferedWriter(new FileWriter(filename));
    }
    @Override
    public void add(String url, Collection<String> word) throws IOException {
        StringBuilder words = new StringBuilder();
        for (String w : word)
        {
            if (w.length() > 0)
            {
                words.append(",").append(w);
            } else {
                words = new StringBuilder(w);
            }
        }
        filewriter.write(url + "," + words+ "\n");
    }

    @Override
    public void close() throws IOException {
        filewriter.close();
    }
}
