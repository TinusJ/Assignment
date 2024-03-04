package com.tinusj.assignment.controller;


import com.tinusj.assignment.record.AccountRecord;
import com.tinusj.assignment.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountRecord> createAccount(@RequestBody AccountRecord accountRecord) {
        log.debug("Creating account");
        AccountRecord account = accountService.createAccount(accountRecord.email(), accountRecord.name(), accountRecord.balance());

        return ResponseEntity.ok(account);
    }

    @GetMapping("/{email}")
    public ResponseEntity<AccountRecord> viewAccount(@PathVariable String email) {
        log.debug("Viewing account");
        AccountRecord account = accountService.viewAccount(email);

        return ResponseEntity.ok(account);
    }
}
