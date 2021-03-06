package com.persistencia.persistencia_cliente.service;

import com.persistencia.persistencia_cliente.kafka.ConsumerService;
import com.persistencia.persistencia_cliente.model.Cliente;
import com.persistencia.persistencia_cliente.model.ClienteDTO;
import com.persistencia.persistencia_cliente.rest.ServiceRepository;
import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Service
public class ClientService {

    private final Map transactionsMap;
    private final ConsumerService consumerService;

    @Autowired
    private ServiceRepository serviceRepository;


    public ClientService(final ConsumerService newConsumerService, final Map newTransactionsMap) {
        this.consumerService = newConsumerService;
        this.transactionsMap = newTransactionsMap;
    }

    @PostConstruct
    public void init() {
        System.out.println("executing init()....");
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::updateCache, 0L, 60L, TimeUnit.SECONDS);
    }

    @SuppressWarnings("unchecked")
    private void updateCache() {
//        Map<String, Cliente> transactionMap = mapGenericRecordMap(this.consumerService.getKafkaRecords(1000, "gestionClientes"));
        mapGenericRecordMap(this.consumerService.getKafkaRecords(1000, "gestionClient"));
//        this.transactionsMap.putAll(transactionMap);

    }

//    private Map<String, Cliente> mapGenericRecordMap(Map<String, GenericRecord> transactions) {
    private void mapGenericRecordMap(Map<String, GenericRecord> transactions) {
//        HashMap<String, Cliente> resultMap = new HashMap<>();
        transactions.forEach((key, value) -> {
            System.out.println("SAVING....");
            ClienteDTO cliente = new ClienteDTO();
            Cliente c = mapGenericRecord(value);
            cliente.setEmail(c.getEmail());
            cliente.setName(c.getName());
                serviceRepository.save(cliente);
//            resultMap.put(key, mapGenericRecord(value));

        });
//        return resultMap;
    }

    private Cliente mapGenericRecord(GenericRecord value) {
        return new Cliente.Builder()
                .name(String.valueOf(value.get("name")))
                .email(String.valueOf(value.get("email")))
                .build();
    }
}
