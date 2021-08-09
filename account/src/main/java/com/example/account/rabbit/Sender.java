package com.example.account.rabbit;

import com.example.account.entity.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Sender {
    private final static String QUEUE_NAME = "account";
    private final ObjectMapper objectMapperSender;
    private final RabbitTemplate rabbitTemplate;

    void sendMessage(Client client) {
        try {
            String objectJson = objectMapperSender.writeValueAsString(client);
            Message message = getMessage(objectJson);
            this.rabbitTemplate.convertAndSend(QUEUE_NAME, message);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private Message getMessage(String objectJson) {
        return MessageBuilder
                .withBody(objectJson.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
    }
}
