package com.naruto.service;

import com.naruto.dto.personagem.PersonagemResponseDto;
import com.naruto.model.Jutsu;
import com.naruto.model.Personagem;

public interface PersonagemSeviceUsoInterno {
    Personagem salvar(Personagem personagem);
    Jutsu buscarJutsu(Long id);
    Personagem buscar(Long id);
    Personagem buscar(String nome);
    PersonagemResponseDto converterResponseDto(Personagem personagem);


}
