package com.naruto.dto.personagem;

import com.naruto.dto.jutsu.JutsuResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
@Schema(description = "Objeto de saida representando um personagem do cadastrado no banco.")
public record PersonagemResponseDto(
        @Schema(description = "ID único do personagem", example = "10")
        Long id,
        @Schema(description = "Nome do personagem", example = "Paulo")
        String nome,
        @Schema(description = "Categoria de ninja", example = "NINJA_DE_NINJUTSU, NINJA_DE_GENJUTSU ou NINJA_DE_TAIJUTSU ")
        String categoriaNinja,
        @Schema(description = "Idade", example = "18")
        int idade,
        @Schema(description = "Quantidade de chakra", example = "10")
        int chakra,
        @Schema(description = "Quantidade de vidas", example = "23")
        int vida,

        @Schema(description = "Lista de jutsus associados ao personagem")
        Map<String, JutsuResponseDto> jutsus
) {
}
