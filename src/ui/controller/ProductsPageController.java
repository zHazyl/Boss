//package ui.controller;
//
//import java.math.BigDecimal;
//import java.nio.file.DirectoryStream.Filter;
//import java.util.ArrayList;
//import java.util.List;
//
//import connector.AddNewProduct;
//import connector.AddQuantity;
//import connector.ChangeExportPrice;
//import connector.DeleteProduct;
//import connector.LoadProducts;
//import javafx.collections.transformation.FilteredList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.Tab;
//import javafx.scene.control.TabPane;
//import javafx.scene.control.TextField;
//import product.ObservableProduct;
//import ui.controller.ProductsPageTab.ProductsPageTab;
//
//public class ProductsPageController {
//
//    private static String[] tabNames = { "meats", "grains", "vegetables", "fruits", "juice", "coffee", "tea", "milk",
//            "water", "soft_drinks", "beer", "wine" }; // tabNames = list of table name
//
//    private List<ProductsPageTab> tabList = new ArrayList<>();
//
//    @FXML
//    private TabPane tabPane;
//    @FXML
//    private Tab beerTab;
//    @FXML
//    private Tab coffeeTab;
//    @FXML
//    private Tab fruitsTab;
//    @FXML
//    private Tab grainsTab;
//    @FXML
//    private Tab juiceTab;
//    @FXML
//    private Tab meatsTab;
//    @FXML
//    private Tab milkTab;
//    @FXML
//    private Tab soft_drinksTab;
//    @FXML
//    private Tab teaTab;
//    @FXML
//    private Tab vegetablesTab;
//    @FXML
//    private Tab waterTab;
//    @FXML
//    private Tab wineTab;
//
//    @FXML
//    private TextField txtFilterName;
//    @FXML
//    private Button deleteProductBtn;
//    @FXML
//    private Button addQuantityBtn;
//    @FXML
//    private Button changeImportPriceBtn;
//    @FXML
//    private TextField txtAddQuantity;
//    @FXML
//    private TextField txtChangeExportPrice;
//
//    @FXML
//    private Button addNewProductBtn;
//    @FXML
//    private ComboBox<String> tableNameCbBox;
//    @FXML
//    private TextField txtCountPerUnit;
//    @FXML
//    private TextField txtExportPrice;
//    @FXML
//    private TextField txtImportPrice;
//    @FXML
//    private TextField txtName;
//    @FXML
//    private TextField txtQty;
//
//    @FXML
//    void addNewProductBtnClicked(ActionEvent event) {
//        String tableName = tableNameCbBox.getSelectionModel().getSelectedItem();
//        String name = txtName.getText();
//        String countPerUnit = txtCountPerUnit.getText();
//        String quantity = txtQty.getText();
//        String importPrice = txtImportPrice.getText();
//        String exportPrice = txtExportPrice.getText();
//
//        // check value is valid?
//
//        AddNewProduct.Add(tableName, name, countPerUnit, quantity, importPrice, exportPrice);
//
//        // load all products in this tab from database (better than only change in
//        // observable list)
//        int tabIndex = 0;
//        for (tabIndex = 0; tabIndex < tabNames.length; tabIndex++) {
//            if (tabNames[tabIndex] == tableName) {
//                break;
//            }
//        }
//        tabList.get(tabIndex).setProducts(LoadProducts.load(tableName));
//        tabList.get(tabIndex).setItems();
//    }
//
//    @FXML
//    void deleteProductBtnClicked(ActionEvent event) {
//        int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
//        String tableName = tabNames[tabIndex];
//        ObservableProduct product = tabList.get(tabIndex).getSelectedItem();
//
//        // delete
//        DeleteProduct.delete(tableName, product.getId());
//
//        // load all products in this tab from database
//        tabList.get(tabIndex).setProducts(LoadProducts.load(tableName));
//        tabList.get(tabIndex).setItems();
//    }
//
//    @FXML
//    void addQuantityBtnClicked(ActionEvent event) {
//        int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
//        String tableName = tabNames[tabIndex];
//        ObservableProduct product = tabList.get(tabIndex).getSelectedItem();
//
//        String addQty = txtAddQuantity.getText();
//        String id = product.getId();
//
//        // check value is valid?
//
//        AddQuantity.addQuantity(Integer.parseInt(addQty), id, tableName);
//
//        // load all products in this tab from database
//        tabList.get(tabIndex).setProducts(LoadProducts.load(tableName));
//        tabList.get(tabIndex).setItems();
//    }
//
//    @FXML
//    void changeImportPriceBtnClicked(ActionEvent event) {
//        int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
//        String tableName = tabNames[tabIndex];
//        ObservableProduct product = tabList.get(tabIndex).getSelectedItem();
//
//        String newPrice = txtChangeExportPrice.getText();
//        BigDecimal newExportPrice = new BigDecimal(newPrice);
//        String id = product.getId();
//
//        // check value is valid?
//
//        ChangeExportPrice.change(newExportPrice, id, tableName);
//
//        // load all products in this tab from database
//        tabList.get(tabIndex).setProducts(LoadProducts.load(tableName));
//        tabList.get(tabIndex).setItems();
//    }
//
//    @FXML
//    void initialize() {
//        // ordered
//        tabList.add(new ProductsPageTab(meatsTab));
//        // tabList.add(new ProductsPageTab(grainsTab));
//        // tabList.add(new ProductsPageTab(vegetablesTab));
//        tabList.add(new ProductsPageTab(fruitsTab));
//        tabList.add(new ProductsPageTab(juiceTab));
//        tabList.add(new ProductsPageTab(coffeeTab));
//        tabList.add(new ProductsPageTab(teaTab));
//        // tabList.add(new ProductsPageTab(milkTab));
//        tabList.add(new ProductsPageTab(waterTab));
//        tabList.add(new ProductsPageTab(soft_drinksTab));
//        tabList.add(new ProductsPageTab(beerTab));
//        tabList.add(new ProductsPageTab(wineTab));
//
//        tableNameCbBox.getItems().addAll(
//                "NONE", "meats", "grains", "vegetables", "fruits", "juice", "coffee", "tea", "milk",
//                "water", "soft_drinks", "beer", "wine");
//
//        // filter or search
//        txtFilterName.textProperty().addListener((observable, oldVal, newVal) -> {
//            int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
//            FilteredList<ObservableProduct> filteredList = tabList.get(tabIndex).getFilteredList();
//            filteredList.setPredicate(product -> {
//                if (newVal == null || newVal.isEmpty()) {
//                    return true;
//                }
//
//                if (product.getName().indexOf(newVal) != -1) {
//                    return true;
//                } else {
//                    return false;
//                }
//            });
//
//        });
//    }
//}

