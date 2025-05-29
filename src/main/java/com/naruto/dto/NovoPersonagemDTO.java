package com.naruto.dto;

import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.enuns.CategoriaNinja;
import com.naruto.model.Jutsu;

public record NovoPersonagemDTO(
        String nome,
        CategoriaNinja categoriaNinja,
        int chakra,
        int vida,
        JutsuRequestDto jutsuRequestDto
){}