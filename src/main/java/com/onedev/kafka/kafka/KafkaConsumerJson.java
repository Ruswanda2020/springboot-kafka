package com.onedev.kafka.kafka;

import com.onedev.kafka.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerJson {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerJson.class);

    @Value("${kafka.topic.json.name}")
    private String topicJsonName;

    @Value("${spring.kafka.consumer.group-id-json}")
    private String groupIdJson;

    @KafkaListener(topics = "${kafka.topic.json.name}", groupId = "${spring.kafka.consumer.group-id-json}", containerFactory = "userKafkaListenerContainerFactory")
    public void consumeJson(UserDto userDto) {
        LOGGER.info("User object received from JSON topic -> {}", userDto);
    }
}