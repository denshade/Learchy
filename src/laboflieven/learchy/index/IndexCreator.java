package laboflieven.learchy.index;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface IndexCreator
{
    void add(String URL, Collection<String> word) throws IOException;
    void close() throws IOException;
}
