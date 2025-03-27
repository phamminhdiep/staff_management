package com.example.staff_management.example.bean_scope_life_cycle;

import jdk.jfr.Category;
import org.springframework.stereotype.Component;

@Component
public class SingletonBean {
    public void print() {
        System.out.println("This is ExampleBean");
    }
}
