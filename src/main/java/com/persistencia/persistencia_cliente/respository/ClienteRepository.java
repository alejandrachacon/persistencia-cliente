package com.persistencia.persistencia_cliente.respository;

import com.persistencia.persistencia_cliente.kafka.ConsumerService;
import com.persistencia.persistencia_cliente.model.Cliente;
import com.persistencia.persistencia_cliente.rest.ServiceRepository;
import org.apache.avro.generic.GenericRecord;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClienteRepository implements ServiceRepository {

    private final Map transactionsMap;
    private final ConsumerService consumerService;

    public ClienteRepository(final ConsumerService newConsumerService, final Map newTransactionsMap) {
        this.consumerService = newConsumerService;
        this.transactionsMap = newTransactionsMap;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Cliente> getTransactions() {
        return this.transactionsMap;
    }

    @PostConstruct
    public void init() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::updateCache, 0L, 15L, TimeUnit.SECONDS);
    }

    @SuppressWarnings("unchecked")
    private void updateCache() {
        Map<String, Cliente> transactionMap = mapGenericRecordMap(this.consumerService.getKafkaRecords(1000, "gestionClientes"));
        this.transactionsMap.putAll(transactionMap);
    }

    private Map<String, Cliente> mapGenericRecordMap(Map<String, GenericRecord> transactions) {
        HashMap<String, Cliente> resultMap = new HashMap<>();
        transactions.forEach((key, value) -> resultMap.put(key, mapGenericRecord(value)));
        return resultMap;
    }

    private Cliente mapGenericRecord(GenericRecord value) {
        return new Cliente.Builder()
                .name(String.valueOf(value.get("name")))
                .category(String.valueOf(value.get("category")))
                .build();
    }
}
