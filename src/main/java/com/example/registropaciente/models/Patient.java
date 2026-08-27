package com.example.registropaciente.models;

import com.example.registropaciente.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Patient {
    private String names;
    private String surnames;
    private Sexo sex;
    private boolean isSick;
    private Date birthDate;
}
