package com.example.registropaciente.models;

import com.example.registropaciente.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pacient {
    private String names;
    private String surnames;
    private Sexo sex;
    private boolean isSick;
    private Date birthDate;
}
