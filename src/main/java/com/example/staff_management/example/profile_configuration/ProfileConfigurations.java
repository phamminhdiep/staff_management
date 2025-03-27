package com.example.staff_management.example.profile_configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileConfigurations {
     @Bean
     @Profile("dev")
     public DevProfileBean devProfileBean() {
         return new DevProfileBean();
     }
}
