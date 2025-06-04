package com.naruto.mappers;

import com.naruto.dto.jogo.AtaqueResponseDto;
import com.naruto.model.Jogo;
import com.naruto.model.Personagem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JogoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pontosNinja01", ignore = true)
    @Mapping(target = "pontosNinja02", ignore = true)
    Jogo toEntity(Personagem ninja01, Personagem ninja02);

    @Mapping(source = "mensagem", target = "mensagem")
    @Mapping(source = "dano", target = "danoACausar")
    @Mapping(source = "idInimigo", target = "idInimigo")
    AtaqueResponseDto toResponse(String mensagem, int dano, Long idInimigo);
}
