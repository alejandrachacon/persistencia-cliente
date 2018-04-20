package com.persistencia.persistencia_cliente.rest;

import com.persistencia.persistencia_cliente.model.Cliente;

import java.util.Map;

public interface ServiceRepository {

    Map<String, Cliente> getTransactions();
}
