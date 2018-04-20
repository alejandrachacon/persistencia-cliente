package com.persistencia.persistencia_cliente.kafka;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsumerService {

    private KafkaConsumer consumer;

    public ConsumerService(KafkaConsumer newConsumer) {
        this.consumer = newConsumer;
    }

    public Map<String, GenericRecord> getKafkaRecords(final long timeout, final String topic) {
        List<TopicPartition> partitions = Arrays.asList(new TopicPartition(topic, 0));
        this.consumer.assign(partitions);
        this.consumer.seekToBeginning(partitions);
        Map<String, GenericRecord> recordMap = new HashMap<>();

        while (true) {
            ConsumerRecords<String, GenericRecord> records = this.consumer.poll(timeout);

            if (records == null || records.isEmpty()) {
                return recordMap;
            }

            for (ConsumerRecord<String, GenericRecord> cRecord : records) {
                if (cRecord.value() != null) {
                    recordMap.put(cRecord.key(), cRecord.value());
                } else {
                    recordMap.remove(cRecord.key());
                }
            }
        }
    }

}
