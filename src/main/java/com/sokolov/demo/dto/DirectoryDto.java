package com.sokolov.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Sokolov_SA
 * @created 15.07.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectoryDto {

    private Long id;
    private String name;
    private String address;
    private String address2;
    private String cityId;
    private String phone;
    private String postcode;
    private String district;
    private Set<FactDto> facts;

}
