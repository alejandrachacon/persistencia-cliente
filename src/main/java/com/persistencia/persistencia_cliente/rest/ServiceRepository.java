package com.persistencia.persistencia_cliente.rest;

import com.persistencia.persistencia_cliente.model.Cliente;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Map;

public interface ServiceRepository extends PagingAndSortingRepository<Cliente, Long> {

    Map<String, Cliente> getTransactions();
    List<Cliente> findAll();



}
