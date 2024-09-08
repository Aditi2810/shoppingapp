package com.shoppingapp.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

	@KafkaListener(topics = "shopping_app_topic", groupId = "shopping-app-group")
	public void listenToTopic(String messageReceived) {
		System.out.println("Message received is " + messageReceived);
	}
}
