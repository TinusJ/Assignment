package com.tinusj.assignment.record;

import java.math.BigDecimal;

public record TransactionRecord(String sourceAccountEmail, String targetAccountEmail, BigDecimal amount) {
}