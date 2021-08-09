package com.example.account.rabbit;

import com.example.account.entity.Client;
import com.example.account.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Receiver {
    private final static String QUEUE_NAME = "client";

    private final Sender sender;
    private final AccountService accountService;

    @RabbitListener(queues = QUEUE_NAME)
    public void receivingMessage(Client client) {
        if (client.isDeleted()){
            accountService.delete(accountService.getAccountIdByGuid(client.getGuid()));
        } else {
            client.setAccount_uid((long) accountService.getAccountIdByGuid(client.getGuid()).getAccount_uid());
            sender.sendMessage(client);
        }
    }
}
