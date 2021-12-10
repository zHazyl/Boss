package main;

import com.mysql.cj.jdbc.ha.BestResponseTimeBalanceStrategy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SalaryController implements Initializable {

    @FXML
    TableView<ListSalary> salaryTable;
    @FXML
    TableColumn<ListSalary, String> id;
    @FXML
    TableColumn<ListSalary, String> salary;

    double totalSalary;

    ObservableList<ListSalary> listSalaries = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String query = "select * from salaryPersonel;";
        DatabaseConnection d = new DatabaseConnection();
        Connection connection = d.getConnection();
        int m = LocalDate.now().getMonthValue();
        totalSalary = 0;

        try {
            PreparedStatement preparedStatement =  connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listSalaries.add(new ListSalary(String.valueOf(resultSet.getString("id")),
                        resultSet.getDouble(String.valueOf(m))));
                totalSalary += resultSet.getDouble(String.valueOf(m));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        salaryTable.setItems(listSalaries);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));

    }

    public void update(ActionEvent event) {
        DatabaseConnection d = new DatabaseConnection();
        Connection connection = d.getConnection();
        int m = LocalDate.now().getMonthValue();

        String query = "update month set personnelSalary = " + String.valueOf(totalSalary) + " where name = " + String.valueOf(m) + ";";

        try {
            PreparedStatement preparedStatement =  connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Stage s = (Stage) salaryTable.getScene().getWindow();
        s.close();
    }
}
