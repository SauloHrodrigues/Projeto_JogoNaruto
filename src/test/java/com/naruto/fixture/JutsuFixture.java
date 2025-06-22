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

    public static JutsuResponseDto response(Long id, String nome, int dano, int consumoDechakra){
        return new JutsuResponseDto(id, nome, dano, consumoDechakra);
    }

    public static Map<String, JutsuResponseDto> mapJustsuDto(JutsuResponseDto dto){
        Map<String, JutsuResponseDto> jutsusMap = new HashMap<>();
        jutsusMap.put(dto.nome(),dto);
        return jutsusMap;
    }


}
