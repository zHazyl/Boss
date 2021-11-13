package com.example.boss;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class BossController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    AnchorPane ap_parent;
    @FXML
    HBox hbox_parent;

    @FXML
    Pane choice1;
    @FXML
    Pane choicea;
    @FXML
    ImageView imgv_clerk;

    Image myimageA = new Image("a.png");

    @FXML
    Pane choice11;
    @FXML
    Pane choicea1;
    @FXML
    ImageView imgv_stat;

    Image myimageB = new Image("b.png");

    Image a0 = new Image("a0.png");
    Image b0 = new Image("b0.png");

    public void reset() {

        choicea.setStyle("-fx-background-color:#ffc107");
        choice1.setStyle("-fx-background-color:#ffc107");
        choicea1.setStyle("-fx-background-color:#ffc107");
        choice11.setStyle("-fx-background-color:#ffc107");
        imgv_clerk.setImage(a0);
        imgv_stat.setImage(b0);
    }

    public void choiceImgCleark() {
        reset();
        choicea.setStyle("-fx-background-color:white");
        choice1.setStyle("-fx-background-color:#455a64");
        imgv_clerk.setImage(myimageA);
    }


    public void choiceClerk(ActionEvent event) throws IOException {

        Node loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ContentA.fxml")));
        hbox_parent.getChildren().set(1, loader);

        choiceImgCleark();

    }

    public void choiceImgStat() {
        reset();
        choicea1.setStyle("-fx-background-color:white");
        choice11.setStyle("-fx-background-color:#455a64");
        imgv_stat.setImage(myimageB);

    }


    public void choiceStat(ActionEvent event) throws IOException {

        Node loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ContentB.fxml")));
        hbox_parent.getChildren().set(1, loader);

        choiceImgStat();

    }


    public void displayStage(ActionEvent event, String file_fxml) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(file_fxml)));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public void logout(ActionEvent event) throws IOException {

        displayStage(event,"login.fxml");

    }

}
