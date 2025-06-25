package com.naruto.dto.jogo;

import jakarta.validation.constraints.NotBlank;

public record NovoJogoRequestDto(

        @NotBlank(message = "O nome do combatente 1 é obrigatório.")
        String nomeDoCombatente01,
        @NotBlank(message = "O nome do combatente 2 é obrigatório.")
        String nomeDoCombatente02
) {
}
