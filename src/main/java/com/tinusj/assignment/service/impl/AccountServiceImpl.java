package com.tinusj.assignment.service.impl;

import com.tinusj.assignment.entity.Account;
import com.tinusj.assignment.record.AccountRecord;
import com.tinusj.assignment.repository.AccountRepository;
import com.tinusj.assignment.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountRecord createAccount(String email, String name, BigDecimal balance) {

        if (accountRepository.findByEmail(email).isEmpty()) {
            Account acc = accountRepository.save(new Account(null, name, email, balance));

            return new AccountRecord(acc.getName(), acc.getEmail(), acc.getBalance());
        }

        throw new RuntimeException("Account already exists");
    }

    @Override
    public AccountRecord viewAccount(String email) {

        return accountRepository.findByEmail(email)
                .map(account -> new AccountRecord(account.getName(), account.getEmail(), account.getBalance()))
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
}
