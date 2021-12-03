package com.example.boss;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    @FXML
    TextField tf_id;
    @FXML
    TextField tf_user;
    @FXML
    TextField tf_pass;
    @FXML
    TextField tf_fn;
    @FXML
    TextField tf_ln;

    Connection connect;

    public void add(ActionEvent event) {
        String query = "INSERT INTO customers (username, password, id, first_name, last_name) VALUES (?,?,?,?,?);";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, tf_user.getText());
            preparedStatement.setString(2, tf_pass.getText());
            preparedStatement.setString(3, tf_id.getText());
            preparedStatement.setString(4, tf_fn.getText());
            preparedStatement.setString(5, tf_ln.getText());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close(event);
    }

    public void close(ActionEvent event) {
        Stage stage = (Stage) tf_fn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/myschema", "root", "tnh210302");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
