package com.tinusj.assignment.controller;

import com.tinusj.assignment.record.TransactionRecord;
import com.tinusj.assignment.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    public void transfer(@RequestBody TransactionRecord transactionRecord) {
        log.debug("Transfer money");
        transactionService.transfer(transactionRecord.sourceAccountEmail(), transactionRecord.targetAccountEmail(), transactionRecord.amount());
    }
}
