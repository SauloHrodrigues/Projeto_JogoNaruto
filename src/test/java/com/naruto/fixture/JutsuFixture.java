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

    public static Jutsu entity(Long id,JutsuRequestDto dto){
        return Jutsu.builder()
                .id(id)
                .nome(dto.nome())
                .dano(dto.dano())
                .consumoDeChakra(dto.consumoDeChakra())
                .build();
    }

    public static Jutsu entity(Long id,String nome,int dano,int consumo ){
        return Jutsu.builder()
                .id(id)
                .nome(nome)
                .dano(dano)
                .consumoDeChakra(consumo)
                .build();
    }

    public static JutsuResponseDto response(Jutsu jutsu){
        return new JutsuResponseDto(jutsu.getId(), jutsu.getNome(), jutsu.getDano(), jutsu.getConsumoDeChakra());
    }

    public static Map<String, JutsuResponseDto> mapJustsuResponseDto(JutsuResponseDto dto){
        Map<String, JutsuResponseDto> jutsusMap = new HashMap<>();
        jutsusMap.put(dto.nome(),dto);
        return jutsusMap;
    }
}