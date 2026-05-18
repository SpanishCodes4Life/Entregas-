package com.mackenzie.br.entregas.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mackenzie.br.entregas.Service.TransportadoraService;
import com.mackenzie.br.entregas.transportadora.Transportadora;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transportadoras")
@RequiredArgsConstructor
public class TransportadoraController {

    private final TransportadoraService service;

    @GetMapping
    public List<Transportadora> listar() {
        return service.listar();
    }
}
