package main;

import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
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

public class SignInController implements Initializable {

    @FXML
    TextField tf_username;

    @FXML
    PasswordField pf_password;

    @FXML
    Label label_fail_login;

    @FXML
    AnchorPane ac;

    @FXML
    private JFXButton button_login;

    MainController main;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Tada(tf_username.getParent()).play();
    }

    public void login(ActionEvent event) throws IOException {

        if (validateLogin()) {
            label_fail_login.setText("");
            TranslateTransition t = new TranslateTransition(Duration.seconds(1), ac.getParent());
            t.setToX(-613);
            t.play();

            t.setOnFinished((e) -> {
                try {
                    FXMLLoader fxml = new FXMLLoader(getClass().getResource("../ui/view/infor.fxml"));
                    ac.getChildren().removeAll();
                    ac.getChildren().setAll((Parent) fxml.load());
                    new Tada(ac).play();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
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

    public void close(ActionEvent event) {
        Stage stage = (Stage) tf_username.getScene().getWindow();
        stage.close();
    }

}
