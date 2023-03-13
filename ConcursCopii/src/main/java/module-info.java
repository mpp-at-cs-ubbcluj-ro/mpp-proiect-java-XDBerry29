module com.example.concurscopii {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.concurscopii to javafx.fxml;
    exports com.example.concurscopii;
}