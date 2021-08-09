package com.example.demo.services;

import com.example.demo.entity.db.Client;
import com.example.demo.repository.BaseRepository;
import com.example.demo.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService extends BaseAbstractService<Client>{
    private final ClientRepository clientRepository;

    @Override
    protected BaseRepository<Client> getEntityRepository() {
        return clientRepository;
    }
}
