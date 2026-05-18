package com.mackenzie.br.entregas.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mackenzie.br.entregas.entrega.Entrega;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    List<Entrega> findByClienteId(Long clienteId);
    List<Entrega> findByTransportadoraId(Long transportadoraId);
}