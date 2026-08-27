package com.example.registropaciente.dao;

import com.example.registropaciente.models.Patient;

import java.util.ArrayList;
import java.util.List;

public class PacientDAO {
    private final List<Patient> pacients = new ArrayList<>();

    public void addPacient(Patient pacient){
        pacients.add(pacient);
    }

    public List<Patient> listarPacientes(){
        return pacients;
    }
}
