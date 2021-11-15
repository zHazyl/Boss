package com.example.boss;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import de.jensd.fx.glyphs.fontawesome.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class LoginController {

    @FXML
    TextField tf_username;

    @FXML
    PasswordField pf_password;

    @FXML
    Label label_fail_login;

    @FXML
    AnchorPane parent;


    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setLabel_fail_login() {
        label_fail_login.setText("Username or password is not true!!");
    }

    public void displayStage(ActionEvent event, String file_fxml) throws IOException {

        Node loader = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(file_fxml)));
        loader.setLayoutX(280);
        loader.setLayoutY(148);
        parent.getChildren().set(0, loader);

    }

    public void login(ActionEvent event) throws IOException {

        if (validateLogin()) {
            displayStage(event, "boss.fxml");
        } else {
            setLabel_fail_login();
        }

    }

    public boolean validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

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

    @FXML
    HBox p;
    @FXML
    AnchorPane out;

    public void exit(ActionEvent event) {
        Stage stage = (Stage) out.getScene().getWindow();
        stage.close();
    }

}