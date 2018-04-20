package com.persistencia.persistencia_cliente.config;

import com.persistencia.persistencia_cliente.kafka.ConsumerService;
import com.persistencia.persistencia_cliente.respository.ClienteRepository;
import com.persistencia.persistencia_cliente.rest.ServiceRepository;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class SpringConfig {

    public static final String BOOTSTRAP_SERVERS = "35.171.129.201:9092";
    public static final String SCHEMA_REGISTRY = "http://35.171.129.201:8081";

    @Bean
    public KafkaConsumer consumer(){
        final Properties consumerConfig = new Properties();
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                io.confluent.kafka.serializers.KafkaAvroDeserializer.class);
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                io.confluent.kafka.serializers.KafkaAvroDeserializer.class);
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        consumerConfig.put("acks", "all");
        consumerConfig.put("retries", 0);
        consumerConfig.put("schema.registry.url", SCHEMA_REGISTRY);

        return new KafkaConsumer(consumerConfig);
    }


    @Bean
    public ConsumerService consumerService(final KafkaConsumer newConsumer) {
        return new ConsumerService(newConsumer);
    }

    @Bean
    public ServiceRepository kafkaRepository(final ConsumerService consumerService, final Map transactionsMap) {
        return new ClienteRepository(consumerService, transactionsMap);
    }

    @Bean
    public Map transactionsMap() {
        return new ConcurrentHashMap();
    }

}
