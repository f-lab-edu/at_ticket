package com.atticket.reservation.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import com.atticket.common.kafka.topic.Topic;

public class ReservationTopicConfig {

	@Configuration
	public class ReservationKafaTopic {

		@Bean
		public NewTopic checkReservation() {
			return TopicBuilder.name(Topic.CHECK_RESERVATION.name()).build();
		}

	}

}
