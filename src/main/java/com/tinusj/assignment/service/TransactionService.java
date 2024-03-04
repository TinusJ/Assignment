package com.tinusj.assignment.service;

import java.math.BigDecimal;

public interface TransactionService {
    void transfer(String fromEmail, String toEmail, BigDecimal amount);
}
