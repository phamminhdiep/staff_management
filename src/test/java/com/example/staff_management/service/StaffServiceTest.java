package com.example.staff_management.service;

import com.example.staff_management.dto.StaffDto;
import com.example.staff_management.entities.Role;
import com.example.staff_management.entities.Staff;
import com.example.staff_management.repository.StaffRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StaffServiceTest {

    @Mock
    private StaffRepository staffRepository;
    @Mock
    private  PasswordEncoder passwordEncoder;
    @Mock
    private  EmailService emailService;
    @Mock
    private  ModelMapper modelMapper;

    @InjectMocks
    private StaffService staffService;


    @Test
    public void testSearchStaff(){
        Staff staff = Staff.builder()
                .firstName("Diep")
                .lastName("Pham")
                .email("abc@gmail.com")
                .role(Role.STAFF)
                .build();

        Staff staff2 = Staff.builder()
                .firstName("An")
                .lastName("Nguyen")
                .email("xyz@gmail.com")
                .role(Role.STAFF)
                .build();

        List<StaffDto> expected = Arrays.asList(modelMapper.map(staff, StaffDto.class));

        Mockito.when(staffService.searchStaff("Diep", "ASC", 0, 1)).thenReturn(expected);

        List<StaffDto> searchResult = staffService.searchStaff("Diep", "ASC", 0, 1);
        Assertions.assertEquals(1, searchResult.size());
        Assertions.assertEquals(expected, searchResult);


    }
}
