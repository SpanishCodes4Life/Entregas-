package com.mackenzie.br.entregas.Service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mackenzie.br.entregas.Cliente.Cliente;
import com.mackenzie.br.entregas.Repository.ClienteRepository;
import com.mackenzie.br.entregas.Repository.EnderecoRepository;
import com.mackenzie.br.entregas.endereco.Endereco;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final ViaCepService viaCepService;

    public Cliente salvarClienteComEndereco(String nome, String cep) {

        Cliente cliente = new Cliente();
        cliente.setNome(nome);

        cliente = clienteRepository.save(cliente);

        var dados = viaCepService.buscarEndereco(cep);

        if(dados == null || dados.get("erro")!=null){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "CEP inválido."
            );
        }
        Endereco endereco = new Endereco();
        endereco.setCep(cep);
        endereco.setRua((String) dados.get("logradouro"));
        endereco.setCidade((String) dados.get("localidade"));
        endereco.setEstado((String) dados.get("uf"));
        endereco.setCliente(cliente);

        enderecoRepository.save(endereco);
        cliente.setEndereco(endereco);
        return cliente;
    }

    public java.util.List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
        .orElseThrow(()->
        new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Cliente não encontrado."
    ));
    }

    public void deletar(Long id) {
        if(!clienteRepository.existsById(id)){
        throw new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Cliente não encontrado."
        );
    }
        clienteRepository.deleteById(id);
    }


    public Cliente atualizarCliente(Long id, Cliente clienteAtualizado) {

        Cliente atual = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Cliente não encontrado."
                        ));

        atual.setNome(clienteAtualizado.getNome());

        Endereco endereco = enderecoRepository.findByClienteId(id)
                .orElseGet(Endereco::new);

         if (clienteAtualizado.getEndereco() == null) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
            "Endereço obrigatório."
                    );
        }

         if (clienteAtualizado.getEndereco().getCep() == null) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
            "CEP obrigatório."
                );
            }

        if (!clienteAtualizado.getEndereco().getCep()
                .equals(endereco.getCep())) {

            var dados = viaCepService.buscarEndereco(
                 clienteAtualizado.getEndereco().getCep()
            );

            endereco.setCep(clienteAtualizado.getEndereco().getCep());
            endereco.setRua((String) dados.get("logradouro"));
            endereco.setCidade((String) dados.get("localidade"));
            endereco.setEstado((String) dados.get("uf"));
            endereco.setCliente(atual);

            enderecoRepository.save(endereco);
        }

        return clienteRepository.save(atual);
    }
}