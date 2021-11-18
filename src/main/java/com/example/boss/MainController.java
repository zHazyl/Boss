package com.example.boss;

import animatefx.animation.BounceIn;
import animatefx.animation.Shake;
import animatefx.animation.Tada;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private AnchorPane ac;
    private FXMLLoader fxml;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        TranslateTransition t = new TranslateTransition(Duration.seconds(1), ac);
//        t.setToX(613);
//        t.play();
//        t.setOnFinished((e) -> {
//        });


        changeAC("signIn.fxml");
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

}
