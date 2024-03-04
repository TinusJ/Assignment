package com.tinusj.assignment.controller;

import com.tinusj.assignment.record.AccountRecord;
import com.tinusj.assignment.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


class AccountControllerTest {

    @Mock
    private AccountService accountService;

    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        accountController = new AccountController(accountService);
    }

    @Test
    void createAccount_Success() {
        AccountRecord mockRecord = new AccountRecord("test@example.com", "Test User", BigDecimal.valueOf(1000.0));
        when(accountService.createAccount(anyString(), anyString(), BigDecimal.valueOf(anyDouble()))).thenReturn(mockRecord);

        ResponseEntity<AccountRecord> response = accountController.createAccount(mockRecord);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createAccount_Failure() {
        doThrow(new RuntimeException("Test exception")).when(accountService)
                .createAccount(anyString(), anyString(), any(BigDecimal.class));

        Exception exception = assertThrows(RuntimeException.class, () ->
                accountController.createAccount(new AccountRecord("fail@example.com", "Fail User", BigDecimal.ZERO))
        );

        assertEquals("Test exception", exception.getMessage());
    }

    @Test
    void viewAccount_Success() {
        AccountRecord mockRecord = new AccountRecord( "Test User","test@example.com", BigDecimal.valueOf(1000.0));
        when(accountService.viewAccount(anyString())).thenReturn(mockRecord);

        ResponseEntity<AccountRecord> response = accountController.viewAccount("test@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test User", response.getBody().name());
    }

    @Test
    void viewAccount_NotFound() {
        when(accountService.viewAccount(anyString())).thenThrow(new RuntimeException("Account not found"));

        Exception exception = assertThrows(RuntimeException.class, () ->
                accountController.viewAccount("notfound@example.com"));

        assertEquals("Account not found", exception.getMessage());
    }
}