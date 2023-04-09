module com.example.concurscopii {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires org.xerial.sqlitejdbc;


    opens com.example.concurscopii to javafx.fxml;
    exports com.example.concurscopii;

    opens com.example.concurscopii.service to javafx.fxml;
    exports com.example.concurscopii.service;

    opens com.example.concurscopii.domain to javafx.fxml;
    exports com.example.concurscopii.domain ;

    opens com.example.concurscopii.repository to javafx.fxml;
    exports com.example.concurscopii.repository;

    opens com.example.concurscopii.controller to javafx.fxml;
    exports com.example.concurscopii.controller;
}