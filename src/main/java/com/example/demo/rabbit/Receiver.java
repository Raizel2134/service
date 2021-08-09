package com.example.demo.rabbit;

import com.example.demo.entity.db.Client;
import com.example.demo.services.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Receiver {
    private final static String QUEUE_NAME = "account";
    private final BaseService<Client> clientService;

    @RabbitListener(queues = QUEUE_NAME)
    public void receivingMessage(Client client) {
        clientService.save(client);
    }
}
