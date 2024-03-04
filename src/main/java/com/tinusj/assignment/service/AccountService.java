package com.tinusj.assignment.service;

import com.tinusj.assignment.record.AccountRecord;

import java.math.BigDecimal;

public interface AccountService {

    AccountRecord createAccount(String email, String name, BigDecimal balance);

    AccountRecord viewAccount(String email);
}
