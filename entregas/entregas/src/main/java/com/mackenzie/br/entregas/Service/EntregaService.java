package com.mackenzie.br.entregas.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mackenzie.br.entregas.Cliente.Cliente;
import com.mackenzie.br.entregas.Repository.ClienteRepository;
import com.mackenzie.br.entregas.Repository.EnderecoRepository;
import com.mackenzie.br.entregas.Repository.EntregaRepository;
import com.mackenzie.br.entregas.Repository.HistoricoRepository;
import com.mackenzie.br.entregas.Repository.TransportadoraRepository;
import com.mackenzie.br.entregas.endereco.Endereco;
import com.mackenzie.br.entregas.entrega.Entrega;
import com.mackenzie.br.entregas.historicoStatus.HistoricoStatus;
import com.mackenzie.br.entregas.transportadora.Transportadora;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EntregaService {

    private final EntregaRepository entregaRepository;
    private final ClienteRepository clienteRepository;
    private final TransportadoraRepository transportadoraRepository;
    private final EnderecoRepository enderecoRepository;
    private final HistoricoRepository historicoRepository;


    public Entrega criarEntrega(Long clienteId, Long transportadoraId, String descricao, Long enderecoOrigemId) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrado."
                ));


        Transportadora transportadora = transportadoraRepository.findById(transportadoraId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Transportadora não encontrada."
                ));


        Entrega entrega = new Entrega();
        entrega.setCliente(cliente);
        entrega.setTransportadora(transportadora);
        entrega.setDescricao(descricao);
        entrega.setStatus("PEDIDO_REALIZADO");

        if (enderecoOrigemId != null) {
            Endereco enderecoOrigem = enderecoRepository.findById(enderecoOrigemId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Endereço de origem não encontrado."
                    ));
            entrega.setEnderecoOrigem(enderecoOrigem);
        }

        entrega = entregaRepository.save(entrega);

        HistoricoStatus historico = new HistoricoStatus();
        historico.setStatus("PEDIDO_REALIZADO");
        historico.setEntrega(entrega);
        historico.setEntregaRefId(entrega.getId());
        historicoRepository.save(historico);

        return entrega;
    }


    public List<Entrega> listar() {
        return entregaRepository.findAll();
    }


    public Entrega buscarPorId(Long id) {
        return entregaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Entrega não encontrada."
                ));
    }


    public List<Entrega> listarPorCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cliente não encontrado."
            );
        }
        return entregaRepository.findByClienteId(clienteId);
    }


    public List<Entrega> listarPorTransportadora(Long transportadoraId) {
        if (!transportadoraRepository.existsById(transportadoraId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Transportadora não encontrada."
            );
        }
        return entregaRepository.findByTransportadoraId(transportadoraId);
    }

    public Entrega atualizarStatus(Long entregaId, String novoStatus) {
        Entrega entrega = buscarPorId(entregaId);


        String[] statusValidos = { "PEDIDO_REALIZADO", "PACOTE_COLETADO", "EM_TRANSITO", "EM_ENTREGA", "ENTREGADO" };
        boolean statusValido = false;
        for (String status : statusValidos) {
            if (status.equals(novoStatus)) {
                statusValido = true;
                break;
            }
        }

        if (!statusValido) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Status inválido. Valores aceitos: PEDIDO_REALIZADO, PACOTE_COLETADO, EM_TRANSITO, EM_ENTREGA, ENTREGADO"
            );
        }

        entrega.setStatus(novoStatus);
        entrega = entregaRepository.save(entrega);


        HistoricoStatus historico = new HistoricoStatus();
        historico.setStatus(novoStatus);
        historico.setEntrega(entrega);
        historico.setEntregaRefId(entrega.getId());
        historicoRepository.save(historico);

        return entrega;
    }


    public void deletar(Long id) {
        if (!entregaRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Entrega não encontrada."
            );
        }

        var historicos = historicoRepository.findByEntregaId(id);
        for (HistoricoStatus historico : historicos) {
            historico.setEntrega(null);
            historicoRepository.save(historico);
        }

        entregaRepository.deleteById(id);
    }
}
