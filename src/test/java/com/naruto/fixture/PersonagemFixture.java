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

    public static Personagem toEntity(Long id,NovoPersonagemDTO dto){
        return toEntity(id,dto.nome(), dto.categoriaNinja(), dto.idade(), dto.chakra(), dto.vida());
    }
    public static Personagem toEntity(Long id,String nome,String categoriaNinja, int idade,int chakra, int vida){
        Personagem personagem;
        switch (categoriaNinja.toUpperCase()) {
            case "NINJA_DE_NINJUTSU" -> personagem = new NinjaDeNinjutsu();
            case "NINJA_DE_GENJUTSU" -> personagem = new NinjaDeGenjutsu();
            case "NINJA_DE_TAIJUTSU" -> personagem = new NinjaDeTaijutsu();
            default -> throw new IllegalArgumentException("Categoria inválida");
        }
        personagem.setId(id);
        personagem.setNome(nome);
        personagem.setIdade(idade);
        personagem.adicionarChakra(chakra);
        personagem.aumentarVidas(vida);
        return personagem;
    }

    public static PersonagemResponseDto toResponseDto(NinjaDeNinjutsu ninjutsu){
        return toResponseDto(ninjutsu,"NINJA_DE_NINJUTSU");
    }
    public static PersonagemResponseDto toResponseDto(NinjaDeGenjutsu genjutsu){
        return toResponseDto(genjutsu, "NINJA_DE_GENJUTSU");
    }
    public static PersonagemResponseDto toResponseDto(NinjaDeTaijutsu taijutsu){
        return toResponseDto(taijutsu,"NINJA_DE_TAIJUTSU");
    }
    public static PersonagemResponseDto toResponseDto(Personagem personagem, String ninja) {
       Map<String,JutsuResponseDto> jutsuResponseDto = JutsuFixture.toResponseDto(personagem.getJutsus());
        return new PersonagemResponseDto(
                personagem.getId(), personagem.getNome(),
                ninja, personagem.getIdade(), personagem.getChakra(),
                personagem.getVida(), jutsuResponseDto
        );
    }
}