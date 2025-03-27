package com.example.staff_management.example.di;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class Dependency {
    public void print() {
        System.out.println("Dependency is injected");
    }
}
