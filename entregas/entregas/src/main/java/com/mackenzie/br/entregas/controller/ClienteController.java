package com.mackenzie.br.entregas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mackenzie.br.entregas.Cliente.Cliente;
import com.mackenzie.br.entregas.Service.ClienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

// create
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody CriarClienteDTO dto) {
    try {
        return ResponseEntity.status(201)
            .body(service.salvarClienteComEndereco(dto.getNome(), dto.getCep()));
    }catch (ResponseStatusException ex) {
        return ResponseEntity
            .status(ex.getStatusCode())
            .body(ex.getReason());
    }
    }

// read listar todos
    @GetMapping
    public java.util.List<Cliente> listar() {
        return service.listar();
    }

// read buscar especificamente
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
    try {
        return ResponseEntity.ok(service.buscarPorId(id));
    } catch (ResponseStatusException ex) {
        return ResponseEntity
            .status(ex.getStatusCode())
            .body(ex.getReason());
    }
    }

// update
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
    try {
        Cliente cliente = service.atualizarCliente(id, clienteAtualizado);
        return ResponseEntity.ok(cliente);
    } catch (ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(ex.getReason());
    }
    }

// delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
    try {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    } catch (ResponseStatusException ex) {
        return ResponseEntity
             .status(ex.getStatusCode())
             .body(ex.getReason());
    }
    }

}