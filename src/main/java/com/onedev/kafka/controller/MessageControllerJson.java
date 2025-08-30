package com.onedev.kafka.controller;

import com.onedev.kafka.dto.UserDto;
import com.onedev.kafka.kafka.KafkaProducerJson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kafka")
public class MessageControllerJson {

    private KafkaProducerJson kafkaProducerJson;

    public MessageControllerJson(KafkaProducerJson kafkaProducerJson) {
        this.kafkaProducerJson = kafkaProducerJson;
    }

    @PostMapping("/publishJson")
    public ResponseEntity<String> publishJson(@RequestBody UserDto userDto) {
        kafkaProducerJson.sendMessage(userDto);
        return ResponseEntity.ok("Json message sent to kafka topic");
    }
}