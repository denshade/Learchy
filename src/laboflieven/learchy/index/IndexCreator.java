package laboflieven.learchy.index;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface IndexCreator
{
    void add(String URL, Collection<String> word) throws IOException, SQLException;
    void close() throws IOException, SQLException;
}
