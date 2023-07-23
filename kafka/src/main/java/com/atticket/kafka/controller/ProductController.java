package com.atticket.kafka.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.kafka.payload.NotifyData;
import com.atticket.kafka.producer.ProductProducer;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kafka/product")
public class ProductController {

	private final ProductProducer kafkaJsonProducer;

	/**
	 * 상품에 관련 알림 요청을 받는다.
	 * @param notifyData
	 * @return
	 */
	@PostMapping("/notify/mail")
	public ResponseEntity<String> publish(@RequestBody NotifyData notifyData) {
		kafkaJsonProducer.sendMail(notifyData);
		return ResponseEntity.ok("Json Message sent to the topic");
	}

}
