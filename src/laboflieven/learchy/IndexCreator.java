package laboflieven.learchy;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

interface IndexCreator
{
    void add(String URL, Collection<String> word) throws IOException;
    void close() throws IOException;
}
