package com.naruto.mappers;

import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.model.Jutsu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface JutsuMapper {

    JutsuMapper INSTANCE = Mappers.getMapper(JutsuMapper.class);
    Jutsu toEntity(JutsuRequestDto dto);

}