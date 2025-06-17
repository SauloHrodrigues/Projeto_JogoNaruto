package com.naruto.service;

import com.naruto.dto.jogo.AtaqueRequestDto;
import com.naruto.dto.jogo.AtaqueResponseDto;
import com.naruto.dto.jogo.NovoJogoDto;

public interface IJogoService {

    public void novoJogo(NovoJogoDto novosJogadores);

    public AtaqueResponseDto atacarDeJutsu(AtaqueRequestDto dto);

    public String desviar(Long id);
}
