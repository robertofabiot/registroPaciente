package com.example.registropaciente.models;

import com.example.registropaciente.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pacient {
    private String names;
    private String surnames;
    private Sexo sexo;
}
