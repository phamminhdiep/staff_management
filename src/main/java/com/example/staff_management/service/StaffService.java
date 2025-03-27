package com.example.staff_management.service;

import com.example.staff_management.dto.StaffDto;
import com.example.staff_management.entities.Staff;
import com.example.staff_management.event.UserCreatedEvent;
import com.example.staff_management.mapper.StaffMapper;
import com.example.staff_management.repository.StaffRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ModelMapper modelMapper;
    private final StaffMapper staffMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${staff.default.password}")
    private String DEFAULTPASSWORD;


    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Staff getStaffById(int id) {
        return staffRepository.findById(id).orElseThrow(() -> new RuntimeException("Staff not found"));
    }

    public Staff getStaffByEmail(String email) {
        return staffRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Staff not found"));
    }

    @Transactional
    public StaffDto addStaff(StaffDto staffDto) throws MessagingException {
        Staff staff = convertToEntity(staffDto);
        // generate random and numeric checkin code
        DecimalFormat df = new DecimalFormat("0000");
        int number = (int) (Math.random() * 10000);
        String checkinCode = df.format(number);

        if (staffRepository.existsByEmail(staff.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        while (staffRepository.existsByCheckinCode(checkinCode)) {
            number = (int) (Math.random() * 10000);
            checkinCode = df.format(number);
        }
        staff.setCheckinCode(checkinCode);
        staff.setPassword(passwordEncoder.encode(DEFAULTPASSWORD));

        emailService.sendEmailTemplate(staff);


        StaffDto res = convertToDto(staffRepository.save(staff));
//        System.out.println("Main thread name " + Thread.currentThread().getName());
        eventPublisher.publishEvent(new UserCreatedEvent(this, staff));
//        int error = 1/0;
        return res;

    }

    @Transactional
    public StaffDto updateStaff(StaffDto staffDto) {
        Staff staff = convertToEntity(staffDto);
        Staff existingStaff = staffRepository.findByEmail(staff.getEmail())
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        if (existingStaff == null) {
            throw new RuntimeException("Staff not found");
        }

        // update the changed fields
        existingStaff.setFirstName(staff.getFirstName());
        existingStaff.setLastName(staff.getLastName());
        existingStaff.setEmail(staff.getEmail());
        existingStaff.setRole(staff.getRole());

        return convertToDto(staffRepository.save(existingStaff));
    }

    @Transactional
    public void deleteStaff(StaffDto staffDto) {
        Staff existingStaff = staffRepository.findByEmail(staffDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        staffRepository.delete(existingStaff);

    }

    public List<StaffDto> searchStaff(String keyword, String sortDirection, int page, int size) {
        ;

        Sort sort = Sort.by(Sort.Direction.ASC, "firstName", "lastName");

        if (sortDirection != null && sortDirection.equalsIgnoreCase("DESC")) {
            sort = Sort.by(Sort.Direction.DESC, "firstName", "lastName");
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        if (keyword == null) {
            return staffRepository.findAll(pageable)
                    .stream()
                    .map(staffMapper::toDTO)
                    .toList();
        }

        return staffRepository.findByFirstNameContainingOrLastNameContaining(keyword, keyword, pageable)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public StaffDto convertToDto(Staff staff) {
        return modelMapper.map(staff, StaffDto.class);
    }

    public Staff convertToEntity(StaffDto staffDto) {
        return modelMapper.map(staffDto, Staff.class);
    }
}
