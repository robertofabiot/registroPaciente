package com.example.registropaciente.controllers;

import com.example.registropaciente.dao.PacientDAO;
import com.example.registropaciente.enums.Sexo;
import com.example.registropaciente.models.Patient;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    private TableView<Patient> tblPacients;

    @FXML
    private TableColumn<Patient, String> colNames;

    @FXML
    private TableColumn<Patient, String> colSurnames;

    @FXML
    private TableColumn<Patient, String> colSex;

    @FXML
    private TableColumn<Patient, String> colSick;

    @FXML
    private TableColumn<Patient, String> colBirthDate;

    @FXML
    private Label lblAdvertencia;

    @FXML
    private void initialize() {
        cbSex.getItems().setAll(Sexo.values());
        configureDatePicker();
        configureTable();
        configureValidation();
        validarDatos();
        refreshPacientList();
    }

    private void configureTable() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        colNames.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNames()));
        colSurnames.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSurnames()));
        colSex.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSex() != null ? data.getValue().getSex().toString() : ""));
        colSick.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isSick() ? "ENFERMO" : "NO ENFERMO"));
        colBirthDate.setCellValueFactory(data -> {
            Date date = data.getValue().getBirthDate();
            return new SimpleStringProperty(date != null ? dateFormat.format(date) : "");
        });
    }

    @FXML
    protected void addOnClick(){
        clearWarning();

        if (!allRequiredDataIsReady()) {
            if (dtPicker.getValue() != null && !isBirthDateValid(dtPicker.getValue())) {
                if (dtPicker.getValue().isAfter(LocalDate.now())) {
                    showWarning("La fecha de nacimiento no puede ser una fecha futura");
                } else {
                    showWarning("La fecha de nacimiento debe estar dentro de los últimos 120 años");
                }
                dtPicker.requestFocus();
                return;
            }

            showWarning("Complete todos los campos obligatorios antes de agregar el paciente");
            focusFirstInvalidField();
            return;
        }

        Patient pacient = leerDatos();

        pacients.addPacient(pacient);
        refreshPacientList();
        clearForm();
    }

    private void configureDatePicker() {
        dtPicker.setEditable(false);
        dtPicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                LocalDate minDate = today.minusYears(120);
                if (date != null && (date.isAfter(today) || date.isBefore(minDate))) {
                    setDisable(true);
                    setStyle("-fx-background-color: #e5e7eb;");
                }
            }
        });
    }

    private boolean isBirthDateValid(LocalDate date) {
        if (date == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        LocalDate minDate = today.minusYears(120);
        return !date.isAfter(today) && !date.isBefore(minDate);
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
        } else if (dtPicker.getValue() != null && !isBirthDateValid(dtPicker.getValue())) {
            if (dtPicker.getValue().isAfter(LocalDate.now())) {
                showWarning("La fecha de nacimiento no puede ser una fecha futura");
            } else {
                showWarning("La fecha de nacimiento debe estar dentro de los últimos 120 años");
            }
        }
    }

    private boolean allRequiredDataIsReady() {
        return hasText(txtNames)
                && hasText(txtSurnames)
                && cbSex.getValue() != null
                && dtPicker.getValue() != null
                && isBirthDateValid(dtPicker.getValue());
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

        if (dtPicker.getValue() == null || !isBirthDateValid(dtPicker.getValue())) {
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
        tblPacients.getItems().setAll(pacients.listarPacientes());
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
