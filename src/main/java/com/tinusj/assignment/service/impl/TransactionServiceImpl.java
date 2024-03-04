package com.tinusj.assignment.service.impl;

import com.tinusj.assignment.entity.Account;
import com.tinusj.assignment.entity.Transaction;
import com.tinusj.assignment.repository.AccountRepository;
import com.tinusj.assignment.repository.TransactionRepository;
import com.tinusj.assignment.service.TransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void transfer(String fromEmail, String toEmail, BigDecimal amount) {
        Account sourceAccount = accountRepository.findByEmail(fromEmail)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        Account targetAccount = accountRepository.findByEmail(toEmail)
                .orElseThrow(() -> new RuntimeException("Target account not found"));

        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        targetAccount.setBalance(targetAccount.getBalance().add(amount));

        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        Transaction transaction = new Transaction();
        transaction.setSourceAccountId(sourceAccount.getId());
        transaction.setTargetAccountId(targetAccount.getId());
        transaction.setAmount(amount);

        transactionRepository.save(transaction);
    }
}
