package com.example.boss;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.ResourceBundle;

public class VipController implements Initializable {

    @FXML
    TableView<Customer> customerTable;
    @FXML
    private TableColumn<Personel, String> idCol;
    @FXML
    private TableColumn<Personel, String> firstNameCol;
    @FXML
    private TableColumn<Personel, String> lastNameCol;
    @FXML
    private TableColumn<Personel, String> rankCol;
    @FXML
    private TableColumn<Personel, String> pointCol;
    @FXML
    private TableColumn<Personel, String> phoneCol;

    Connection connect = null;

    ObservableList<Customer> vip = FXCollections.observableArrayList();


    public void loadVip() {

        String query = "SELECT * FROM customers";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connect.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                vip.add(new Customer(String.valueOf(resultSet.getInt("id")),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("rank"),
                        resultSet.getInt("reward_point"),
                        resultSet.getDouble("amount")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        customerTable.setItems(vip);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        rankCol.setCellValueFactory(new PropertyValueFactory<>("rank"));;
        pointCol.setCellValueFactory(new PropertyValueFactory<>("rewardPoint"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("password"));

    }

    public void refreshTable(ActionEvent event) {
        vip.clear();
        loadVip();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/myschema", "root", "tnh210302");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadVip();
    }

    public void openAdd(ActionEvent event) throws IOException {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addCustomer.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        String css = Objects.requireNonNull(Main.class.getResource("main.css")).toExternalForm();

        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(css);

        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();

    }

    public void delete(ActionEvent event) {

        Customer del = customerTable.getSelectionModel().getSelectedItem();
        if (del == null) {
            return;
        }
        String id = del.id;
        String delete = "DELETE FROM customers WHERE id = " + id + ";";

        try {
            PreparedStatement preparedStatement = connect.prepareStatement(delete);
            preparedStatement.execute();
        } catch (SQLException exception) {

        }

        refreshTable(event);
    }
}
