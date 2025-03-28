package com.example.staff_management.controller;

import com.example.staff_management.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/example")
@RequiredArgsConstructor
public class ExampleController {

    private final ExampleService exampleService;

    @GetMapping("/bean-component")
    public void beans() {
        exampleService.beanAndComponentExample();
    }


    @GetMapping("/profiles")
    public void profiles() {
        exampleService.profileAndConfigExample();
    }

    @GetMapping("/di")
    public void di() {
        exampleService.DIExample();
    }

    @GetMapping("/bean-scopes")
    public void beanScopes() {
        exampleService.beanScopeExample();
    }

    @GetMapping("/lazy-eager")
    public void lazyEager() {
        exampleService.lazyEagerExample();
    }

    @GetMapping("/cascade")
    public void cascade() {
        exampleService.cascadeExample();
    }

    @GetMapping("/mapper")
    public void mapper() {
        exampleService.mapperExample();
    }

    @GetMapping("/native-query")
    public void nativeQuery() {
        exampleService.nativeQueryExample();
    }

    @GetMapping("/jpql")
    public void JPQL() {
        exampleService.JPQLExample();
    }

    @GetMapping("/custom-query-with-contructor")
    public void customQueryWithContructor() {
        exampleService.CustomQueryWithContructorExample();
    }

    @GetMapping("/projection")
    public void projection() {
        exampleService.ProjectionExample();
    }

    @GetMapping("/jointable")
    public void joinTable() {
        exampleService.JoinTableExample();
    }

    @GetMapping("/delayed")
    public String delayed() {
        // simulate a delayed API
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Delayed API";
    }
}
