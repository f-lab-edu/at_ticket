package com.atticket.product.client.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.atticket.product.client.dto.PostNotifyProductToMailDto;

@FeignClient(name = "KafkaFeignClient", url = "${api.kafka.url}:${api.kafka.port}/kafka/product")
public interface KafkaFeignClient {
	@PostMapping(value = "/notify/mail")
	String postNotifyProductToMail(PostNotifyProductToMailDto postNotifyProductToMailDto);
}
