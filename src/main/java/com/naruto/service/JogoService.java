package com.naruto.service;

import com.naruto.dto.jogo.AtaqueRequestDto;
import com.naruto.dto.jogo.AtaqueResponseDto;
import com.naruto.dto.jogo.NovoJogoRequestDto;
import com.naruto.dto.jogo.NovoJogoResponseDto;

public interface JogoService {

    NovoJogoResponseDto novoJogo(NovoJogoRequestDto novosJogadores);

    AtaqueResponseDto atacarDeJutsu(AtaqueRequestDto dto);

    String desviar(Long id);
}
