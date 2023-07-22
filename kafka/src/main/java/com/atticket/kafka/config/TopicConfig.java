package com.atticket.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

public class TopicConfig {
	@Configuration
	public class kafkaTopicConfig {
		@Bean
		public NewTopic javaGuidesTopic() {
			return TopicBuilder.name("product").partitions(10).build();
		}
	}
}
