package com.demo.kafka.services;

import com.demo.kafka.avro.OrderCreatedEvent;

public interface KafkaProducerService {
    
     public void enviarMensaje(String mensaje);

     public void createOrder(String id,OrderCreatedEvent event);
     
}
