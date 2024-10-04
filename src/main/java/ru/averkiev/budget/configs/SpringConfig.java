package ru.averkiev.budget.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings("unused")
public class SpringConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
