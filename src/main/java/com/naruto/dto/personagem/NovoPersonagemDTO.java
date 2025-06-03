package com.naruto.dto.personagem;

import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.enuns.CategoriaNinja;
import com.naruto.model.Jutsu;

public record NovoPersonagemDTO(
        String nome,
        String categoriaNinja,
        int chakra,
        int vida,
        JutsuRequestDto jutsuRequestDto
){}