package ui.controller;

import java.math.BigDecimal;
import java.nio.file.DirectoryStream.Filter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import connector.AddNewProduct;
import connector.AddQuantity;
import connector.ChangeExportPrice;
import connector.DeleteProduct;
import connector.LoadProducts;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import main.DatabaseConnection;
import product.ObservableProduct;
import ui.controller.ProductsPageTab.ProductsPageTab;

public class ProductsPageController {

    private static String[] tabNames = { "meats", "fruits", "juice", "coffee", "tea",
            "water", "soft_drinks", "beer", "wine" }; // tabNames = list of table name

    private List<ProductsPageTab> tabList = new ArrayList<>();

    @FXML
    private TabPane tabPane;
    @FXML
    private Tab beerTab;
    @FXML
    private Tab coffeeTab;
    @FXML
    private Tab fruitsTab;
    @FXML
    private Tab juiceTab;
    @FXML
    private Tab meatsTab;
    @FXML
    private Tab soft_drinksTab;
    @FXML
    private Tab teaTab;
    @FXML
    private Tab waterTab;
    @FXML
    private Tab wineTab;

    @FXML
    private TextField txtFilterName;
    @FXML
    private Button deleteProductBtn;
    @FXML
    private Button addQuantityBtn;
    @FXML
    private Button changeImportPriceBtn;
    @FXML
    private TextField txtAddQuantity;
    @FXML
    private TextField txtChangeExportPrice;

    @FXML
    private Button addNewProductBtn;
    @FXML
    private ComboBox<String> tableNameCbBox;
    @FXML
    private TextField txtCountPerUnit;
    @FXML
    private TextField txtExportPrice;
    @FXML
    private TextField txtImportPrice;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtQty;

