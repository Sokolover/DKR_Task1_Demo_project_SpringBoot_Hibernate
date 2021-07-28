package com.sokolov.demo.converter;

import com.sokolov.demo.dto.DirectoryDto;
import com.sokolov.demo.dto.FactDto;
import com.sokolov.demo.model.directory.lazy.DirectoryLazy;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Sokolov_SA
 * @created 15.07.2021
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DirectoryConverter {

    private final ModelMapper modelMapper;
    private final FactConverter factConverter;

    public DirectoryDto convertToDto(DirectoryLazy directory) {
        DirectoryDto directoryDto = modelMapper.map(directory, DirectoryDto.class);

        Set<FactDto> factDtos = directory.getFactLazies().stream()
                .map(factConverter::convertToDto)
                .collect(Collectors.toSet());
        directoryDto.setFacts(factDtos);

        return directoryDto;
    }

}
