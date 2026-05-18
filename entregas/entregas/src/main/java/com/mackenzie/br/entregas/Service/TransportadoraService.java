package com.mackenzie.br.entregas.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mackenzie.br.entregas.Repository.TransportadoraRepository;
import com.mackenzie.br.entregas.transportadora.Transportadora;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransportadoraService {

    private final TransportadoraRepository transportadoraRepository;

    public List<Transportadora> listar() {
        return transportadoraRepository.findAll();
    }
}
