package laboflieven.learchy.index;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CSVIndexCreatorTest {

    @Test
    void add() throws IOException {
        File file = File.createTempFile("pre", "post");
        CSVIndexCreator creator = new CSVIndexCreator(file );
        creator.add("https://www.google.com", new HashSet<>());
        creator.close();
        assertTrue(file.exists());
    }
}