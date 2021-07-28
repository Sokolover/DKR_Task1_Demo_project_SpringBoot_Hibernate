package com.sokolov.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sokolov_SA
 * @created 15.07.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactDto {

    private Long id;
    private String shortDescription;
    private String fullDescription;
    private Integer rate;
    private String comment;

}
