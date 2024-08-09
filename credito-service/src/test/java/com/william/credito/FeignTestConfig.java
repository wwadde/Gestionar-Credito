package com.william.credito;


import com.william.credito.config.feign.AccountFeign;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class FeignTestConfig {

    @Bean
    @Primary
    public AccountFeign accountFeign(FeignClientBuilder feignClientBuilder) {
        return feignClientBuilder.forType(AccountFeign.class, "accountFeign").build();
    }
}
