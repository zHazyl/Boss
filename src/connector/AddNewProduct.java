package connector;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AddNewProduct {

    private static Connection con = null;
    private static Statement stmt = null;
    
    public static void Add(String tableName, String name, String countPerUnit, String quantity, String importPrice,
            String exportPrice) {
        String query = generateAddProductQuery(tableName, name, countPerUnit, quantity, importPrice, exportPrice);
        try {
            con = ConnectionToDB.ConnectToDB();
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        } catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String generateAddProductQuery(String tableName, String name, String countPerUnit, String quantity,
            String importPrice,
            String exportPrice) {
        String query = "";
        query += "INSERT INTO " + tableName + " (name, count_per_unit, quantity, import_price, export_price) VALUES ('"
                + name + "','" + countPerUnit + "','" + quantity + "','" + importPrice + "','" + exportPrice + "');";
        return query;
    }
}
