package com.william.cuenta.config.mapper;

import com.william.cuenta.domain.model.Account;
import com.william.cuenta.infrastructure.dto.AccountDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MapAccountFactory {

    @Bean
    public Function<Account, AccountDTO> entityToAccountDTO(ModelMapper mapper) {
        return persona -> mapper.map(persona, AccountDTO.class);
    }


}
