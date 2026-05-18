package com.mackenzie.br.entregas.entrega;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mackenzie.br.entregas.Cliente.Cliente;
import com.mackenzie.br.entregas.transportadora.Transportadora;
import com.mackenzie.br.entregas.historicoStatus.HistoricoStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Entity
@Data
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status; // Status atual (redundante com histórico, para facilitar buscas)

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("entregas")
    private Cliente cliente;

    @ManyToOne
    @JsonIgnoreProperties("entregas")
    private Transportadora transportadora;

    private String descricao; // Descrição do pacote (pode ser nulo)

    @ManyToOne
    private com.mackenzie.br.entregas.endereco.Endereco enderecoOrigem; // Onde o pacote é enviado (pode ser nulo)

    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "entrega", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("entrega")
    private List<HistoricoStatus> historicoStatus = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
}
