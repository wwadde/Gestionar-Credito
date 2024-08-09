package com.william.credito.infrastructure.controller;


import com.william.credito.infrastructure.dto.CreateCreditDTO;
import com.william.credito.infrastructure.dto.CreditDTO;
import com.william.credito.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;


@RestController
@RequestMapping("/credit")
@RequiredArgsConstructor
public class CreditController {

    private final CreditService service;

    @GetMapping("/get-credit")
    public ResponseEntity<CreditDTO> getCredit(@RequestParam(name = "PersonaId") Long id) {
        return ResponseEntity.ok(service.getCredit(id));
    }

    @PostMapping("/create-credit")
    public ResponseEntity<String> createCredit(@RequestBody CreateCreditDTO creditDTO,
                                               @RequestParam(name = "PersonId") Long id) {
        return ResponseEntity.ok(service.createCredit(creditDTO, id));
    }

    @PutMapping("/payment")
    public ResponseEntity<String> payment(@RequestParam(name = "PersonId") Long id,
                                          @RequestParam(name = "Amount") BigInteger amount,
                                          @RequestParam(name = "CreditId") Long creditId) {
        return ResponseEntity.ok(service.sendPayment(id, amount, creditId));
    }

    @DeleteMapping("/delete-credit")
    public ResponseEntity<String> deleteCredit(@RequestParam(name = "PersonId") Long id,
                                               @RequestParam(name = "CreditId") Long creditId) {
        return ResponseEntity.ok(service.deleteCredit(creditId));
    }


}
