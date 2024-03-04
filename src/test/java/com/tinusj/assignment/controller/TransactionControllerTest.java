package com.tinusj.assignment.controller;

import com.tinusj.assignment.record.TransactionRecord;
import com.tinusj.assignment.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionController = new TransactionController(transactionService);
    }

    @Test
    void transfer_Success() {
        TransactionRecord transactionRecord = new TransactionRecord("source@example.com", "target@example.com", new BigDecimal("100.00"));

        transactionController.transfer(transactionRecord);

        verify(transactionService).transfer(transactionRecord.sourceAccountEmail(), transactionRecord.targetAccountEmail(), transactionRecord.amount());
    }

    @Test
    void transfer_Failure() {
        TransactionRecord transactionRecord = new TransactionRecord("invalid@example.com", "target@example.com", new BigDecimal("100.00"));

        doThrow(new RuntimeException("Invalid account")).when(transactionService)
                .transfer(eq("invalid@example.com"), anyString(), any(BigDecimal.class));

        Exception exception = assertThrows(RuntimeException.class, () ->
                transactionController.transfer(transactionRecord)
        );

        assertEquals("Invalid account", exception.getMessage());
    }
}