package com.naruto.fixture;

import com.naruto.dto.jogo.AtaqueRequestDto;
import com.naruto.dto.jogo.NovoJogoDto;

public class JogoFixture {

    public static NovoJogoDto novoJogoDto(String ninja01, String ninja02){
        return new NovoJogoDto(ninja01, ninja02);
    }

    public static AtaqueRequestDto ataqueDto(Long idPersanagem, Long idJutsu){
        return new AtaqueRequestDto(idPersanagem,idJutsu);
    }
}
