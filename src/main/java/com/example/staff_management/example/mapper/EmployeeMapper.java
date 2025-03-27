package com.example.staff_management.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EmployeeMapper {
    @Mapping(source = "email", target = "employeeEmail")
    @Mapping(source = "name", target = "employeeName")
    EmployeeDto employeeToEmployeeDTO(Employee employee);
    @Mapping(source = "employeeName", target = "name")
    @Mapping(source = "employeeEmail", target = "email")
    Employee employeeDTOToEmployee(EmployeeDto employeeDTO);
}
