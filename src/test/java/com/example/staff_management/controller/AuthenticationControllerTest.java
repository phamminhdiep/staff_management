package com.example.staff_management.controller;

import com.example.staff_management.config.JwtService;
import com.example.staff_management.dto.AuthenticationRequest;
import com.example.staff_management.dto.AuthenticationResponse;
import com.example.staff_management.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(AuthController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private AuthenticationService authenticationService;
    @MockitoBean
    private SecurityFilterChain securityFilterChain;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testLogin() throws Exception {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("hyusieunhan@gmail.com")
                .password("12345678")
                .build();

        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoeXVzaWV1bmhhbk" +
                "BnbWFpbC5jb20iLCJpYXQiOjE3NDI1MjQ0NDYsImV4cCI6MTc0MjUyODA0Nn0" +
                ".FOf9HmbtWNzKLwM1KJ7nOS7fg7UNdkYjdI-DRVr75ww";

        when(authenticationService.authenticate(authenticationRequest)).thenReturn(
                AuthenticationResponse.builder()
                        .accessToken(token)
                        .build());

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(authenticationRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").exists())
                .andExpect(jsonPath("$.access_token").value(token));

    }

    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(post("/api/v1/auth/logout"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Logged out successfully"));
    }
}

