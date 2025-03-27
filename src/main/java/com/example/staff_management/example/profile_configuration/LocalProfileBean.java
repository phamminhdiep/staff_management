package com.example.staff_management.example.profile_configuration;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
public class LocalProfileBean {
    public void print() {
        System.out.println("This is local bean");
    }
}
