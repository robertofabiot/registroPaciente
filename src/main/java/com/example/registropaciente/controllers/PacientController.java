package com.example.registropaciente.controllers;

import com.example.registropaciente.dao.PacientDAO;
import com.example.registropaciente.enums.Sexo;
import com.example.registropaciente.models.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

public class PacientController {
    private final PacientDAO pacients = new PacientDAO();

    @FXML
    private TextField txtNames;

    @FXML
    private TextField txtSurnames;

    @FXML
    private DatePicker dtPicker;

    @FXML
    private ComboBox<Sexo> cbSex;

    @FXML
    private RadioButton rbtnSick;

    @FXML
    private Button btnAdd;

    @FXML
    private ListView<String> lstPacients;

    @FXML
    private Label lblAdvertencia;

    @FXML
    private void initialize() {
        cbSex.getItems().setAll(Sexo.values());
        configureValidation();
        validarDatos();
        refreshPacientList();
    }

    @FXML
    protected void addOnClick(){
        clearWarning();

        if (!allRequiredDataIsReady()) {
            showWarning("Complete todos los campos obligatorios antes de agregar el paciente");
            focusFirstInvalidField();
            return;
        }

        Patient pacient = leerDatos();

        pacients.addPacient(pacient);
        refreshPacientList();
        clearForm();
    }

    private void showWarning(String message) {
        lblAdvertencia.setText(message);
    }

    private void clearWarning() {
        lblAdvertencia.setText("");
    }

    private void configureValidation() {
        txtNames.textProperty().addListener((observable, oldValue, newValue) -> validarDatos());
        txtSurnames.textProperty().addListener((observable, oldValue, newValue) -> validarDatos());
        cbSex.valueProperty().addListener((observable, oldValue, newValue) -> validarDatos());
        dtPicker.valueProperty().addListener((observable, oldValue, newValue) -> validarDatos());
    }

    private void validarDatos(){
        btnAdd.setDisable(!allRequiredDataIsReady());

        if (allRequiredDataIsReady()) {
            clearWarning();
        }
    }

    private boolean allRequiredDataIsReady() {
        return hasText(txtNames)
                && hasText(txtSurnames)
                && cbSex.getValue() != null
                && dtPicker.getValue() != null;
    }

    private boolean hasText(TextField textField) {
        return textField.getText() != null && !textField.getText().trim().isEmpty();
    }

    private void focusFirstInvalidField() {
        if (!hasText(txtNames)) {
            txtNames.requestFocus();
            return;
        }

        if (!hasText(txtSurnames)) {
            txtSurnames.requestFocus();
            return;
        }

        if (cbSex.getValue() == null) {
            cbSex.requestFocus();
            return;
        }

        if (dtPicker.getValue() == null) {
            dtPicker.requestFocus();
        }
    }

    private String formatNames(String name){
        StringBuilder nameFormated = new StringBuilder();

        boolean lastIsWhiteSpace = false;

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);

            if(Character.isWhitespace(c) && !lastIsWhiteSpace) {
                nameFormated.append(c);
               lastIsWhiteSpace = true;

            } else if (Character.isLetter(c)){
                nameFormated.append(c);
                lastIsWhiteSpace = false;
            }

        }
        return nameFormated.toString();
    }

    private Patient leerDatos() {
        Date birthDate = null;

        if (dtPicker.getValue() != null) {
            birthDate = Date.from(dtPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        String name = formatNames(txtNames.getText().trim());
        String surname = formatNames(txtSurnames.getText().trim());

        return new Patient(
                name,
                surname,
                cbSex.getValue(),
                rbtnSick.isSelected(),
                birthDate
        );
    }

    private void refreshPacientList() {
        lstPacients.getItems().clear();

        for (Patient pacient : pacients.listarPacientes()) {
            lstPacients.getItems().add(formatPacient(pacient));
        }
    }

    private String formatPacient(Patient pacient) {
        String sickStatus = pacient.isSick() ? "ENFERMO" : "NO ENFERMO";
        String birthDate = new SimpleDateFormat("dd/MM/yyyy").format(pacient.getBirthDate());

        return "%s - %s | %s | %s | %s".formatted(
                pacient.getNames().toUpperCase(),
                pacient.getSurnames().toUpperCase(),
                pacient.getSex().toString().toUpperCase(),
                sickStatus,
                birthDate);
    }

    private void clearForm() {
        txtNames.clear();
        txtSurnames.clear();
        cbSex.setValue(null);
        dtPicker.setValue(null);
        rbtnSick.setSelected(false);
        txtNames.requestFocus();
    }
}
