package com.naruto.service;

import com.naruto.dto.jogo.AtaqueRequestDto;
import com.naruto.dto.jogo.AtaqueResponseDto;
import com.naruto.dto.jogo.NovoJogoDto;

public interface JogoService {

    public void novoJogo(NovoJogoDto novosJogadores);

    public AtaqueResponseDto atacarDeJutsu(AtaqueRequestDto dto);

    public void desviar();

    public void placarDoJogo();

    public void finalizarJogo();

    public void visualizarHistórico();

}
