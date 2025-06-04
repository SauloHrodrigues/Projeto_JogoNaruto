package com.naruto.mappers;

import com.naruto.dto.personagem.NovoPersonagemDTO;
import com.naruto.dto.personagem.PersonagemResponseDto;
import com.naruto.model.NinjaDeNinjutsu;
import com.naruto.model.NinjaDeTaijutsu;
import com.naruto.model.Personagem;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonagemMapper {

    @Mapping(target = "categoriaNinja", expression = "java(categoriaNinja(personagem))")
    PersonagemResponseDto toResponseDto(Personagem personagem);
    @Mapping(target = "categoriaNinja", expression = "java(categoriaNinja(personagem))")
    List<PersonagemResponseDto> toResponseDto(List<Personagem> personagem);

    default Personagem toEntity(NovoPersonagemDTO dto) {
        return switch (dto.categoriaNinja().toUpperCase()) {
            case "NINJA_DE_NINJUTSU" -> {
                NinjaDeNinjutsu ninja = new NinjaDeNinjutsu();
                copyCommonFields(dto, ninja);
                yield ninja;
            }
            case "NINJA_DE_TAIJUTSU" -> {
                NinjaDeTaijutsu ninja = new NinjaDeTaijutsu();
                copyCommonFields(dto, ninja);
                yield ninja;
            }
            default -> throw new IllegalArgumentException("Tipo inválido: " + dto.categoriaNinja());
        };
    }

    default void copyCommonFields(NovoPersonagemDTO dto, Personagem personagem) {
        personagem.setNome(dto.nome());
        personagem.aumentarVidas(dto.vida());
        personagem.adicionarChakra(dto.chakra());
    }

    default String categoriaNinja(Personagem personagem) {
        if (personagem instanceof NinjaDeNinjutsu) return "NINJA_DE_NINJUTSU";
        if (personagem instanceof NinjaDeTaijutsu) return "NINJA_DE_TAIJUTSU";
        return "DESCONHECIDO";
    }
}
