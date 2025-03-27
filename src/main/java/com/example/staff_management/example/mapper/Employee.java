package com.example.staff_management.example.mapper;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {
    private int id;
    private String name;
    private String phone;
    private String email;
}
