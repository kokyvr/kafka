package com.demo.kafka.services.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.demo.kafka.avro.OrderCreatedEvent;
import com.demo.kafka.services.KafkaProducerService;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaTemplate<String, Object> kafkaOrderEvent;

    @Value("${app.topic.name}")
    private String topicName;

    public KafkaProducerServiceImpl( @Qualifier("kafkaTemplate")  KafkaTemplate<String, String> kafkaTemplate,
            @Qualifier("avroKafkaTemplate") KafkaTemplate<String, Object> kafkaOrderEvent) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaOrderEvent = kafkaOrderEvent;
    }

    @Override
    public void enviarMensaje(String mensaje) {
        kafkaTemplate.send(topicName, mensaje);
    }

    @Override
    public void createOrder(String id, OrderCreatedEvent event) {
     kafkaOrderEvent.send("orders", event.getOrderId(), event);
    }
}
