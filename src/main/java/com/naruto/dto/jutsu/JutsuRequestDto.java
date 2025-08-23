package com.naruto.dto.jutsu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
@Schema(description = "Objeto para criar um novo jutsu.")
public record JutsuRequestDto(
        @Schema(description = "Nome do jutsu.",examples = "Rasengan")
        @NotBlank(message = "O nome do jutsu é campo de preenchimento obrigatório.")
        String nome,

        @Schema(description = "Quantidade de dano.",examples = "15")
        @NotNull(message = "A quantidade de dano é campo de preenchimento obrigatório.")
        int dano,

        @Schema(description = "Quantidade de consumo de chakras.",examples = "25")
        @NotNull(message = "A quantidade de consumo de chakras é campo de preenchimento obrigatório.")
        int consumoDeChakra
) {
}
