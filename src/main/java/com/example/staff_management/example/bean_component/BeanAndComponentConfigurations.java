package com.example.staff_management.example.bean_component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanAndComponentConfigurations {

    @Bean
    public SpringBean springBean() {
        return new SpringBean();
    }

}
