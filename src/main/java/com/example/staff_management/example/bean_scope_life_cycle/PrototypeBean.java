package com.example.staff_management.example.bean_scope_life_cycle;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PrototypeBean {
    public void print() {
        System.out.println("This is PrototypeBean");
    }
}
