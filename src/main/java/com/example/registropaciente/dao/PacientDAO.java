package com.example.registropaciente.dao;

import com.example.registropaciente.models.Pacient;

import java.util.ArrayList;
import java.util.List;

public class PacientDAO {
    List<Pacient> pacients;

    public void PacienteDAO(){
        pacients = new ArrayList<>();
    }

    public void addPacient(Pacient pacient){
        pacients.add(pacient);
    }

    public List<Pacient> listarPacientes(){
        return pacients;
    }
}
