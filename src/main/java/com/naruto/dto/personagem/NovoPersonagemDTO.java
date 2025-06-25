package com.naruto.dto.personagem;

import com.naruto.dto.jutsu.JutsuRequestDto;
import jakarta.validation.constraints.NotBlank;

public record NovoPersonagemDTO(
        @NotBlank(message = "O nome do personagem é campo de preenchimento obrigatório.")
        String nome,
        @NotBlank(message = "A categoria do ninja é campo de preenchimento obrigatório." +
                "Deve ser preenchido: \'NINJA_DE_NINJUTSU\', \'NINJA_DE_TAIJUTSU \' ou" +
                "\'NINJA_DE_GENJUTSU\'")
        String categoriaNinja,

        int idade,

        int chakra,

        int vida,

        JutsuRequestDto jutsuRequestDto
) {
}