package com.naruto.mappers;

import com.naruto.dto.personagem.NovoPersonagemDTO;
import com.naruto.dto.personagem.PersonagemResponseDto;
import com.naruto.model.NinjaDeGenjutsu;
import com.naruto.model.NinjaDeNinjutsu;
import com.naruto.model.NinjaDeTaijutsu;
import com.naruto.model.Personagem;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PersonagemMapper {

    PersonagemMapper INSTANCE = Mappers.getMapper(PersonagemMapper.class);

    @Mapping(target = "categoriaNinja", expression = "java(categoriaNinja(personagem))")
    PersonagemResponseDto toResponseDto(Personagem personagem);
    @Mapping(target = "categoriaNinja", expression = "java(categoriaNinja(personagem))")
    List<PersonagemResponseDto> toResponseDtoList(List<Personagem> personagem);

    default Personagem toEntity(NovoPersonagemDTO dto) {
        return switch (dto.categoriaNinja().toUpperCase()) {
            case "NINJA_DE_NINJUTSU" -> {
                yield new NinjaDeNinjutsu().novoPersonagem(dto);
            }
            case "NINJA_DE_TAIJUTSU" -> {
                yield new NinjaDeTaijutsu().novoPersonagem(dto);
            }
            case "NINJA_DE_GENJUTSU" -> {
                yield new NinjaDeGenjutsu().novoPersonagem(dto);
            }
            default -> throw new IllegalArgumentException("Tipo inválido: " + dto.categoriaNinja());
        };
    }

    default String categoriaNinja(Personagem personagem) {
        if (personagem instanceof NinjaDeNinjutsu) return "NINJA_DE_NINJUTSU";
        if (personagem instanceof NinjaDeTaijutsu) return "NINJA_DE_TAIJUTSU";
        if (personagem instanceof NinjaDeGenjutsu) return "NINJA_DE_GENJUTSU";
        return "DESCONHECIDO";
    }
}
