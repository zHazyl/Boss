module com.example.cashier {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.java;
    requires com.jfoenix;
    requires de.jensd.fx.glyphs.fontawesome;

    opens com.example.boss to javafx.fxml;
    exports com.example.boss;
}