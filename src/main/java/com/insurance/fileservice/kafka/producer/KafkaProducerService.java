package com.insurance.fileservice.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import org.apache.kafka.clients.producer.KafkaProducer;
import com.insurance.fileservice.config.KafkaConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaProducerService {
    private KafkaProducer<String, String> producer;

    @Inject
    private KafkaConfig kafkaConfig;

    @Inject
    ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        producer = new KafkaProducer<>(kafkaConfig.getProducerProps());
    }

    public void sendMessage(String topic, String key, String value) {
        producer.send(new ProducerRecord<>(topic, key, value), (metadata, exception) -> {
            if (exception == null) {
                System.out.println("Message sent successfully: " + metadata.toString());
            } else {
                exception.printStackTrace();
            }
        });
    }

    @PreDestroy
    public void close() {
        producer.close();
    }
}
