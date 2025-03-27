package com.example.staff_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffCheckinsDto {
    private StaffDto staff;
    private List<CheckinDto> checkins;
}
