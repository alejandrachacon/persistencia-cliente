package com.persistencia.persistencia_cliente.config;

import com.persistencia.persistencia_cliente.kafka.ConsumerService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class SpringConfig {

    public static final String BOOTSTRAP_SERVERS = "localhost:9092";
    public static final String SCHEMA_REGISTRY = "http://localhost:8081";

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
    public ConsumerService consumerService(final KafkaConsumer newConsumer){
        return new ConsumerService(newConsumer);
    }

}
