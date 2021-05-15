module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires java.sql;
    requires org.controlsfx.controls;

    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.connection;
    opens org.example.connection to javafx.fxml;
}