module com.example.registropaciente {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens com.example.registropaciente to javafx.fxml;
    exports com.example.registropaciente;
    exports com.example.registropaciente.controllers;
    opens com.example.registropaciente.controllers to javafx.fxml;
    exports com.example.registropaciente.application;
    opens com.example.registropaciente.application to javafx.fxml;
}