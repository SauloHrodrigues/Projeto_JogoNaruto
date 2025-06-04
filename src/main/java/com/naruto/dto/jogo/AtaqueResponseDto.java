package com.naruto.dto.jogo;

public record AtaqueResponseDto(
        String mensagem,
        int danoACausar,
        Long idInimigo
) {
}