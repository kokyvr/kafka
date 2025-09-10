package com.demo.kafka.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.kafka.avro.OrderCreatedEvent;
import com.demo.kafka.services.KafkaProducerService;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final KafkaProducerService producerService;

    public EventoController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public ResponseEntity<String> publicar(@RequestBody String mensaje) {
        producerService.enviarMensaje(mensaje);
        return ResponseEntity.ok("Mensaje enviado a Kafka");
    }

    @PostMapping("createOrder")
    public ResponseEntity<String> createOrder(@RequestBody OrderCreatedEvent request) {
        OrderCreatedEvent event = new OrderCreatedEvent(
                request.getOrderId(),
                request.getUserId(),
                request.getAmount(),
                request.getTimestamp());
        producerService.createOrder(request.getOrderId(), event);
        return ResponseEntity.ok("Order Avro enviado a Kafka ");
    }
}
