package com.naruto.fixture;

import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.dto.jutsu.JutsuResponseDto;
import com.naruto.dto.personagem.NovoPersonagemDTO;
import com.naruto.dto.personagem.PersonagemResponseDto;
import com.naruto.model.Jutsu;
import com.naruto.model.NinjaDeGenjutsu;
import com.naruto.model.NinjaDeNinjutsu;
import com.naruto.model.NinjaDeTaijutsu;
import com.naruto.model.Personagem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonagemFixture {

    public static NovoPersonagemDTO novoDto(String nome, String categoria,int chakra, int vida,  JutsuRequestDto jutsuDto) {
        return new NovoPersonagemDTO(
                nome,
                categoria,
                chakra,
                vida,
                jutsuDto
        );
    }

    public static PersonagemResponseDto responseDto(Long id, NovoPersonagemDTO dto, JutsuResponseDto jutsusResponse ){
        Map<String, JutsuResponseDto> jutsusMap = JutsuFixture.mapJustsuResponseDto(jutsusResponse);
        return new PersonagemResponseDto(
                id,
                dto.nome(),
                dto.categoriaNinja(),
                dto.chakra(),
                dto.vida(),
                jutsusMap
        );
    }

    public static NinjaDeNinjutsu ninjaDeNinjutsuEntity(String nome){
        NinjaDeNinjutsu ninjutsu = new NinjaDeNinjutsu();
        ninjutsu.setNome(nome);
        return ninjutsu;
    }
    public static NinjaDeGenjutsu ninjaDeGenjutsuEntity(String nome){
        NinjaDeGenjutsu genjutsu = new NinjaDeGenjutsu();
        genjutsu.setNome(nome);
        return genjutsu;
    }

    public static NinjaDeTaijutsu ninjaDeTaijutsuEntity(String nome){
        NinjaDeTaijutsu taijutsu = new NinjaDeTaijutsu();
        taijutsu.setNome(nome);
        return taijutsu;
    }
}