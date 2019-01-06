package laboflieven.learchy;

import static org.junit.jupiter.api.Assertions.*;

class DecentStringTokenizerTest {

    @org.junit.jupiter.api.Test
    void tokenize() {
        assertEquals(6, DecentStringTokenizer.tokenize("This.Is.a list of words.").size());
    }
}