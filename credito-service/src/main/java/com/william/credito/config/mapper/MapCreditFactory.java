package com.william.credito.config.mapper;



import com.william.credito.domain.model.Credit;
import com.william.credito.infrastructure.dto.CreateCreditDTO;
import com.william.credito.infrastructure.dto.CreditDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MapCreditFactory {

    @Bean
    public Function<Credit, CreditDTO> entityToCreditDTO(ModelMapper mapper) {
        return persona -> mapper.map(persona, CreditDTO.class);
    }

    @Bean
    public Function<CreateCreditDTO, Credit> dtoToCreditEntity(ModelMapper mapper) {
        return persona -> mapper.map(persona, Credit.class);
    }


}
