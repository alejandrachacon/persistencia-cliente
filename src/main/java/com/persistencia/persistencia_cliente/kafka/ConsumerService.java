package com.persistencia.persistencia_cliente.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerService {

    private KafkaConsumer consumer;



    public ConsumerService(KafkaConsumer newConsumer) {
        this.consumer = newConsumer;
    }
}
