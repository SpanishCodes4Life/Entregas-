package com.mackenzie.br.entregas.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriarClienteDTO {
    private String nome;
    private String cep;
}
