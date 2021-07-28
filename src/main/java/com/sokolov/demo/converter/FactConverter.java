package com.sokolov.demo.converter;

import com.sokolov.demo.dto.FactDto;
import com.sokolov.demo.model.directory.lazy.FactLazy;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sokolov_SA
 * @created 15.07.2021
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FactConverter {

    private final ModelMapper modelMapper;

    public FactDto convertToDto(FactLazy fact) {
        return modelMapper.map(fact, FactDto.class);
    }

}
