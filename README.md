# Spring Boot Kafka Project

This project is a simple example of using Spring Boot with Apache Kafka to send and receive messages.

## Prerequisites

- Docker
- Docker Compose
- Java 17+
- Maven

## Running the Project

This project consists of two parts: the Kafka environment (Zookeeper & Kafka) running in Docker, and the Spring Boot application itself.

### 1. Run the Kafka Environment

This project uses Docker Compose to run Zookeeper and Kafka.

1.  Open a terminal in the project's root directory.
2.  Run the following command to start the containers in detached mode:
    ```bash
    docker-compose up -d
    ```
3.  To stop the containers, run:
    ```bash
    docker-compose down
    ```

### 2. Run the Spring Boot Application

After the Kafka environment is running, you can start the Spring Boot application.

1.  Make sure you are in the project's root directory.
2.  Run the application using the Maven wrapper:
    ```bash
    ./mvnw spring-boot:run
    ```
    On Windows Command Prompt, use:
    ```bash
    mvnw.cmd spring-boot:run
    ```
The application will start on port 8080.

## Sending Messages

This project provides a REST API endpoint to send messages to the Kafka topic.

1.  Make sure the Spring Boot application is running.
2.  Use `curl` or open your browser to access the following endpoint:
    ```bash
    curl -X GET "http://localhost:8080/api/v1/kafka/publish?message=YourMessageHere"
    ```
    Replace `YourMessageHere` with the message you want to send.
3.  You will get a `sent message to the topic` response upon success.

## Application Consumer

This Spring Boot application also includes a Kafka consumer that listens to the `newTopic` topic. When a message is sent to the topic, the consumer will automatically receive it and print it to the application's log.

You can see this in action by checking the console where you are running the Spring Boot application (`./mvnw spring-boot:run`). After sending a message via the API, you should see a log entry similar to this:

```
INFO --- [ntainer#0-0-C-1] com.onedev.kafka.kafka.KafkaConsumer  : Message received -> YourMessageHere
```

## Checking Messages Directly in the Kafka Topic

If you want to verify the messages directly in Kafka, bypassing the application consumer, you can use the `kafka-console-consumer` tool inside the Kafka container.

1.  Make sure the Kafka container is running.
2.  Open a new terminal and run the following command:
    ```bash
    docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic newTopic --from-beginning
    ```
The terminal will display all messages from the `newTopic` topic and will continue to listen for new ones. Press `Ctrl+C` to exit.
