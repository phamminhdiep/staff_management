package com.example.staff_management.example.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExampleBean {
    //@Autowired
    private Dependency dependency;

    public ExampleBean() {
        dependency.print();
    }

    @Autowired
    public ExampleBean(Dependency dependency) {
        this.dependency = dependency;
        dependency.print();
    }

    public void print() {
        dependency.print();
    }

    public Dependency getDependency() {
        return dependency;
    }

//    @Autowired
    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
    }
}
