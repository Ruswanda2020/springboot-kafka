package com.onedev.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.name}")
    private String topicName;

    @Value("${kafka.topic.json.name}")
    private String topicJsonName;

    @Bean
    public NewTopic newTopic() {
        return TopicBuilder.name(topicName)
                .build();

    }

    @Bean
    public NewTopic newTopicJson() {
        return TopicBuilder.name(topicJsonName)
                .build();
    }
}
