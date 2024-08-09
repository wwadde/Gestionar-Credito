package com.william.cuenta.infrastructure.controller;

import com.william.cuenta.infrastructure.dto.AccountDTO;
import com.william.cuenta.service.AccountService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @GetMapping
    public ResponseEntity<AccountDTO> getAccount(@RequestParam Long personId) {
        return ResponseEntity.ok(service.getAccount(personId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return ResponseEntity.ok(service.getAllAccounts());
    }

    @PostMapping("/create-account")
    public ResponseEntity<String> createAccount(@RequestParam Long personId, @RequestParam BigInteger balance) {
        return ResponseEntity.ok(service.createAccount(personId, balance));
    }

    @PostMapping("/send-payment")
    public ResponseEntity<String> sendPayment(@RequestParam Long personId,
                                              @RequestParam BigInteger amount,
                                              @RequestParam Long creditId) {
        return ResponseEntity.ok(service.sendPayment(personId, amount, creditId));
    }

    @PutMapping("/update-balance")
    public ResponseEntity<String> updateBalance(@RequestParam Long personId, @RequestParam(name = "addAmount") BigInteger amount) {
        return ResponseEntity.ok(service.updateBalance(personId, amount));
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(@RequestParam Long personId) {
        return ResponseEntity.ok(service.deleteAccount(personId));
    }


}
