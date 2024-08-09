package com.william.credito.config.feign;


import com.william.credito.infrastructure.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;

@FeignClient(name = "CUENTA-SERVICE", url = "http://localhost:8081/cuenta/account")
public interface AccountFeign {
    @GetMapping
    public ResponseEntity<AccountDTO> getAccount(@RequestParam Long personId);


    @PostMapping("/send-payment")
    public ResponseEntity<String> sendPayment(@RequestParam Long personId,
                                              @RequestParam BigInteger amount,
                                              @RequestParam Long creditId);

}
