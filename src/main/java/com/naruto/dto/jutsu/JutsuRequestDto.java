package com.naruto.dto.jutsu;

public record JutsuRequestDto(
        String nome,
        int dano,
        int consumoDeChakra
) {
}
