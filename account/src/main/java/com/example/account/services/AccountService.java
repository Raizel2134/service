package com.example.account.services;

import com.example.account.entity.Account;
import com.example.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private Random random = new Random();

    public Account getAccountIdByGuid(int guid) {
        Account account = accountRepository.findByGuid(guid);
        if (account == null){
            Account accountNew = new Account();
            accountNew.setGuid(guid);
            accountNew.setAccount_uid(random.nextInt(200000) + 1);
            accountRepository.save(accountNew);
            return accountNew;
        }
        return account;
    }

    public void delete(Account account){
        accountRepository.delete(account);
    }
}
