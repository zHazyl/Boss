package main;

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
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;
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

    @FXML
    private AnchorPane ac_vip;

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
        ac_work.setVisible(false);
        //ac_add.setVisible(false);
        loadProgress();


        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../ui/view/vip.fxml"));
        try {
            ac_vip.getChildren().add(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    AnchorPane ac_work;

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
                choiceMember();

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
                        resultSet.getDouble("salary"),
                        resultSet.getInt("days_off"),
                        resultSet.getInt("days_late"),
                        resultSet.getDouble("bonus")));
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
        ac_work.setVisible(false);
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

        String verifyAdd = "INSERT INTO userCashier (username, password, id, email, first_name, last_name, phone, position, gender, address, born, salary) VALUES (?,?,?,?,?,?,?,?,?,?,?, 5000000);";
        String work = "INSERT INTO salaryPersonel (id) VALUES (" + tf_id.getText() + ");";


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
            preparedStatement = connection.prepareStatement(work);
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
        String work = "DELETE FROM `jdbc-video`.`salaryPersonel` WHERE id = " + id + ";";

        try {
            preparedStatement = connection.prepareStatement(delete);
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement(work);
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
        ac_work.setVisible(false);
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
        ac_work.setVisible(false);
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
        closeManagement();

    }

    @FXML
    Pane memberPane;
    @FXML
    Pane calculatorPane;

    public void choiceMember() {
        memberPane.setStyle("-fx-background-color:#ffc107;-fx-background-radius: 5px 5px 0px 0px;");
        calculatorPane.setStyle("-fx-background-color:#455a64;-fx-background-radius: 5px 5px 0px 0px;");
        vip_pane.setStyle("-fx-background-color:#455a64;-fx-background-radius: 5px 5px 0px 0px;");
        infomarket.setVisible(true);
        new FadeIn(infomarket).play();
        new FadeOut(pane_cover).play();
        pane_cover.setVisible(false);
        new Tada(infomarket).play();
    }

    public void choiceCalculator() {
        calculatorPane.setStyle("-fx-background-color:#ffc107;-fx-background-radius: 5px 5px 0px 0px;");
        memberPane.setStyle("-fx-background-color:#455a64;-fx-background-radius: 5px 5px 0px 0px;");
        vip_pane.setStyle("-fx-background-color:#455a64;-fx-background-radius: 5px 5px 0px 0px;");
        infomarket.setVisible(false);

    }

    @FXML
    JFXButton memberButton;
    @FXML
    JFXButton calculatorButton;
    @FXML
    JFXButton vip_button;
    @FXML
    Pane vip_pane;

    public void handleChoice(ActionEvent event) {
        if (event.getSource() == memberButton) {
            choiceMember();
            closeManagement();
            closeManagement();
            ac_vip.setVisible(false);
        } else if (event.getSource() == calculatorButton) {
            choiceCalculator();
            openManagement();
            ac_vip.setVisible(false);
            updateProfit();
        } else if (event.getSource() == vip_button) {
            memberPane.setStyle("-fx-background-color:#455a64;-fx-background-radius: 5px 5px 0px 0px;");
            calculatorPane.setStyle("-fx-background-color:#455a64;-fx-background-radius: 5px 5px 0px 0px;");
            vip_pane.setStyle("-fx-background-color:#ffc107;-fx-background-radius: 5px 5px 0px 0px;");
            ac_vip.setVisible(true);

        }
    }

    @FXML
    TextField tf_salary;
    @FXML
    TextField tf_off;
    @FXML
    TextField tf_late;
    @FXML
    TextField tf_bonus;

    public void open_work() {
        lb_total.setText("");
        ac_work.setVisible(true);
        new FadeIn(ac_work).play();
        Personel update = personelTable.getSelectionModel().getSelectedItem();
        if (update == null) {
            update = personelTable.getItems().get(0);
        }
        double a;

        tf_salary.setText(String.valueOf(update.salary));
        tf_off.setText(String.valueOf(update.dayOff));
        tf_late.setText(String.valueOf(update.dayLate));
        tf_bonus.setText(String.valueOf(update.bonus));

    }

    public void updateWork(ActionEvent event) {
        Personel work = personelTable.getSelectionModel().getSelectedItem();
        if (work == null) {
            work = personelTable.getItems().get(0);
        }
        String id = work.id;

        String month = Integer.toString(LocalDate.now().getMonthValue());
        String totalSalary = Double.toString(totalSalary());
        String total = " UPDATE salaryPersonel SET `" + month + "` = " + totalSalary + " WHERE id = " + id + ";";
        //System.out.println(total);
        String updateSalary = "UPDATE userCashier SET salary = " +
                tf_salary.getText() + ", days_off = " +
                tf_off.getText() + ", days_late = " +
                tf_late.getText() + ", bonus = " +
                tf_bonus.getText() + " WHERE id = " + id + ";";


        try {
            preparedStatement = connection.prepareStatement(total);
            preparedStatement.execute();
            preparedStatement.close();
            preparedStatement = connection.prepareStatement(updateSalary);
            preparedStatement.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }



    }

    public double totalSalary() {
        double salary = Double.parseDouble(tf_salary.getText());
        int off = Integer.parseInt(tf_off.getText());
        int late = Integer.parseInt(tf_late.getText());
        double bonus = Double.parseDouble(tf_bonus.getText());
        return salary - off * 200000 - late * 30000 + bonus;
    }

    @FXML
    Label lb_total;

    public void totalCal(ActionEvent event) {
        lb_total.setText(String.valueOf(totalSalary()));
    }

    @FXML
    LineChart<String, Number> progressChart;
    ObservableList<Month> monthProgress = null;

    public void loadProgress() {

        monthProgress = FXCollections.observableArrayList();
        String monthst = "SELECT * FROM `jdbc-video`.month;";
        ResultSet monthList;

        try {
            PreparedStatement mre = connection.prepareStatement(monthst);
            monthList = mre.executeQuery();
            while (monthList.next()) {
                monthProgress.add(new Month(monthList.getDouble(3), monthList.getDouble(1), monthList.getDouble(5), monthList.getDouble(2), monthList.getDouble(6), monthList.getDouble(4)));
            }
            //new Month(monthList.getDouble(2), monthList.getDouble(0), monthList.getDouble(4), monthList.getDouble(1),
        } catch (SQLException ex) {

        }


        progressChart.getData().clear();

        XYChart.Series<String, Number> turnoverSeries = new XYChart.Series<String, Number>();
        turnoverSeries.getData().add(new XYChart.Data<String, Number>("Jan", monthProgress.get(0).turnover));
        turnoverSeries.getData().add(new XYChart.Data<String, Number>("Feb", monthProgress.get(1).turnover));
        turnoverSeries.getData().add(new XYChart.Data<String, Number>("Mar", monthProgress.get(2).turnover));
        turnoverSeries.getData().add(new XYChart.Data<String, Number>("Apr", monthProgress.get(3).turnover));
        turnoverSeries.getData().add(new XYChart.Data<String, Number>("May", monthProgress.get(4).turnover));
        turnoverSeries.getData().add(new XYChart.Data<String, Number>("Jun", monthProgress.get(5).turnover));
        turnoverSeries.getData().add(new XYChart.Data<String, Number>("Jul", monthProgress.get(6).turnover));
        turnoverSeries.getData().add(new XYChart.Data<String, Number>("Aug", monthProgress.get(7).turnover));
        turnoverSeries.getData().add(new XYChart.Data<String, Number>("Sep", monthProgress.get(8).turnover));
        turnoverSeries.getData().add(new XYChart.Data<String, Number>("Oct", monthProgress.get(9).turnover));
        turnoverSeries.getData().add(new XYChart.Data<String, Number>("Nov", monthProgress.get(10).turnover));
        turnoverSeries.getData().add(new XYChart.Data<String, Number>("Dec", monthProgress.get(11).turnover));
        turnoverSeries.setName("Turnover");

        XYChart.Series<String, Number> profitSeries = new XYChart.Series<String, Number>();
        profitSeries.getData().add(new XYChart.Data<String, Number>("Jan", monthProgress.get(0).profit));
        profitSeries.getData().add(new XYChart.Data<String, Number>("Feb", monthProgress.get(1).profit));
        profitSeries.getData().add(new XYChart.Data<String, Number>("Mar", monthProgress.get(2).profit));
        profitSeries.getData().add(new XYChart.Data<String, Number>("Apr", monthProgress.get(3).profit));
        profitSeries.getData().add(new XYChart.Data<String, Number>("May", monthProgress.get(4).profit));
        profitSeries.getData().add(new XYChart.Data<String, Number>("Jun", monthProgress.get(5).profit));
        profitSeries.getData().add(new XYChart.Data<String, Number>("Jul", monthProgress.get(6).profit));
        profitSeries.getData().add(new XYChart.Data<String, Number>("Aug", monthProgress.get(7).profit));
        profitSeries.getData().add(new XYChart.Data<String, Number>("Sep", monthProgress.get(8).profit));
        profitSeries.getData().add(new XYChart.Data<String, Number>("Oct", monthProgress.get(9).profit));
        profitSeries.getData().add(new XYChart.Data<String, Number>("Nov", monthProgress.get(10).profit));
        profitSeries.getData().add(new XYChart.Data<String, Number>("Dec", monthProgress.get(11).profit));
        profitSeries.setName("Profit");

        progressChart.getData().addAll(turnoverSeries, profitSeries);
    }

    @FXML
    Label month;
    @FXML
    Label lb_salary;
    @FXML
    Label lb_import;
    @FXML
    Label lb_tax;
    @FXML
    Label lb_other;
    @FXML
    AnchorPane ac_manage;
    @FXML
    AnchorPane ac_progress;
    public void openManagement() {
        loadProgress();
        int mo = LocalDate.now().getMonthValue();
        Month m = monthProgress.get(mo - 1);
        month.setText(Integer.toString(mo));
        lb_import.setText(Double.toString(m.importTotal/1000));
        lb_salary.setText(Double.toString(m.personnelSalary/1000));
        lb_tax.setText(Double.toString(m.tax/1000));
        lb_other.setText(Double.toString(m.other/1000));
        ac_progress.setVisible(true);
        ac_manage.setVisible(true);

    }

    public void closeManagement() {
        ac_progress.setVisible(false);
        ac_manage.setVisible(false);
    }

    public void detailSalary(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../ui/view/salary.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        String css = Objects.requireNonNull(Main.class.getResource("../ui/view/main.css")).toExternalForm();

        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(css);

        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }

    public void product(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../ui/view/ProductsPage.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Products Management");
        stage.show();
    }

    public void updateProfit() {
        int mo = LocalDate.now().getMonthValue();

        monthProgress = FXCollections.observableArrayList();
        String monthst = "SELECT * FROM `jdbc-video`.month where name = " + String.valueOf(mo) + ";";
        ResultSet monthList;
        Month m = null;

        try {
            PreparedStatement mre = connection.prepareStatement(monthst);
            monthList = mre.executeQuery();
            while (monthList.next()) {
                m = new Month(monthList.getDouble(3), monthList.getDouble(1), monthList.getDouble(5), monthList.getDouble(2), monthList.getDouble(6), monthList.getDouble(4));
            }
            //new Month(monthList.getDouble(2), monthList.getDouble(0), monthList.getDouble(4), monthList.getDouble(1),
        } catch (SQLException ex) {

        }

        m.profit = m.turnover - m.tax - m.importTotal - m.other - m.personnelSalary;
        String query = "update month set profit = " + String.valueOf(m.profit) + " where name = " + String.valueOf(mo) + ";";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException exception) {

        }

    }

}
