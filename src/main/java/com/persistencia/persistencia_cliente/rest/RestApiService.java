package com.persistencia.persistencia_cliente.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiService {

    private final ServiceRepository repository;

    public RestApiService(ServiceRepository newRepository) {
        this.repository = newRepository;
    }

    @GetMapping(path = "getClients")
    public String getTransactions() {
        try {
            return new ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(this.repository.getTransactions());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error generating json map " + e.getMessage());
        }
    }
}
