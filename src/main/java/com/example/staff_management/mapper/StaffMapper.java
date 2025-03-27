package com.example.staff_management.mapper;

import com.example.staff_management.dto.StaffDto;
import com.example.staff_management.entities.Staff;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StaffMapper {
    Staff toEntity(StaffDto staffDto);
    StaffDto toDTO(Staff staff);
}
