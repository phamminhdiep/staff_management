package com.example.staff_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckinDto {
    private LocalTime checkinTime;
    private LocalTime checkoutTime;
    private LocalDate date;

}
