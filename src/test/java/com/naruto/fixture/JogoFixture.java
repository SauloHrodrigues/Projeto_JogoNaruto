package com.naruto.fixture;

import com.naruto.dto.jogo.AtaqueRequestDto;
import com.naruto.dto.jogo.NovoJogoRequestDto;

public class JogoFixture {

    public static NovoJogoRequestDto novoJogoDto(String ninja01, String ninja02){
        return new NovoJogoRequestDto(ninja01, ninja02);
    }

    public static AtaqueRequestDto ataqueDto(Long idPersanagem, Long idJutsu){
        return new AtaqueRequestDto(idPersanagem,idJutsu);
    }
}
