package com.mackenzie.br.entregas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mackenzie.br.entregas.Cliente.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {}