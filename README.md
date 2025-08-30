# Spring Boot Kafka Project

This project demonstrates a Spring Boot application integrated with Apache Kafka for both string and JSON message processing. It includes separate producers and consumers for each message type, along with REST API endpoints for easy interaction.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Running the Project](#running-the-project)
  - [1. Run the Kafka Environment](#1-run-the-kafka-environment)
  - [2. Run the Spring Boot Application](#2-run-the-spring-boot-application)
- [Project Structure Overview](#project-structure-overview)
- [Data Transfer Object (DTO)](#data-transfer-object-dto)
- [API Endpoints](#api-endpoints)
  - [1. Sending String Messages](#1-sending-string-messages)
  - [2. Sending JSON Messages](#2-sending-json-messages)
- [Kafka Consumers](#kafka-consumers)
  - [1. String Message Consumer](#1-string-message-consumer)
  - [2. JSON Message Consumer](#2-json-message-consumer)
- [Checking Messages Directly in Kafka](#checking-messages-directly-in-kafka)

## Prerequisites

To run this project, you need the following installed:

-   **Docker** & **Docker Compose**: For running the Kafka environment.
-   **Java 17+**: The application is built with Java 17.
-   **Maven**: For building and running the Spring Boot application.

## Running the Project

The project consists of two main parts: the Kafka environment (Zookeeper & Kafka) managed by Docker, and the Spring Boot application.

### 1. Run the Kafka Environment

The Kafka environment is set up using Docker Compose.

1.  Open a terminal in the project's root directory.
2.  Start the Zookeeper and Kafka containers in detached mode:
    ```bash
    docker-compose up -d
    ```
3.  To stop and remove the containers, run:
    ```bash
    docker-compose down
    ```

### 2. Run the Spring Boot Application

Once the Kafka environment is up and running, you can start the Spring Boot application.

1.  Ensure you are in the project's root directory.
2.  Run the application using the Maven wrapper:
    ```bash
    ./mvnw spring-boot:run
    ```
    For Windows Command Prompt, use:
    ```bash
    mvnw.cmd spring-boot:run
    ```
The application will start on `http://localhost:8080`.

## Project Structure Overview

-   `src/main/java/com/onedev/kafka/kafka/KafkaProducer.java`: Sends string messages to Kafka.
-   `src/main/java/com/onedev/kafka/kafka/KafkaConsumer.java`: Consumes string messages from Kafka.
-   `src/main/java/com/onedev/kafka/kafka/KafkaProducerJson.java`: Sends `UserDto` objects as JSON messages to Kafka.
-   `src/main/java/com/onedev/kafka/kafka/KafkaConsumerJson.java`: Consumes `UserDto` objects from JSON Kafka topic.
-   `src/main/java/com/onedev/kafka/controller/MessageController.java`: REST controller for sending string messages.
-   `src/main/java/com/onedev/kafka/controller/MessageControllerJson.java`: REST controller for sending JSON messages.
-   `src/main/java/com/onedev/kafka/config/KafkaTopicConfig.java`: Configures Kafka topics (`newTopic` and `newTopicJson`).
-   `src/main/java/com/onedev/kafka/config/KafkaJsonConfig.java`: Configures Kafka consumer factory for JSON deserialization.
-   `src/main/java/com/onedev/kafka/dto/UserDto.java`: Data Transfer Object for user information (used in JSON messages).
-   `src/main/resources/application.properties`: Application and Kafka configuration properties.

## Data Transfer Object (DTO)

The `UserDto` class is used to represent user data when sending and receiving JSON messages.

```java
package com.onedev.kafka.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
}
```

## API Endpoints

The application exposes two REST API endpoints for sending messages to Kafka.

### 1. Sending String Messages

-   **Endpoint**: `GET http://localhost:8080/api/v1/kafka/publish`
-   **Description**: Sends a simple string message to the `newTopic` Kafka topic.
-   **Example `curl` command**:
    ```bash
    curl "http://localhost:8080/api/v1/kafka/publish?message=hello%20kafka"
    ```
-   **Expected Response**: `Message sent to the topic`

### 2. Sending JSON Messages

-   **Endpoint**: `POST http://localhost:8080/api/v1/kafka/publishJson`
-   **Description**: Sends a `UserDto` object as a JSON message to the `newTopicJson` Kafka topic.
-   **Example `curl` command**:
    ```bash
    curl -X POST "http://localhost:8080/api/v1/kafka/publishJson" \
         -H "Content-Type: application/json" \
         -d '{"id": "1", "firstName": "John", "lastName": "Doe"}'
    ```
-   **Expected Response**: `Json message sent to kafka topic`

## Kafka Consumers

The application includes two Kafka consumers, each listening to a different topic.

### 1. String Message Consumer

-   **Topic**: `newTopic`
-   **Description**: This consumer (`KafkaConsumer.java`) listens for string messages. When a message is received, it logs the content to the application console.
-   **Expected Log Output (after sending a string message)**:
    ```
    INFO --- [ntainer#0-0-C-1] com.onedev.kafka.kafka.KafkaConsumer  : Message received -> YourMessageHere
    ```

### 2. JSON Message Consumer

-   **Topic**: `newTopicJson`
-   **Description**: This consumer (`KafkaConsumerJson.java`) listens for JSON messages, deserializes them into `UserDto` objects, and logs the user object to the application console.
-   **Expected Log Output (after sending a JSON message)**:
    ```
    INFO --- [ntainer#0-0-C-1] com.onedev.kafka.kafka.KafkaConsumerJson  : User object received from JSON topic -> UserDto(id=1, firstName=John, lastName=Doe)
    ```

## Checking Messages Directly in Kafka

You can verify messages directly in Kafka using the `kafka-console-consumer` tool within the Kafka container.

1.  Ensure the Kafka container is running (`docker-compose up -d`).
2.  Open a new terminal and run one of the following commands:
    -   **For `newTopic` (String Messages)**:
        ```bash
        docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic newTopic --from-beginning
        ```
    -   **For `newTopicJson` (JSON Messages)**:
        ```bash
        docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic newTopicJson --from-beginning
        ```
The terminal will display all messages from the specified topic and will continue to listen for new ones. Press `Ctrl+C` to exit.
