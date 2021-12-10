package connector;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangeExportPrice {
    private static Connection con = null;
    private static Statement stmt = null;

    public static void change(BigDecimal newExportPrice, String id, String tableName) {
        String query = generateChangeExportPrice(newExportPrice, id, tableName);
        try {
            con = ConnectionToDB.ConnectToDB();
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String generateChangeExportPrice(BigDecimal newExportPrice, String id, String tableName) {
        return "UPDATE " + tableName + " SET export_price = " + newExportPrice.toString() + " WHERE id = " + id + ";"; 
    }
}
