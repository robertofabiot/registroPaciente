package com.example.registropaciente.controllers;

import com.example.registropaciente.dao.UserDAO;
import com.example.registropaciente.models.User;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
<<<<<<< Updated upstream
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
=======
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
>>>>>>> Stashed changes
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
<<<<<<< Updated upstream
import java.util.ArrayList;
import java.util.List;
=======
import java.net.URL;
>>>>>>> Stashed changes

public class LoginController {
    private final UserDAO users = new UserDAO();

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPasswordField;

    @FXML
    private Label lblAdvertencia;


    @FXML
    private Button btnIngresar;

    @FXML
    private ImageView imgCandado;

    private final List<Image> videoFrames = new ArrayList<>();
    private Timeline videoTimeline;
    private int intentos;

    @FXML
    private void initialize() {
        configurarVideo();
    }

    private void configurarVideo() {
        for (int i = 1; i <= 76; i++) {
            String path = String.format("/com/example/registropaciente/candado_frames/frame_%03d.png", i);
            var resource = getClass().getResource(path);
            if (resource != null) {
                videoFrames.add(new Image(resource.toExternalForm()));
            }
        }
        if (!videoFrames.isEmpty()) {
            imgCandado.setImage(videoFrames.get(0));
        }
    }

    @FXML
    protected void focusPassword(){
        txtPasswordField.requestFocus();
    }

    @FXML
    protected void ingresar(){
        User user = leerDatos();
        intentos++;

        if (validarDatos(user)) {
            User userFound = findUserByUsername(user.getUsername());
            reproducirVideoYEntrar(userFound);
        }
        else {
            mostrarAlerta("Error de autenticación", "Credenciales no válidas.", Alert.AlertType.ERROR);
            if(intentos == 3){
                Stage stage = (Stage) txtPasswordField.getScene().getWindow();
                mostrarAlerta("3 intentos", "Valaverga.", Alert.AlertType.ERROR);
                stage.close();
            }
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void reproducirVideoYEntrar(User userFound) {
        if (videoFrames.isEmpty()) {
            openPacientView(userFound);
            return;
        }

        btnIngresar.setDisable(true);
        videoTimeline = new Timeline();

        double frameDurationMs = 1000.0 / 30.0;
        for (int i = 0; i < videoFrames.size(); i++) {
            final int index = i;
            KeyFrame kf = new KeyFrame(
                    Duration.millis(i * frameDurationMs),
                    e -> imgCandado.setImage(videoFrames.get(index))
            );
            videoTimeline.getKeyFrames().add(kf);
        }

        videoTimeline.setOnFinished(e -> {
            openPacientView(userFound);
        });

        videoTimeline.play();
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
        if (videoTimeline != null) {
            videoTimeline.stop();
        }
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
