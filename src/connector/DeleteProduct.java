package connector;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteProduct {
    private static Connection con = null;
    private static Statement stmt = null;

    public static void delete(String tabName, String id) {
        String query = generateDeleteProductQuery(tabName, id);
        try {
            con = ConnectionToDB.ConnectToDB();
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        } catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String generateDeleteProductQuery(String tabName, String id) {
        String q = "";
        q += "DELETE FROM " + tabName + " WHERE id = '" + id + "';";
        return q;
    }
}
