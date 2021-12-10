package connector;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import product.ObservableProduct;

public class LoadProducts {

    // the first int -> productTypes(table in database)
    // the second int -> the last product id (to jump to next table)
    private static int[] createNewIDIterator = { 0, 0 };

    private static Connection con = null;
    private static Statement stmt = null;
    private static ResultSet resultSet = null;
    private static String query = "";
    private final static String[] productColName = { "id", "name", "count_per_unit", "quantity", "import_price",
            "export_price", };

    public static ObservableList<ObservableProduct> load(String tableName) {
        ObservableList<ObservableProduct> products = FXCollections.observableArrayList();
        createNewIDIterator[0] = 0;
        createNewIDIterator[1] = 0;
        int[] loadColNumber = { 0, 1, 2, 3, 4, 5 };
        query = generateLoadProductsQuery(loadColNumber, tableName);
        try {
            con = ConnectionToDB.ConnectToDB();
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt(productColName[0]);
                String name = resultSet.getString(productColName[1]);
                int countPerUnit = resultSet.getInt(productColName[2]);
                int quantity = resultSet.getInt(productColName[3]);
                BigDecimal importPrice = resultSet.getBigDecimal(productColName[4]);
                BigDecimal exportPrice = resultSet.getBigDecimal(productColName[5]);

                products.add(
                        new ObservableProduct(id + "", name, countPerUnit + "", quantity + "", importPrice, exportPrice));
            }
            stmt.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

        return products;
    }

    private static String generateLoadProductsQuery(int[] loadColNumber, String tableName) {
        String q = "";
        String col = "";
        for (int i : loadColNumber) {
            col += (productColName[i] + ",");
        }
        col = col.substring(0, col.length() - 1);
        q += "SELECT " + col + " FROM " + tableName + ";";
        return q;
    }
}