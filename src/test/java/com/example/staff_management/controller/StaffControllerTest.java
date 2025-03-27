package com.example.staff_management.controller;

import com.example.staff_management.config.JwtService;
import com.example.staff_management.dto.StaffDto;
import com.example.staff_management.entities.Role;
import com.example.staff_management.service.CheckinService;
import com.example.staff_management.service.StaffService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StaffController.class)
public class StaffControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private StaffService staffService;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private ModelMapper modelMapper;
    @MockitoBean
    private SecurityFilterChain securityFilterChain;
    @MockitoBean
    private CheckinService checkinService;


    @Test
    void testAddStaff() throws Exception {
        StaffDto staffDto = StaffDto.builder()
                .email("abc@gmail.com")
                .firstName("Diep")
                .lastName("Pham")
                .role(Role.STAFF)
                .build();

        when(staffService.addStaff(staffDto)).thenReturn(staffDto);

        mockMvc.perform(post("/api/v1/staff/add-staff")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(staffDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("abc@gmail.com")); // Kiểm tra response JSON
    }
}
