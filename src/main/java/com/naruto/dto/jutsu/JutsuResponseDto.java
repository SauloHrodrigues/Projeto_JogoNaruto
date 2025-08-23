package com.naruto.dto.jutsu;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto de saida representando um jutsu do cadastrado no banco.")
public record JutsuResponseDto(
        @Schema(description = "ID único do jutsu", example = "11")
        Long id,

        @Schema(description = "Nome do jutsu.",examples = "Rasengan")
        String nome,

        @Schema(description = "Quantidade de dano.",examples = "15")
        int dano,

        @Schema(description = "Quantidade de consumo de chakras.",examples = "25")
        int consumoDeChakra

) {
}
