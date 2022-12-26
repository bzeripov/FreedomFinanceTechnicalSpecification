module com.example.hashsoapclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.xml;


    opens com.example.hashsoapclient to javafx.fxml;
    exports com.example.hashsoapclient;
}