package product;

import java.math.BigDecimal;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ObservableProduct {
    private StringProperty id;
    // id = name_of_table + id_in_table
    private StringProperty name;
    private StringProperty countPerUnit;
    private StringProperty quantity;
    private StringProperty importPrice;
    private StringProperty exportPrice;

    public ObservableProduct(String id, String name, String countPerUnit, String quantity, BigDecimal importPrice,
            BigDecimal exportPrice) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.countPerUnit = new SimpleStringProperty(countPerUnit);
        this.quantity = new SimpleStringProperty(quantity);
        this.importPrice = new SimpleStringProperty(importPrice.toString());
        this.exportPrice = new SimpleStringProperty(exportPrice.toString());
    }

    // * gets, sets, property
    // id
    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

    // name
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    // count per unit
    public int getCountPerUnit() {
        return Integer.parseInt(countPerUnit.get());
    }

    public void setCountPerUnit(int countperunit) {
        this.countPerUnit.get();
    }

    public StringProperty countPerUnitProperty() {
        return countPerUnit;
    }

    // quantity
    public int getQuantity() {
        return Integer.parseInt(quantity.get());
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity + "");
    }

    public StringProperty quantityProperty() {
        return quantity;
    }

    // import price
    public BigDecimal getImportPrice() {
        return new BigDecimal(importPrice.get());
    }

    public void setImportPrice(BigDecimal importPrice) {
        this.importPrice.set(importPrice.toString());
    }

    public StringProperty importPriceProperty() {
        return importPrice;
    }

    // export price
    public BigDecimal getExportPrice() {
        return new BigDecimal(exportPrice.get());
    }

    public void setExportPrice(BigDecimal exportPrice) {
        this.exportPrice.set(exportPrice.toString());
    }

    public StringProperty exportPriceProperty() {
        return exportPrice;
    }
}
