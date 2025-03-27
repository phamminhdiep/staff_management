package com.example.staff_management.example.bean_scope_life_cycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class BeanLifeCycle {
    @PostConstruct
    public void init() {
        System.out.println("Post construction of BeanLifeCycle");
    }
    @PreDestroy
    public void destroy() {
        System.out.println("Pre destruction of BeanLifeCycle");
    }

}
