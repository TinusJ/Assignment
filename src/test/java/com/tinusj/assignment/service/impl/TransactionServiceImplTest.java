package com.tinusj.assignment.service.impl;

import com.tinusj.assignment.entity.Account;
import com.tinusj.assignment.entity.Transaction;
import com.tinusj.assignment.repository.AccountRepository;
import com.tinusj.assignment.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransactionServiceImplTest {
    public static final String SOURCE_EMAIL = "source@example.com";
    public static final String TARGET_EMAIL = "target@example.com";
    public static final String NONEXISTENT_EMAIL = "nonexistent@example.com";
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionService = new TransactionServiceImpl(accountRepository, transactionRepository);
    }

    @Test
    void transfer_Success() {
        Account sourceAccount = new Account(1L, SOURCE_EMAIL, "Source User", new BigDecimal("200.00"));
        Account targetAccount = new Account(1L, TARGET_EMAIL, "Target User", new BigDecimal("100.00"));

        when(accountRepository.findByEmail(SOURCE_EMAIL)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findByEmail(TARGET_EMAIL)).thenReturn(Optional.of(targetAccount));

        transactionService.transfer(SOURCE_EMAIL, TARGET_EMAIL, new BigDecimal("50.00"));

        assertEquals(new BigDecimal("150.00"), sourceAccount.getBalance());
        assertEquals(new BigDecimal("150.00"), targetAccount.getBalance());
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void transfer_SourceAccountNotFound_ThrowsException() {
        when(accountRepository.findByEmail(NONEXISTENT_EMAIL)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                transactionService.transfer(NONEXISTENT_EMAIL, TARGET_EMAIL, new BigDecimal("50.00"))
        );

        assertEquals("Source account not found", exception.getMessage());
    }

    @Test
    void transfer_InsufficientFunds_ThrowsException() {
        Account sourceAccount = new Account(1L, "Source User", SOURCE_EMAIL, new BigDecimal("30.00"));
        Account targetAccount = new Account(1L, "Target User", TARGET_EMAIL, new BigDecimal("30.00"));

        when(accountRepository.findByEmail(SOURCE_EMAIL)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findByEmail(TARGET_EMAIL)).thenReturn(Optional.of(targetAccount));

        Exception exception = assertThrows(RuntimeException.class, () ->
                transactionService.transfer(SOURCE_EMAIL, TARGET_EMAIL, new BigDecimal("50.00"))
        );

        assertEquals("Insufficient funds", exception.getMessage());
    }
}