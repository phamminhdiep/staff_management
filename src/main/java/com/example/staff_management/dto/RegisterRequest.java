package com.example.staff_management.dto;

import com.example.staff_management.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RegisterRequest {
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String role;
}
