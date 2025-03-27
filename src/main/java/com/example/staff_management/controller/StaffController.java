package com.example.staff_management.controller;

import com.example.staff_management.dto.StaffDto;
import com.example.staff_management.service.CheckinService;
import com.example.staff_management.service.StaffService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@RestController
@RequestMapping("/api/v1/staff")
@CrossOrigin(origins = "http://localhost:4200")
public class StaffController {
    private final StaffService staffService;
    private final CheckinService checkinService;

    @Autowired
    public StaffController(StaffService staffService, CheckinService checkinService, ModelMapper modelMapper) {
        this.staffService = staffService;
        this.checkinService = checkinService;
    }

    //create a new staff
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add-staff")
    public ResponseEntity<?> addStaff(@RequestBody @Valid StaffDto staffDto) {
        try {
            return new ResponseEntity<>((staffService.addStaff(staffDto)), HttpStatus.CREATED);
        }
        catch (RuntimeException | MessagingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get-staffs-checkins")
    public ResponseEntity<?> getAllStaffsCheckins(@RequestParam(required = false) LocalDate startDate,
                                                  @RequestParam(required = false) LocalDate endDate) {

        return ResponseEntity.ok(checkinService.getAllStaffsCheckins(startDate, endDate));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get-staffs-checkins-this-month")
    public ResponseEntity<?> getAllStaffsCheckinsThisMonth() {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        return ResponseEntity.ok(checkinService.getAllStaffsCheckins(startDate, endDate));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/search-staff")
    public ResponseEntity<?> searchStaff(@RequestParam(required = false) String keyword,
                                         @RequestParam(required = false) String sort,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(staffService.searchStaff(keyword, sort, page, size));

    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @PutMapping("/update-staff")
    public ResponseEntity<?> updateStaff(@RequestBody @Valid StaffDto staffDto) {
        try {
            return ResponseEntity.ok(staffService.updateStaff(staffDto));
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete-staff")
    public ResponseEntity<?> deleteStaff(@RequestBody StaffDto staffDto) {
        try {
            staffService.deleteStaff(staffDto);
            return ResponseEntity.ok("Staff deleted successfully");
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get-staffs/{id}")
    public ResponseEntity<?> getStaffById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(staffService.getStaffById(id));
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
