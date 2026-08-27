package com.example.registropaciente.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PacientApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(PacientApplication.class.getResource("/com/example/registropaciente/patient-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Paciente");
        stage.setScene(scene);
        stage.show();
    }
}
