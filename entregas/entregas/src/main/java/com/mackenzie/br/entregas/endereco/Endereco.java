package com.mackenzie.br.entregas.endereco;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mackenzie.br.entregas.Cliente.Cliente;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;
    private String rua;
    private String cidade;
    private String estado;

    @OneToOne
    @JsonBackReference
    private Cliente cliente;
}