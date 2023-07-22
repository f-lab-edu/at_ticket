package com.atticket.kafka.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.kafka.payload.Product;
import com.atticket.kafka.producer.ProductKafkaProducer;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kafka")
public class MessageController {

	private final ProductKafkaProducer kafkaJsonProducer;

	@PostMapping("/product")
	public ResponseEntity<String> publish(@RequestBody Product product) {
		kafkaJsonProducer.sendMessage(product);
		return ResponseEntity.ok("Json Message sent to the topic");
	}

}
