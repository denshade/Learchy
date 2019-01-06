package laboflieven.learchy.index;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class MysqlIndex implements IndexCreator {
    private Connection con;

    public MysqlIndex() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/learchy?serverTimezone=UTC", "lieven", "lieven");
    }

    @Override
    public void add(String URL, Collection<String> words) throws IOException, SQLException {
        try {
            PreparedStatement wordStmt = con.prepareStatement("INSERT IGNORE into keywords(value) values (?)", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement urlstmt = con.prepareStatement("INSERT IGNORE into url(url) values (?)", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement keymapstmt = con.prepareStatement("INSERT IGNORE into keyword2url(idkeyword, url) values (?, ?)", Statement.RETURN_GENERATED_KEYS);

            urlstmt.setString(1, URL);
            ResultSet generatedKeys = getKeys(urlstmt);
            int urlId = getUrlId(URL, generatedKeys);
            for (String word : words)
            {
                wordStmt.setString(1, word);
                generatedKeys = getKeys(wordStmt);
                int wordId = getWordId(word, generatedKeys);
                keymapstmt.setInt(1, wordId);
                keymapstmt.setInt(2, urlId);
                keymapstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    private int getWordId(String word, ResultSet generatedKeys) throws SQLException {
        int wordId;
        if (generatedKeys.next())
        {
            wordId = generatedKeys.getInt(1);
        } else {
            PreparedStatement prepURL = con.prepareStatement("SELECT idkeywords from keywords where keywords.value=?");
            prepURL.setString(1, word);
            ResultSet set = prepURL.executeQuery();
            set.next();
            wordId = set.getInt(1);
        }
        return wordId;
    }
    private int getUrlId(String URL, ResultSet generatedKeys) throws SQLException {
        int urlId;
        if (generatedKeys.next())
        {
            urlId = generatedKeys.getInt(1);
        } else {
            PreparedStatement prepURL = con.prepareStatement("SELECT idurl from url where url.url=?");
            prepURL.setString(1, URL);
            ResultSet set = prepURL.executeQuery();
            set.next();
            urlId = set.getInt(1);
        }
        return urlId;
    }

    private ResultSet getKeys(PreparedStatement stmt) throws SQLException {
        stmt.execute();
        return stmt.getGeneratedKeys();
    }

    @Override
    public void close() throws IOException, SQLException {
        con.close();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        MysqlIndex my = new MysqlIndex();
        my.add("http://www.bimetra.be", Arrays.asList("Hi", "there"));
        my.close();
    }
}
