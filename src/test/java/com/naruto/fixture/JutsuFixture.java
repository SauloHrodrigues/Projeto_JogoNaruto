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

    public static Jutsu toEntity(Long id, JutsuRequestDto dto){
        return toEntity(id,dto.nome(), dto.dano(), dto.consumoDeChakra());
    }

    public static Jutsu toEntity(Long id, String nome, int dano, int consumoDeChakra){
        return Jutsu.builder()
                .id(id)
                .nome(nome)
                .dano(dano)
                .consumoDeChakra(consumoDeChakra)
                .build();
    }


    public static JutsuResponseDto toResponseDto(Jutsu jutsu){
        return new JutsuResponseDto(jutsu.getId(), jutsu.getNome(), jutsu.getDano(), jutsu.getConsumoDeChakra());
    }


    public static Map<String, JutsuResponseDto> toResponseDto(Map<String, Jutsu> dto) {
        Map<String, JutsuResponseDto> jutsusMap = new HashMap<>();

        for (Map.Entry<String, Jutsu> jutsu : dto.entrySet()) {

            JutsuResponseDto responseDto = toResponseDto(jutsu.getValue());

            jutsusMap.put(responseDto.nome(), responseDto);
        }
        return jutsusMap;
    }

}