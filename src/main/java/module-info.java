module org.example.agentvanzari {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.xerial.sqlitejdbc;
    requires java.sql;
    requires spring.security.crypto;

    opens org.example.agentvanzari.domain to javafx.base;

    exports org.example.agentvanzari;
    exports org.example.agentvanzari.service;

    opens org.example.agentvanzari to javafx.fxml;
}