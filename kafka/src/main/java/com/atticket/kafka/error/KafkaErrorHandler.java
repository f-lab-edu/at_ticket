package com.atticket.kafka.error;

import org.apache.kafka.clients.consumer.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service(value = "kafkaErrorHandler")
public class KafkaErrorHandler implements ConsumerAwareListenerErrorHandler {

	private static final Logger LOG = LoggerFactory.getLogger(KafkaErrorHandler.class);

	@Override
	public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
		LOG.warn("Reservation error : {}, because : {}", message.getPayload(), exception.getMessage());

		return null;
	}

}
