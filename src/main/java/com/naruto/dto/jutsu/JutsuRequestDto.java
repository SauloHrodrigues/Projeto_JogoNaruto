package com.naruto.dto.jutsu;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record JutsuRequestDto(
        @NotBlank(message = "O nome do jutsu é campo de preenchimento obrigatório.")
        String nome,
        @NotNull(message = "A quantidade de dano é campo de preenchimento obrigatório.")
        int dano,
        @NotNull(message = "A quantidade de consumo de chakras é campo de preenchimento obrigatório.")
        int consumoDeChakra
) {
}
