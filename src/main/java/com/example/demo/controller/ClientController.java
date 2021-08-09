package com.example.demo.controller;

import com.example.demo.entity.db.Client;
import com.example.demo.services.BaseService;
import com.example.demo.rabbit.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final BaseService<Client> clientService;
    private final Sender sender;

    @GetMapping
    public ResponseEntity<List<Client>> showAllClient() {
        List<Client> clients = clientService.findAll();

        return clients != null && !clients.isEmpty()
                ? new ResponseEntity<>(clients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> showClientById(@PathVariable Long id) {
        Client client = clientService.findById(id).orElseThrow(NullPointerException::new);

        return client != null
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody Client client) {
        if (client.getAccount_uid() == 0) sender.sendMessage(client);
        else clientService.save(client);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody Client client) {
        sender.sendMessage(client);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Client client = clientService.findById(id).orElseThrow(NullPointerException::new);

        client.setDeleted(true);
        sender.sendMessage(client);
        clientService.delete(client);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/checkValidate")
    public ResponseEntity<?> justTest() {
        List<Client> clients = clientService.findAll();

        for (Client client : clients) {
            if (client.getAccount_uid() == 0) {
                sender.sendMessage(client);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
