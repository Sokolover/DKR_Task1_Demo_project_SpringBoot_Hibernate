package com.sokolov.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sokolov_SA
 * @created 05.07.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DaySegmentDto {

    private Long id;
    private String period;

}
