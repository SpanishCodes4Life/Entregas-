package com.mackenzie.br.entregas.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriarEntregaDTO {
    private Long clienteId;
    private Long transportadoraId;
    private String descricao;
    private Long enderecoOrigemId;
}