    @FXML
    void addNewProductBtnClicked(ActionEvent event) {
        String tableName = tableNameCbBox.getSelectionModel().getSelectedItem();
        String name = txtName.getText();
        String countPerUnit = txtCountPerUnit.getText();
        String quantity = txtQty.getText();
        String importPrice = txtImportPrice.getText();
        String exportPrice = txtExportPrice.getText();

        // check value is valid?

        AddNewProduct.Add(tableName, name, countPerUnit, quantity, importPrice, exportPrice);

        // update total import
        double im = Double.parseDouble(importPrice);
        int qu = Integer.parseInt(quantity);
        double totalIm = im * qu * 1000;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectNow = connection.getConnection();
        String month = String.valueOf(LocalDate.now().getMonthValue());
        String query = "update month set importTotal = importTotal + " + String.valueOf(totalIm) + " where name = " + month + ";";
        try {
            PreparedStatement preparedStatement = connectNow.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException exception) {

        }

        // load all products in this tab from database (better than only change in
        // observable list)
        int tabIndex = 0;
        for (tabIndex = 0; tabIndex < tabNames.length; tabIndex++) {
            if (tabNames[tabIndex] == tableName) {
                break;
            }
        }
        tabList.get(tabIndex).setProducts(LoadProducts.load(tableName));
        tabList.get(tabIndex).setItems();
    }

    @FXML
    void deleteProductBtnClicked(ActionEvent event) {
        int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
        String tableName = tabNames[tabIndex];
        ObservableProduct product = tabList.get(tabIndex).getSelectedItem();

        // delete
        DeleteProduct.delete(tableName, product.getId());

        // load all products in this tab from database
        tabList.get(tabIndex).setProducts(LoadProducts.load(tableName));
        tabList.get(tabIndex).setItems();
    }

    @FXML
    void addQuantityBtnClicked(ActionEvent event) {
        int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
        String tableName = tabNames[tabIndex];
        ObservableProduct product = tabList.get(tabIndex).getSelectedItem();

        String addQty = txtAddQuantity.getText();
        String id = product.getId();
        BigDecimal im = product.getImportPrice();
        int qu = Integer.parseInt(addQty) * 1000;
        String totalIm = String.valueOf(im.multiply(BigDecimal.valueOf(qu)));
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectNow = connection.getConnection();
        String month = String.valueOf(LocalDate.now().getMonthValue());
        String query = "update month set importTotal = importTotal + " + totalIm + " where name = " + month + ";";
        System.out.println(query);
        try {
            PreparedStatement preparedStatement = connectNow.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException exception) {

        }

        // check value is valid?

        AddQuantity.addQuantity(Integer.parseInt(addQty), id, tableName);

        // load all products in this tab from database
        tabList.get(tabIndex).setProducts(LoadProducts.load(tableName));
        tabList.get(tabIndex).setItems();
    }

    @FXML
    void changeImportPriceBtnClicked(ActionEvent event) {
        int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
        String tableName = tabNames[tabIndex];
        ObservableProduct product = tabList.get(tabIndex).getSelectedItem();

        String newPrice = txtChangeExportPrice.getText();
        BigDecimal newExportPrice = new BigDecimal(newPrice);
        String id = product.getId();

        // check value is valid?

        ChangeExportPrice.change(newExportPrice, id, tableName);

        // load all products in this tab from database
        tabList.get(tabIndex).setProducts(LoadProducts.load(tableName));
        tabList.get(tabIndex).setItems();
    }

    @FXML
    void initialize() {
        // ordered
        tabList.add(new ProductsPageTab(meatsTab));
        // tabList.add(new ProductsPageTab(grainsTab));
        // tabList.add(new ProductsPageTab(vegetablesTab));
        tabList.add(new ProductsPageTab(fruitsTab));
        tabList.add(new ProductsPageTab(juiceTab));
        tabList.add(new ProductsPageTab(coffeeTab));
        tabList.add(new ProductsPageTab(teaTab));
        // tabList.add(new ProductsPageTab(milkTab));
        tabList.add(new ProductsPageTab(waterTab));
        tabList.add(new ProductsPageTab(soft_drinksTab));
        tabList.add(new ProductsPageTab(beerTab));
        tabList.add(new ProductsPageTab(wineTab));

        tableNameCbBox.getItems().addAll(
                "NONE", "meats", "fruits", "juice", "coffee", "tea",
                "water", "soft_drinks", "beer", "wine");

        // filter or search
        txtFilterName.textProperty().addListener((observable, oldVal, newVal) -> {
            int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
            FilteredList<ObservableProduct> filteredList = tabList.get(tabIndex).getFilteredList();
            filteredList.setPredicate(product -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }

                if (product.getName().indexOf(newVal) != -1) {
                    return true;
                } else {
                    return false;
                }
            });

        });
    }
}

