package com.naruto.dto.jogo;

import com.naruto.dto.personagem.PersonagemResponseDto;
import jakarta.validation.constraints.NotBlank;

public record NovoJogoResponseDto(
        PersonagemResponseDto jogador01,
        PersonagemResponseDto jogador02,

        String mensagem

) {
}
