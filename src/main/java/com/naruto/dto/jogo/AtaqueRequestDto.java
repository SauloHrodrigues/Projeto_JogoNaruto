package com.naruto.dto.jogo;

import jakarta.validation.constraints.NotNull;

public record AtaqueRequestDto(
        @NotNull(message = "O preenchimento do id do personagem é obrigatório.")
        Long idPersonagem,
        @NotNull(message = "O preenchimento do id do jutsu é obrigatório.")
        Long idDoJutsu
) {
}