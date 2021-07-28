package com.sokolov.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Sokolov_SA
 * @created 05.07.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkingDayDto {

    private Long id;
    private String dayName;
    private Set<DaySegmentDto> daySegmentDtos;

}
