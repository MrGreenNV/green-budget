package ru.averkiev.gateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.averkiev.gateway.ampq.RabbitMQProducer;

@RestController
@RequestMapping("/api/v1/ampq")
public class AmpqController {

    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    public AmpqController(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @PostMapping("/send-message")
    public ResponseEntity<HttpStatus> sendMessage(@RequestBody final String request) {
        rabbitMQProducer.sendMessage(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
