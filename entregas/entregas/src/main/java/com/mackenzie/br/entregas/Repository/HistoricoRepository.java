package com.mackenzie.br.entregas.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mackenzie.br.entregas.historicoStatus.HistoricoStatus;

public interface HistoricoRepository extends JpaRepository<HistoricoStatus, Long> {
    List<HistoricoStatus> findByEntregaId(Long entregaId);
}