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
public class TimesheetDto {

    private Long id;
    private Integer dayAmount;
    private Set<WorkingDayDto> workingDayDtos;

}