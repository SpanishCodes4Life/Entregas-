package com.mackenzie.br.entregas.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {

    @SuppressWarnings("unchecked")
    public java.util.Map<String, Object> buscarEndereco(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        return restTemplate.getForObject(url, java.util.Map.class);
    }
}
