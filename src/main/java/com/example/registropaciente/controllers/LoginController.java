package com.example.registropaciente.controllers;

import com.example.registropaciente.dao.UserDAO;
import com.example.registropaciente.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        String username = txtUsername.getText().trim();
        String password = txtPasswordField.getText();

        User userFound = findUserByUsername(username);

        if (userFound == null) {
            showWarning("El usuario es incorrecto, intente de nuevo");
            txtUsername.requestFocus();
            txtUsername.selectAll();
            return;
        }


        if (!userFound.getPassword().equals(password)) {
            showWarning("La contraseña es incorrecta, intente de nuevo");
            txtPasswordField.requestFocus();
            txtPasswordField.selectAll();
            return;
        }

        openPacientView(userFound);
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
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/registropaciente/patient-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) txtPasswordField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
