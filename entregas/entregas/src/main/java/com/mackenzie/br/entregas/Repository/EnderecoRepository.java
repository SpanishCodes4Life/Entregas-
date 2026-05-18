package com.mackenzie.br.entregas.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mackenzie.br.entregas.endereco.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Optional<Endereco> findByClienteId(Long clienteId);
}