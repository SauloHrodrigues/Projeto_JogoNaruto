package com.naruto.mappers;

import com.naruto.dto.NovoPersonagemDTO;
import com.naruto.dto.PersonagemResponseDto;
import com.naruto.model.Jutsu;
import com.naruto.model.NinjaDeGenjutsu;
import com.naruto.model.NinjaDeNinjutsu;
import com.naruto.model.NinjaDeTaijutsu;
import com.naruto.model.Personagem;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonagemMapper {

//    PersonagemMapper INSTANCE = Mappers.getMapper(PersonagemMapper.class);

    NinjaDeTaijutsu toEntityTaijutsu(NovoPersonagemDTO dto);
    NinjaDeNinjutsu toEntityNinjutsu(NovoPersonagemDTO dto);
    NinjaDeGenjutsu toEntityGenjutsu(NovoPersonagemDTO dto);

    List<PersonagemResponseDto> toResponse(List<Personagem> personagems);

    PersonagemResponseDto toResponse(Personagem personagem);

}
