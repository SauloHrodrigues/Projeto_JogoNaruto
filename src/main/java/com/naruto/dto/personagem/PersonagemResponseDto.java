package com.naruto.dto.personagem;

import com.naruto.dto.jutsu.JutsuResponseDto;
import java.util.Map;

public record PersonagemResponseDto(
        Long id,
        String nome,
        String categoriaNinja,
        int idade,

        int chakra,
        int vida,
        Map<String, JutsuResponseDto> jutsus
) {
}
