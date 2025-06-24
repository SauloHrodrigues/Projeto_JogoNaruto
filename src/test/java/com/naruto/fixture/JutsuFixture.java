package com.naruto.fixture;

import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.dto.jutsu.JutsuResponseDto;
import com.naruto.model.Jutsu;
import java.util.HashMap;
import java.util.Map;

public class JutsuFixture {

    public static JutsuRequestDto requestDto(String nome, int dano, int consumoDeChkra){
        return new JutsuRequestDto(nome,dano,consumoDeChkra);
    }


    public static JutsuResponseDto response(Long id, JutsuRequestDto dto){
        return new JutsuResponseDto(id, dto.nome(), dto.dano(), dto.consumoDeChakra());
    }

    public static Jutsu entity(Long id, String nome, int dano, int consumoDeChakra){
        return Jutsu.builder()
                .id(id)
                .nome(nome)
                .dano(dano)
                .consumoDeChakra(consumoDeChakra)
                .build();
    }

    public static Jutsu entity(JutsuRequestDto dto){
       return entity(
               1L,
               dto.nome(),
               dto.dano(),
               dto.consumoDeChakra()
       );
    }

    public static Map<String, JutsuResponseDto> mapJustsuResponseDto(JutsuResponseDto dto){
        Map<String, JutsuResponseDto> jutsusMap = new HashMap<>();
        jutsusMap.put(dto.nome(),dto);
        return jutsusMap;
    }


}
