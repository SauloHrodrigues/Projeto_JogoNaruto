package com.naruto.fixture;

import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.dto.jutsu.JutsuResponseDto;
import com.naruto.dto.personagem.NovoPersonagemDTO;
import com.naruto.dto.personagem.PersonagemResponseDto;
import com.naruto.model.NinjaDeGenjutsu;
import com.naruto.model.NinjaDeNinjutsu;
import com.naruto.model.NinjaDeTaijutsu;
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

    public static NinjaDeNinjutsu ninjaDeNinjutsuEntity(Long id, String nome){
        NinjaDeNinjutsu ninjutsu = new NinjaDeNinjutsu();
        ninjutsu.setId(id);
        ninjutsu.setNome(nome);
        ninjutsu.adicionarChakra(50);
        return ninjutsu;
    }
    public static NinjaDeGenjutsu ninjaDeGenjutsuEntity(Long id,String nome){
        NinjaDeGenjutsu genjutsu = new NinjaDeGenjutsu();
        genjutsu.setId(id);
        genjutsu.setNome(nome);
        return genjutsu;
    }

    public static NinjaDeTaijutsu ninjaDeTaijutsuEntity(Long id, String nome){
        NinjaDeTaijutsu taijutsu = new NinjaDeTaijutsu();
        taijutsu.setId(id);
        taijutsu.setNome(nome);
        return taijutsu;
    }
}