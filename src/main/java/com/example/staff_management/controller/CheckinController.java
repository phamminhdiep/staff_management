package com.example.staff_management.controller;

import com.example.staff_management.dto.CheckinDto;
import com.example.staff_management.entities.Checkin;
import com.example.staff_management.service.CheckinService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.extras.springsecurity6.util.SpringSecurityContextUtils;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@RestController
@RequestMapping("/api/v1/check-in")
public class CheckinController {
    private final CheckinService checkinService;
    private final ModelMapper modelMapper;

    @Autowired
    public CheckinController(CheckinService checkinService, ModelMapper modelMapper) {
        this.checkinService = checkinService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/check-in")
    public ResponseEntity<?> checkin(Authentication authentication, @RequestParam String checkinCode) {
        try {
            Checkin checkin = checkinService.checkin(authentication, checkinCode);
            return ResponseEntity.ok(convertToDto(checkin));
            // throw the runtime exception message to the client
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/check-out")
    public ResponseEntity<?> checkout(Authentication authentication, @RequestParam String checkinCode) {
        try {
            Checkin checkin = checkinService.checkout(authentication, checkinCode);
            return ResponseEntity.ok(convertToDto(checkin));
            // throw the runtime exception message to the client
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-staff-checkins")
    public ResponseEntity<?> getStaffCheckins(Authentication authentication,
              @RequestParam(required = false) LocalDate startDate,
                      @RequestParam(required = false) LocalDate endDate) {


        return ResponseEntity.ok(checkinService.getStaffCheckins(authentication, startDate, endDate)
                .stream()
                .map(this::convertToDto)
                .toList());
    }

    @GetMapping("/get-staff-checkins-this-month")
    public ResponseEntity<?> getStaffCheckinsThisMonth(Authentication authentication) {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());

        return ResponseEntity.ok(checkinService.getStaffCheckins(authentication, startDate, endDate)
                .stream()
                .map(this::convertToDto)
                .toList());
    }

    private CheckinDto convertToDto(Checkin checkin) {
        return modelMapper.map(checkin, CheckinDto.class);
    }
}
