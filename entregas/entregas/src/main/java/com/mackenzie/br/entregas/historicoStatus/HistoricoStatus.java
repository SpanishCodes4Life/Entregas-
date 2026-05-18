package com.mackenzie.br.entregas.historicoStatus;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mackenzie.br.entregas.entrega.Entrega;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Entity
@Data
public class HistoricoStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private LocalDateTime dataStatus;

    @Column(nullable = false)
    private Long entregaRefId;

    @ManyToOne(optional = true)
    @JoinColumn(name = "entrega_id", nullable = true)
    @JsonIgnoreProperties({"historicoStatus", "cliente", "transportadora"})
    private Entrega entrega;

    @PrePersist
    protected void onCreate() {
        if (dataStatus == null) {
            dataStatus = LocalDateTime.now();
        }
    }
}
