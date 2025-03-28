package com.example.staff_management.service;

import com.example.staff_management.dto.CheckinDto;
import com.example.staff_management.dto.StaffCheckinsDto;
import com.example.staff_management.dto.StaffDto;
import com.example.staff_management.entities.Checkin;
import com.example.staff_management.entities.Staff;
import com.example.staff_management.repository.CheckinRepository;
import com.example.staff_management.repository.StaffRepository;
import com.example.staff_management.repository.projection.StaffCheckinProjection;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class CheckinService {
    private final CheckinRepository checkinRepository;
    private final StaffRepository staffRepository;
    private final EmailService emailService;
    private final ModelMapper modelMapper;


    public Checkin checkin(Authentication authentication, String checkinCode) {

        // get the current user
        Staff staff = (Staff) authentication.getPrincipal();
        // check if staff is null
        if (staff == null) {
            throw new RuntimeException("Staff not found");
        }
        // check if checkin code is correct
        if (!checkinCode.equals(staff.getCheckinCode())) {
            throw new RuntimeException("Wrong checkin code");
        }
        // get the current time
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String currentFormattedTime = now.format(formatter);

        // check if the staff has already checked in today
        Optional<Checkin> todayCheckin = checkinRepository.findByStaffIdAndDate(staff.getId(), LocalDate.now());
        if (todayCheckin.isPresent() && todayCheckin.get().getCheckinTime() != null) {
            throw new RuntimeException("Staff has already checked in today");
        }
        // create checkin object
        Checkin checkin = Checkin.builder()
                .staff(staff)
                .checkinTime(LocalTime.parse(currentFormattedTime, formatter))
                .date(LocalDate.now())
                .build();

        // save checkin object
        return checkinRepository.save(checkin);
    }

    // checkout
    public Checkin checkout(Authentication authentication, String checkinCode) {
        // get staff by id
        // get the current user
        Staff staff = (Staff) authentication.getPrincipal();
        // check if staff is null
        if (staff == null) {
            throw new RuntimeException("Staff not found");
        }
        // check if checkin code is correct
        if (!checkinCode.equals(staff.getCheckinCode())) {
            throw new RuntimeException("Wrong checkin code");
        }
        // get the current time
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String currentFormattedTime = now.format(formatter);

        // check if the staff has already checked in today
        Optional<Checkin> todayCheckin = checkinRepository.findByStaffIdAndDate(staff.getId(), LocalDate.now());
        if (todayCheckin.isEmpty()) {
            throw new RuntimeException("Staff has not checked in today");
        }
        // check if the staff has already checked out today
        if (todayCheckin.get().getCheckoutTime() != null) {
            throw new RuntimeException("Staff has already checked out today");
        }
        // update checkin object
        Checkin checkin = todayCheckin.get();
        checkin.setCheckoutTime(LocalTime.parse(currentFormattedTime, formatter));

        // save checkin object
        return checkinRepository.save(checkin);
    }

    public List<Checkin> getStaffCheckins(Authentication authentication, LocalDate startDate, LocalDate endDate) {
        // get the current user
        Staff staff = (Staff) authentication.getPrincipal(); // get the current user
        // check if staff is null
        if (staff == null) {
            throw new RuntimeException("Staff not found");
        }
        if (startDate == null || endDate == null) {
            // Lấy ngày hiện tại
            LocalDate today = LocalDate.now();

            // Tìm thứ Hai đầu tuần (tuần hiện tại)
            startDate = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

            // Tìm Chủ Nhật cuối tuần (thứ Hai + 6 ngày)
            endDate = startDate.plusDays(6);
        }
        List<Checkin> checkins = checkinRepository.findByStaffIdAndDateBetween(staff.getId(), startDate, endDate);
        // loop through the days between start date and end date
        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            // check if the checkin record for the day is not found
            LocalDate specificDate = date;
            if (checkins.stream().noneMatch(checkin -> checkin.getDate().equals(specificDate))) {
                // create an empty checkin object
                Checkin emptyCheckin = Checkin.builder()
                        .date(date)
                        .build();
                // add the empty checkin object to the list
                checkins.add(emptyCheckin);
            }
        }
        // sort the checkins by date
        checkins.sort(Comparator.comparing(Checkin::getDate));
        return checkins;
    }

    @Scheduled(cron = "0 00 20 * * MON-FRI") // 8:00 PM every Monday to Friday
    public void sendForgetCheckinAndCheckoutEmail() {
        List<StaffCheckinProjection> staffCheckins = staffRepository.findStaffWithoutCheckinOrCheckoutToday()
                .stream()
                .filter(staff -> staff.getEmail()!=null)
                .toList();

        for (StaffCheckinProjection staffCheckin : staffCheckins) {
            if (staffCheckin.getCheckinId() == null || staffCheckin.getCheckinTime() == null) {
                emailService.sendEmail(staffCheckin.getEmail(), "Forget Checkin"
                                + LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")),
                        "You have not checked in today, "
                                + LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
            }
            else if (staffCheckin.getCheckoutTime() == null) {
                emailService.sendEmail(staffCheckin.getEmail(), "Forget Checkout"
                                + LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")),
                        "You have not checked out today, "
                                + LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
            }

        }


    }


    public List<StaffCheckinsDto> getAllStaffsCheckins(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            // Lấy ngày hiện tại
            LocalDate today = LocalDate.now();

            // Tìm thứ Hai đầu tuần (tuần hiện tại)
            startDate = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

            // Tìm Chủ Nhật cuối tuần (thứ Hai + 6 ngày)
            endDate = startDate.plusDays(6);
        }
        List<StaffCheckinsDto> staffCheckinsDto = new ArrayList<>();

        // get all staffs
        List<Staff> staffs = staffRepository.findAll();
        for(Staff staff: staffs){
            // get all checkins between start date and end date
            List<CheckinDto> checkins = new ArrayList<>(checkinRepository
                    .findByStaffIdAndDateBetween(staff.getId(), startDate, endDate)
                    .stream()
                    .map(this::convertToDto)
                    .toList());
            // loop through the days between start date and end date
            for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
                // check if the checkin record for the day is not found
                LocalDate specificDate = date;
                if (checkins.stream().noneMatch(checkin -> checkin.getDate().equals(specificDate))) {
                    // create an empty checkin object
                    Checkin emptyCheckin = Checkin.builder()
                            .date(date)
                            .build();
                    // add the empty checkin object to the list
                    checkins.add(convertToDto(emptyCheckin));
                }
            }
            // sort the checkins by date
            checkins.sort(Comparator.comparing(CheckinDto::getDate));
            // set the checkins to the staff
            staffCheckinsDto.add(
                    StaffCheckinsDto.builder()
                            .staff(convertToDto(staff))
                            .checkins(checkins)
                            .build()
            );

        }

        return staffCheckinsDto;
    }

    private StaffDto convertToDto(Staff staff) {
        return modelMapper.map(staff, StaffDto.class);
    }

    private CheckinDto convertToDto(Checkin checkin) {
        return modelMapper.map(checkin, CheckinDto.class);
    }
}
