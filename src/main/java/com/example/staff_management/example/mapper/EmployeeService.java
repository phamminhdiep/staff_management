package com.example.staff_management.example.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeService {

    @Qualifier("employeeMapperImpl")
    private final EmployeeMapper mapper;
    private final ModelMapper modelMapper;

    public EmployeeDto convertToDto(Employee employee) {
        return mapper.employeeToEmployeeDTO(employee);
    }

    public Employee convertToEntity(EmployeeDto employeeDto) {
        return mapper.employeeDTOToEmployee(employeeDto);
    }

    public EmployeeDto convertToDtoUsingModelMapper(Employee employee) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(employee, EmployeeDto.class);
    }
}
