package com.sokolov.demo.converter;

import com.sokolov.demo.dto.DaySegmentDto;
import com.sokolov.demo.model.employee.DaySegment;
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
public class DaySegmentConverter {

    private final ModelMapper modelMapper;

    public DaySegmentDto convertToDto(DaySegment daySegment) {
        return modelMapper.map(daySegment, DaySegmentDto.class);
    }

}
