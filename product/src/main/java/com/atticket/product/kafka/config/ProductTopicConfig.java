package com.atticket.product.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import com.atticket.common.kafka.topic.Topic;

public class ProductTopicConfig {

	@Configuration
	public class productKafaTopic {

		@Bean
		public NewTopic mailSender() {
			return TopicBuilder.name(Topic.NOTIFY_MAIL.name()).build();
		}

	}

}
