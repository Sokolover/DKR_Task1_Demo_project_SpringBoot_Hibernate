package com.sokolov.demo.converter;

import com.sokolov.demo.dto.TimesheetDto;
import com.sokolov.demo.dto.WorkingDayDto;
import com.sokolov.demo.model.employee.Timesheet;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Sokolov_SA
 * @created 05.07.2021
 */
@Component
public class TimesheetConverter {

    private final ModelMapper modelMapper;
    private final WorkingDayConverter workingDayConverter;

    public TimesheetConverter(ModelMapper modelMapper, @Lazy WorkingDayConverter workingDayConverter) {
        this.modelMapper = modelMapper;
        this.workingDayConverter = workingDayConverter;
    }

    public TimesheetDto convertToDto(Timesheet timesheet) {
        TimesheetDto timesheetDto = modelMapper.map(timesheet, TimesheetDto.class);

        Set<WorkingDayDto> workingDayDtos = timesheet.getWorkingDaySet().stream()
                .map(workingDayConverter::convertToDto)
                .collect(Collectors.toSet());
        timesheetDto.setWorkingDayDtos(workingDayDtos);

        return timesheetDto;
    }
}
