module com.example.registropaciente {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens com.example.registropaciente to javafx.fxml;
    exports com.example.registropaciente;
}