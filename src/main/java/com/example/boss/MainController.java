package com.example.boss;

import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MainController implements Initializable {

    @FXML
    private AnchorPane ac;
    private FXMLLoader fxml;

    @FXML
    TextField tf_username;

    @FXML
    PasswordField pf_password;

    @FXML
    Label label_fail_login;

    @FXML
    AnchorPane ac_parent;

    @FXML
    private TextField tf_addr;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_fn;

    @FXML
    private TextField tf_gender;

    @FXML
    private TextField tf_id;

    @FXML
    private TextField tf_ln;

    @FXML
    private TextField tf_pass;

    @FXML
    private TextField tf_position;

    @FXML
    private TextField tf_user;

    @FXML
    private TextField tf_phone;

    @FXML
    private AnchorPane infomarket;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        TranslateTransition t = new TranslateTransition(Duration.seconds(1), ac);
//        t.setToX(613);
//        t.play();
//        t.setOnFinished((e) -> {
//        });


        //changeAC("signIn.fxml");
        new Tada(ac).play();

        DatabaseConnection connectNow = new DatabaseConnection();
        connection = connectNow.getConnection();
//        try {
//            resultSet = connection.createStatement().executeQuery("select * from userCashier");
//
//            while (resultSet.next()) {
//                PersonelList.add(new Personel(
//                        resultSet.getString("id"),
//                        resultSet.getString("username"),
//                        resultSet.getString("password"),
//                        resultSet.getString("email"),
//                        resultSet.getDate("create_time"),
//                        resultSet.getString("first_name"),
//                        resultSet.getString("last_name"),
//                        resultSet.getString("phone"),
//                        resultSet.getString("position"),
//                        resultSet.getString("gender"),
//                        resultSet.getDate("born"),
//                        resultSet.getString("address"),
//                        resultSet.getDouble("salary")));
//
//            }
//        } catch (SQLException e) {
//
//        }
//
//        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
//        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
//        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
//        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));;
//        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
//
//        personelTable.setItems(PersonelList);

        refreshTable();
        infomarket.setVisible(false);
        //ac_add.setVisible(false);

    }

    SignInController signIn;

    public void changeAC(String filename) {
        try {
            fxml = new FXMLLoader(getClass().getResource(filename));
            signIn = fxml.getController();

            ac.getChildren().removeAll();
            ac.getChildren().setAll((Parent)fxml.load());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void close(ActionEvent e) {
        Stage stage = (Stage) ac.getScene().getWindow();
        stage.close();
    }

    public void login(ActionEvent event) throws IOException {

        if (validateLogin()) {
            label_fail_login.setText("");
            TranslateTransition t = new TranslateTransition(Duration.seconds(1), ac);
            t.setToX(-629);
            t.play();

            t.setOnFinished((e) -> {
                infomarket.setVisible(true);
                new FadeIn(infomarket).play();
                new FadeOut(pane_cover).play();
                pane_cover.setVisible(false);
                new Tada(infomarket).play();

            });



        } else {
            setLabel_fail_login();
            //new Tada(ac).play();
        }

    }

    public void setLabel_fail_login() {
        label_fail_login.setText("Username or password is not true!!");
        new Tada(label_fail_login).play();
    }

    public boolean validateLogin() {

        String verifyLogin = "SELECT count(1) from boss_user WHERE username = '" + tf_username.getText() + "' AND password ='" + pf_password.getText() + "'";

        boolean login = false;

        try {

            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {

                    login = true;

                } else {
                    login = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return login;
    }

    // table view

    @FXML
    private TableView<Personel> personelTable;
    @FXML
    private TableColumn<Personel, String> idCol;
    @FXML
    private TableColumn<Personel, String> firstNameCol;
    @FXML
    private TableColumn<Personel, String> lastNameCol;
    @FXML
    private TableColumn<Personel, String> positionCol;
    @FXML
    private TableColumn<Personel, String> genderCol;
    @FXML
    private TableColumn<Personel, String> emailCol;


    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Personel personel = null;

    ObservableList<Personel> PersonelList = FXCollections.observableArrayList();

    @FXML
    private void refreshTable() {

        try {
            PersonelList.clear();

            query = "SELECT * FROM userCashier";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PersonelList.add(new Personel(
                        resultSet.getString("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getDate("create_time"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone"),
                        resultSet.getString("position"),
                        resultSet.getString("gender"),
                        resultSet.getDate("born"),
                        resultSet.getString("address"),
                        resultSet.getDouble("salary")));
                personelTable.setItems(PersonelList);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {

        }

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));;
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        new FadeOut(ac_add).play();
        clear_tf();

    }

    public void clear_tf() {
        tf_id.clear();
        tf_user.clear();
        tf_pass.clear();
        tf_email.clear();
        tf_fn.clear();
        tf_ln.clear();
        tf_phone.clear();
        tf_gender.clear();
        tf_position.clear();
        born.setAccessibleText("");
        tf_addr.clear();
    }


    // ADD

    @FXML
    AnchorPane ac_add;
    @FXML
    Pane pane_cover;

    public void open_add(ActionEvent e) {
        tf_id.setDisable(false);
        tf_user.setDisable(false);
        clear_tf();
        title_label.setText("Add member");
        button_add.setText("add");
        button_update.setVisible(false);
        new FadeIn(ac_add).play();
    }

    @FXML
    DatePicker timejoin;
    @FXML
    DatePicker born;

    public void add(ActionEvent e) {

        String verifyAdd = "INSERT INTO userCashier (username, password, id, email, first_name, last_name, phone, position, gender, address, born) VALUES (?,?,?,?,?,?,?,?,?,?,?);";



        try {
            preparedStatement = connection.prepareStatement(verifyAdd);
            preparedStatement.setString(1, tf_user.getText());
            preparedStatement.setString(2, tf_pass.getText());
            preparedStatement.setString(3, tf_id.getText());
            preparedStatement.setString(4, tf_email.getText());
            preparedStatement.setString(5, tf_fn.getText());
            preparedStatement.setString(6, tf_ln.getText());
            preparedStatement.setString(7, tf_phone.getText());
            preparedStatement.setString(8, tf_position.getText());
            preparedStatement.setString(9, tf_gender.getText());
            preparedStatement.setString(10, tf_addr.getText());
            preparedStatement.setString(11, born.getValue().toString());
            preparedStatement.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }


        refreshTable();
    }

    public void deletePersonel(ActionEvent event) {

        Personel del = personelTable.getSelectionModel().getSelectedItem();
        if (del == null) {
            return;
        }
        String id = del.id;
        String delete = "DELETE FROM `jdbc-video`.`userCashier` WHERE id = " + id + ";";

        try {
            preparedStatement = connection.prepareStatement(delete);
            preparedStatement.execute();
        } catch (SQLException exception) {

        }

        refreshTable();
    }

    @FXML
    Label title_label;
    @FXML
    JFXButton button_add;
    @FXML
    JFXButton button_update;

    public void open_edit(ActionEvent event) {
        tf_id.setDisable(true);
        tf_user.setDisable(true);
        new FadeIn(ac_add).play();
        button_update.setVisible(true);
        title_label.setText("Edit member");
        Personel update = personelTable.getSelectionModel().getSelectedItem();
        if (update == null) {
            update = personelTable.getItems().get(0);
        }

        tf_user.setText(update.username);
        tf_pass.setText(update.password);
        tf_fn.setText(update.firstName);
        tf_ln.setText(update.lastName);
        tf_id.setText(update.id);
        tf_gender.setText(update.gender);
        tf_email.setText(update.email);
        tf_position.setText(update.position);
        born.setValue(update.getBorn().toLocalDate());
        tf_addr.setText(update.address);
        tf_phone.setText(update.phone);

    }

    public void update(ActionEvent event) {

        String verifyAdd = "UPDATE userCashier SET password = ?, email = ?, first_name = ?, last_name = ?, phone = ?, position = ?, gender = ?, address = ?, born = ? WHERE id = " + tf_id.getText() + ";";



        try {
            preparedStatement = connection.prepareStatement(verifyAdd);
            preparedStatement.setString(1, tf_pass.getText());
            preparedStatement.setString(2, tf_email.getText());
            preparedStatement.setString(3, tf_fn.getText());
            preparedStatement.setString(4, tf_ln.getText());
            preparedStatement.setString(5, tf_phone.getText());
            preparedStatement.setString(6, tf_position.getText());
            preparedStatement.setString(7, tf_gender.getText());
            preparedStatement.setString(8, tf_addr.getText());
            preparedStatement.setString(9, born.getValue().toString());
            preparedStatement.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }


        refreshTable();
    }

    public void logout(ActionEvent event) {
        label_fail_login.setText("");
        new FadeOut(ac_add).play();
        infomarket.setVisible(false);
        new FadeIn(pane_cover).play();
        pane_cover.setVisible(true);
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), ac);
        t.setToX(0);
        t.play();

        t.setOnFinished(event1 -> {
            new Tada(ac).play();
        });

        tf_username.clear();
        pf_password.clear();

    }

}
