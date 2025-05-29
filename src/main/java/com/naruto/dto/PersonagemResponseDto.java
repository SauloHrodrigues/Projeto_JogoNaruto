package com.naruto.dto;

import com.naruto.dto.jutsu.JutsuResponseDto;
import com.naruto.model.Jutsu;
import java.util.List;
import java.util.Map;

public record PersonagemResponseDto(
        Long id,
        String nome,
        int chakra,
        int vida,
        List<JutsuResponseDto> jutsus
) {
}
