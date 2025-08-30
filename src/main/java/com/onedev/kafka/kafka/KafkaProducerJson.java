package com.onedev.kafka.kafka;

import com.onedev.kafka.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerJson {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerJson.class);

    @Value("${kafka.topic.json.name}")
    private String topicJsonName;

    private KafkaTemplate<String, UserDto> kafkaTemplate;

    public KafkaProducerJson(KafkaTemplate<String, UserDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(UserDto userDto) {
        logger.info("Sending JSON message to topic: {}", userDto);
        Message<UserDto> message = MessageBuilder
                .withPayload(userDto)
                .setHeader(KafkaHeaders.TOPIC, topicJsonName)
                .build();
        kafkaTemplate.send(message);
    }
}