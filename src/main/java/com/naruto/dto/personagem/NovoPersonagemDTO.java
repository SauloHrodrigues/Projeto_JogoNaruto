package com.naruto.dto.personagem;

import com.naruto.dto.jutsu.JutsuRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Objeto para criar um novo personagem.")
public record NovoPersonagemDTO(

        @Schema(description = "Nome do personagem", example = "Paulo")
        @NotBlank(message = "O nome do personagem é campo de preenchimento obrigatório.")
        String nome,

        @Schema(description = "Categoria de ninja", example = "NINJA_DE_NINJUTSU, NINJA_DE_GENJUTSU ou NINJA_DE_TAIJUTSU ")
        @NotBlank(message = "A categoria do ninja é campo de preenchimento obrigatório." +
                "Deve ser preenchido: \'NINJA_DE_NINJUTSU\', \'NINJA_DE_TAIJUTSU \' ou" +
                "\'NINJA_DE_GENJUTSU\'")
        String categoriaNinja,

        @Schema(description = "Idade", example = "18")
        int idade,

        @Schema(description = "Quantidade de chakra", example = "10")
        int chakra,

        @Schema(description = "Quantidade de vidas", example = "23")
        int vida,

        @Schema(description = "Quantidade de vidas", implementation = JutsuRequestDto.class)
        JutsuRequestDto jutsuRequestDto
) {
}