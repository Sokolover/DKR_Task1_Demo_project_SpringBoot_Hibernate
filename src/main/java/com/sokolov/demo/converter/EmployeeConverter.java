package com.sokolov.demo.converter;

import com.sokolov.demo.dto.EmployeeDto;
import com.sokolov.demo.model.employee.Employee;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sokolov_SA
 * @created 05.07.2021
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EmployeeConverter {

    private final ModelMapper modelMapper;
    private final TimesheetConverter timesheetConverter;

    public EmployeeDto convertToDto(Employee employee) {
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        employeeDto.setTimesheetDto(timesheetConverter.convertToDto(employee.getTimesheet()));
        return employeeDto;
    }

}
