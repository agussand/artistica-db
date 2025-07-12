package com._5.scaffolding.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Configuration
public class MappersConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean("mergerMapper")
    public ModelMapper mergerMapper() {
        ModelMapper mapper =  new ModelMapper();
        mapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull());

        Converter<BigDecimal, Double> bigDecimalToDoubleConverter = context -> {
            BigDecimal source = context.getSource();
            if (source == null) {
                return null;
            }
            // Redondea el BigDecimal a 2 decimales antes de convertirlo a Double.
            return source.setScale(2, RoundingMode.HALF_UP).doubleValue();
        };

        // 2. Le añadimos el convertidor a esta instancia específica de ModelMapper
        mapper.addConverter(bigDecimalToDoubleConverter);

        return mapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

}
