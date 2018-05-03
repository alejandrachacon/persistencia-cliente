package com.persistencia.persistencia_cliente.rest;


import com.persistencia.persistencia_cliente.model.Cliente;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestApiService {

    private final ServiceRepository repository;

    public RestApiService(ServiceRepository newRepository) {
        this.repository = newRepository;
    }

    @GetMapping(path = "/getClients")
    public List<Cliente> getClients() {
       return repository.findAll();
    }
}
