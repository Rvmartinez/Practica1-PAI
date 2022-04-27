module com.example.practica1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens com.example.practica1 to javafx.fxml;
    exports com.example.practica1;
}