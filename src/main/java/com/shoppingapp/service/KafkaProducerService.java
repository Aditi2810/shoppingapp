package com.shoppingapp.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingapp.constants.CommonConstants;

@Service
public class KafkaProducerService {
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public void sendMessageToTopic(String message) {
		kafkaTemplate.send(CommonConstants.Shopping_App_Topic, message);
	}
}
