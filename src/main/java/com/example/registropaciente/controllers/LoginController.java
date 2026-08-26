package com.example.registropaciente.controllers;

import com.example.registropaciente.dao.UserDAO;
import com.example.registropaciente.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    UserDAO users = new UserDAO();

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPasswordField;

    @FXML
    protected void ingresar(){
        User currentUser = new User(txtUsername.getText(), txtPasswordField.getText());
        System.out.println(currentUser + " creado");
        System.out.println("Usuarios actuales en sistema: " + users.getUsers());
        for(User userInList: users.getUsers()){
            if(currentUser.equals(userInList)){
                System.out.println("entra aca");
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pacient-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = (Stage) txtPasswordField.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
