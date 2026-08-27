module com.example.registropaciente {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires static lombok;


    opens com.example.registropaciente to javafx.fxml;
    exports com.example.registropaciente;
    exports com.example.registropaciente.controllers;
    opens com.example.registropaciente.controllers to javafx.fxml;
    exports com.example.registropaciente.application;
    opens com.example.registropaciente.application to javafx.fxml;
    exports com.example.registropaciente.enums;
    opens com.example.registropaciente.enums to javafx.fxml;
<<<<<<< Updated upstream
    exports com.example.registropaciente.models;
    opens com.example.registropaciente.models to javafx.fxml, javafx.base;
}
=======
}
>>>>>>> Stashed changes
