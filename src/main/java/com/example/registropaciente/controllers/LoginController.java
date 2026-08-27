package com.example.registropaciente.controllers;

import com.example.registropaciente.dao.UserDAO;
import com.example.registropaciente.models.User;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class LoginController {
    private final UserDAO users = new UserDAO();

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPasswordField;

    @FXML
    private Label lblAdvertencia;

    @FXML
    protected void focusPassword(){
        txtPasswordField.requestFocus();
    }

    @FXML
    protected void ingresar(){
        User user = leerDatos();

        if (validarDatos(user)) {
            User userFound = findUserByUsername(user.getUsername());
            openPacientView(userFound);
        }
    }

    private User leerDatos() {
        String username = txtUsername.getText().trim();
        String password = txtPasswordField.getText();
        return new User(username, password);
    }

    private boolean validarDatos(User user) {
        User userFound = findUserByUsername(user.getUsername());

        if (userFound == null) {
            showWarning("El usuario es incorrecto, intente de nuevo");
            txtUsername.requestFocus();
            txtUsername.selectAll();
            return false;
        }

        if (!userFound.getPassword().equals(user.getPassword())) {
            showWarning("La contraseña es incorrecta, intente de nuevo");
            txtPasswordField.requestFocus();
            txtPasswordField.selectAll();
            return false;
        }

        return true;
    }

    private User findUserByUsername(String username) {
        for (User user : users.getUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }



    private void showWarning(String message) {
        lblAdvertencia.setText(message);
    }

    private void openPacientView(User user) {
        System.out.println("Iniciando sesión del usuario " + user.getUsername());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/registropaciente/patient-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) txtPasswordField.getScene().getWindow();
            stage.setTitle("Registro de Pacientes");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.centerOnScreen();

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.5), root);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
