package com.tinusj.assignment.service.impl;

import com.tinusj.assignment.entity.Account;
import com.tinusj.assignment.record.AccountRecord;
import com.tinusj.assignment.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AccountServiceImplTest {
    @Mock
    private AccountRepository accountRepository;

    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accountService = new AccountServiceImpl(accountRepository);
    }

    @Test
    void createAccount_Success() {
        String email = "success@example.com";
        Account newAccount = new Account(1L,"Success User", email, new BigDecimal("100.00"));

        when(accountRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class))).thenReturn(newAccount);

        AccountRecord createdAccount = accountService.createAccount(email, "Success User", new BigDecimal("100.00"));

        assertNotNull(createdAccount);
        assertEquals(email, createdAccount.email());
    }

    @Test
    void createAccount_AccountAlreadyExists_ThrowsRuntimeException() {
        String email = "exists@example.com";
        Account existingAccount = new Account(1L,"Existing User", email, new BigDecimal("200.00"));

        when(accountRepository.findByEmail(email)).thenReturn(Optional.of(existingAccount));

        Exception exception = assertThrows(RuntimeException.class, () ->
                accountService.createAccount(email, "Existing User", new BigDecimal("200.00"))
        );

        assertEquals("Account already exists", exception.getMessage());
    }

    @Test
    void viewAccount_Success() {
        String email = "view@example.com";
        Account existingAccount = new Account(1L,"View User", email, new BigDecimal("300.00"));

        when(accountRepository.findByEmail(email)).thenReturn(Optional.of(existingAccount));

        AccountRecord accountRecord = accountService.viewAccount(email);

        assertNotNull(accountRecord);
        assertEquals(email, accountRecord.email());
    }
    @Test
    void viewAccount_AccountNotFound_ThrowsRuntimeException() {
        String email = "notfound@example.com";

        when(accountRepository.findByEmail(email)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                accountService.viewAccount(email)
        );

        // Assuming a specific message is thrown for not found, otherwise, this can be omitted
        assertEquals("Account not found", exception.getMessage());
    }

}