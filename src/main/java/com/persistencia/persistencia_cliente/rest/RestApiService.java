package com.persistencia.persistencia_cliente.rest;


import com.persistencia.persistencia_cliente.model.Cliente;
import com.persistencia.persistencia_cliente.model.ClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestApiService {

    @Autowired
    private ServiceRepository repository;

    @GetMapping(path = "/getClients")
    public List<ClienteDTO> getClients() {
       return repository.findAll();
    }
}
