package com.naruto.mappers;

import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.dto.jutsu.JutsuResponseDto;
import com.naruto.model.Jutsu;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JutsuMapper {

    Jutsu toEntity(JutsuRequestDto dto);

}