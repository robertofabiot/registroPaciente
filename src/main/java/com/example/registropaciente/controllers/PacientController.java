package com.example.registropaciente.controllers;

import com.example.registropaciente.dao.PacientDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PacientController {
    PacientDAO pacients = new PacientDAO();

    @FXML
    private TextField txtNames;

    @FXML
    private TextField txtSurnames;

    @FXML
    protected void addOnClick(){
    }

    private void leerDatos() {
    }
}
