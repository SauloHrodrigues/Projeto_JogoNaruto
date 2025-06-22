package com.naruto.fixture;

import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.dto.jutsu.JutsuResponseDto;
import java.util.HashMap;
import java.util.Map;

public class JutsuFixture {

    public static JutsuRequestDto requestDto(String nome, int dano, int consumoDeChkra){
        return new JutsuRequestDto(nome,dano,consumoDeChkra);
    }


    public static JutsuResponseDto response(Long id, JutsuRequestDto dto){
        return new JutsuResponseDto(id, dto.nome(), dto.dano(), dto.consumoDeChakra());
    }

    public static Map<String, JutsuResponseDto> mapJustsuResponseDto(JutsuResponseDto dto){
        Map<String, JutsuResponseDto> jutsusMap = new HashMap<>();
        jutsusMap.put(dto.nome(),dto);
        return jutsusMap;
    }


}
