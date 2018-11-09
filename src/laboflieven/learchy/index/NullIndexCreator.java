package laboflieven.learchy.index;

import laboflieven.learchy.index.IndexCreator;

import java.io.IOException;
import java.util.Collection;

public class NullIndexCreator implements IndexCreator
{
    @Override
    public void add(String URL, Collection<String> word) throws IOException {

    }

    @Override
    public void close() throws IOException {

    }
}
