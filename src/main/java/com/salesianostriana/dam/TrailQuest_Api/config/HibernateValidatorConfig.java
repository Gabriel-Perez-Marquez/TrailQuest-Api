package com.salesianostriana.dam.TrailQuest_Api.config;

import com.salesianostriana.dam.TrailQuest_Api.repository.UserRepository;
import com.salesianostriana.dam.TrailQuest_Api.validation.UniqueEmailValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class HibernateValidatorConfig {

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public UniqueEmailValidator uniqueEmailValidator(UserRepository userRepository) {
        return new UniqueEmailValidator(userRepository);
    }
}
