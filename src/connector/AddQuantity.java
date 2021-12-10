package connector;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddQuantity {
    private static Connection con = null;
    private static Statement stmt = null;

    public static void addQuantity(int quantity, String id, String tableName) {
        List<String> queries = generateAddQuantityQuery(quantity, id, tableName);
        try {
            con = ConnectionToDB.ConnectToDB();
            stmt = con.createStatement();
            stmt.executeUpdate(queries.get(0));
            stmt.executeUpdate(queries.get(1));
            stmt.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<String> generateAddQuantityQuery(int quantity, String id, String tableName) {
        List<String> queries = new ArrayList<>();
        queries.add("SET @quantity = (SELECT quantity FROM " + tableName + " WHERE id = " + id + ");");
        queries.add("UPDATE " + tableName + " SET quantity = (@quantity + " + quantity + ") WHERE id = " + id + ";");
        return queries;
    }
}
