package com.mackenzie.br.entregas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mackenzie.br.entregas.transportadora.Transportadora;

public interface TransportadoraRepository extends JpaRepository<Transportadora, Long> {}