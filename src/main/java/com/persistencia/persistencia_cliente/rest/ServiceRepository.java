package com.persistencia.persistencia_cliente.rest;

import com.persistencia.persistencia_cliente.model.Cliente;
import com.persistencia.persistencia_cliente.model.ClienteDTO;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
public interface ServiceRepository extends PagingAndSortingRepository<ClienteDTO, Long> {

    List<ClienteDTO> findAll();



}
