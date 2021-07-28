package com.sokolov.demo.converter;

import com.sokolov.demo.dto.DaySegmentDto;
import com.sokolov.demo.dto.WorkingDayDto;
import com.sokolov.demo.model.employee.WorkingDay;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Sokolov_SA
 * @created 05.07.2021
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WorkingDayConverter {

    private final ModelMapper modelMapper;
    private final DaySegmentConverter daySegmentConverter;

    public WorkingDayDto convertToDto(WorkingDay workingDay) {
        WorkingDayDto workingDayDto = modelMapper.map(workingDay, WorkingDayDto.class);

        Set<DaySegmentDto> daySegmentDtos = workingDay.getDaySegmentSet().stream()
                .map(daySegmentConverter::convertToDto)
                .collect(Collectors.toSet());
        workingDayDto.setDaySegmentDtos(daySegmentDtos);

        return workingDayDto;
    }
}
