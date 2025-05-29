package com.naruto.dto.jutsu;

public record JutsuResponseDto(
        Long id,
        String nome,
        int dano,
        int consumoDeChakra

) {
}
