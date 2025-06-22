package com.naruto.fixture;

import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.dto.jutsu.JutsuResponseDto;
import com.naruto.dto.personagem.NovoPersonagemDTO;
import com.naruto.dto.personagem.PersonagemResponseDto;
import java.util.Map;

public class PersonagemFixture {

    public static NovoPersonagemDTO novoDto(String nome, String categoria,int chakra, int vida,  JutsuRequestDto jutsuDto) {
        return new NovoPersonagemDTO(
                "Naruto",
                categoria,
                chakra,
                vida,
                jutsuDto
        );
    }
    public static NovoPersonagemDTO novoDto(String nome, String categoria, JutsuRequestDto jutsuDto) {
        return new NovoPersonagemDTO(
                "Naruto",
                categoria,
                100,
                100,
                jutsuDto
        );
    }

    public static PersonagemResponseDto responseDto(NovoPersonagemDTO dto, Map<String, JutsuResponseDto> jutsusMap ){
        return new PersonagemResponseDto(
                1L,
                dto.nome(),
                dto.categoriaNinja(),
                dto.chakra(),
                dto.vida(),
                jutsusMap
        );
    }
}