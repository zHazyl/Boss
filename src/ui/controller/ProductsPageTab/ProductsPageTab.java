package ui.controller.ProductsPageTab;

import connector.LoadProducts;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import product.ObservableProduct;

public class ProductsPageTab {

    private ObservableList<ObservableProduct> products;
    private FilteredList<ObservableProduct> filteredList;

    private Tab productTab;

    private TableView<ObservableProduct> tabTable;
    private TableColumn<ObservableProduct, String> id;
    private TableColumn<ObservableProduct, String> name;
    private TableColumn<ObservableProduct, String> countPerUnit;
    private TableColumn<ObservableProduct, String> quantity;
    private TableColumn<ObservableProduct, String> importPrice;
    private TableColumn<ObservableProduct, String> exportPrice;

    public ProductsPageTab(Tab producTab) {
        this.productTab = producTab;

        tabTable = new TableView<>();
        id = new TableColumn<>("id");
        name = new TableColumn<>("Name");
        name.setPrefWidth(500);
        countPerUnit = new TableColumn<>("Count Per Unit");
        quantity = new TableColumn<>("Qty");
        importPrice = new TableColumn<>("Import Price");
        exportPrice = new TableColumn<>("Export Price");

        initProductTab();
    }

    private void initProductTab() {
        tabTable.getColumns().addAll(id, name, countPerUnit, quantity, importPrice, exportPrice);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        countPerUnit.setCellValueFactory(new PropertyValueFactory<>("countPerUnit"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        importPrice.setCellValueFactory(new PropertyValueFactory<>("importPrice"));
        exportPrice.setCellValueFactory(new PropertyValueFactory<>("exportPrice"));

        products = LoadProducts.load(productTab.getText());

        filteredList = new FilteredList<>(products, b -> true);

        setItems();

        productTab.setContent(tabTable);
    }

    public void setItems() {
        tabTable.setItems(filteredList);
    }

    public ObservableProduct getSelectedItem() {
        return tabTable.getSelectionModel().getSelectedItem();
    }

    public void setProducts(ObservableList<ObservableProduct> products) {
        this.products = products;
        filteredList = new FilteredList<>(products, b -> true);
    }

    public ObservableList<ObservableProduct> getProducts() {
        return products;
    }

    public FilteredList<ObservableProduct> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(FilteredList<ObservableProduct> filteredList) {
        this.filteredList = filteredList;
    }

    public Tab getProductTab() {
        return productTab;
    }

    public void setProductTab(Tab productTab) {
        this.productTab = productTab;
    }
}
