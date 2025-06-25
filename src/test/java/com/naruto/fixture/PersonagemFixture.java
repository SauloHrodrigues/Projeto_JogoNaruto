package com.naruto.fixture;

import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.dto.jutsu.JutsuResponseDto;
import com.naruto.dto.personagem.NovoPersonagemDTO;
import com.naruto.dto.personagem.PersonagemResponseDto;
import com.naruto.model.Jutsu;
import com.naruto.model.NinjaDeGenjutsu;
import com.naruto.model.NinjaDeNinjutsu;
import com.naruto.model.NinjaDeTaijutsu;
import java.util.Map;

public class PersonagemFixture {

    public static NovoPersonagemDTO novoDto(String nome, String categoria, int idade, int chakra, int vida,  JutsuRequestDto jutsuDto) {
        return new NovoPersonagemDTO(
                nome,
                categoria,
                idade,
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
                dto.idade(),
                dto.chakra(),
                dto.vida(),
                jutsusMap
        );
    }

    public static NinjaDeTaijutsu ninjaDeTaijutsuEntity(Long id, NovoPersonagemDTO dto, Jutsu jutsu){
        return ninjaDeTaijutsuEntity(id,dto.nome(), dto.idade(), dto.chakra(), dto.vida(), jutsu);
    }

    public static NinjaDeTaijutsu ninjaDeTaijutsuEntity(Long id, String nome,int idade,int chakra,int vida, Jutsu jutsu){
        NinjaDeTaijutsu taijutsu = new NinjaDeTaijutsu();
        taijutsu.setId(id);
        taijutsu.setNome(nome.toLowerCase());
        taijutsu.setIdade(idade);
        taijutsu.adicionarChakra(chakra);
        taijutsu.aumentarVidas(vida);
        taijutsu.adicionarJutsu(jutsu);
        return taijutsu;
    }

    public static NinjaDeGenjutsu ninjaDeGenjutsuEntity(Long id, NovoPersonagemDTO dto, Jutsu jutsu){
        return ninjaDeGenjutsuEntity(id,dto.nome(), dto.idade(), dto.chakra(), dto.vida(), jutsu);
    }
    public static NinjaDeGenjutsu ninjaDeGenjutsuEntity(Long id, String nome,int idade,int chakra,int vida, Jutsu jutsu){
        NinjaDeGenjutsu genjutsu = new NinjaDeGenjutsu();
        genjutsu.setId(id);
        genjutsu.setNome(nome.toLowerCase());
        genjutsu.setIdade(idade);
        genjutsu.adicionarChakra(chakra);
        genjutsu.aumentarVidas(vida);
        genjutsu.adicionarJutsu(jutsu);
        return genjutsu;
    }

    public static NinjaDeNinjutsu ninjaDeNinjutsuEntity(Long id, NovoPersonagemDTO dto, Jutsu jutsu){
        return ninjaDeNinjutsuEntity(id,dto.nome(), dto.idade(), dto.chakra(), dto.vida(), jutsu);
    }
    public static NinjaDeNinjutsu ninjaDeNinjutsuEntity(Long id, String nome,int idade,int chakra,int vida, Jutsu jutsu){
        NinjaDeNinjutsu ninjutsu = new NinjaDeNinjutsu();
        ninjutsu.setId(id);
        ninjutsu.setNome(nome.toLowerCase());
        ninjutsu.setIdade(idade);
        ninjutsu.adicionarChakra(chakra);
        ninjutsu.aumentarVidas(vida);
        ninjutsu.adicionarJutsu(jutsu);
        return ninjutsu;
    }
}