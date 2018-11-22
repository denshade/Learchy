package laboflieven.learchy.index;

import java.io.IOException;
import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

public class MysqlIndex implements IndexCreator {
    private Connection con;

    public MysqlIndex() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/learchy", "root", "");
    }

    @Override
    public void add(String URL, Collection<String> word) throws IOException, SQLException {
        try {
            Statement stmt = con.createStatement();
            stmt.execute("insert into url(url) values ('"+URL+"')");

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    public void close() throws IOException, SQLException {
        con.close();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        MysqlIndex my = new MysqlIndex();
        my.add("https://www.bimetra.be", new HashSet<>());
        my.close();
    }
}
