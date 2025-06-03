package com.naruto.service;

import com.naruto.dto.jogo.AtaqueDto;
import com.naruto.dto.jogo.NovoJogoDto;

public interface JogoService {

    public void novoJogo(NovoJogoDto novosJogadores);

    public String atacarDeJutsu(AtaqueDto dto);

    public void desviar();

    public void placarDoJogo();

    public void finalizarJogo();

    public void visualizarHistórico();

}
