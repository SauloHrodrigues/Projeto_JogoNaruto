package com.naruto.service;

import com.naruto.model.Jutsu;
import com.naruto.model.Personagem;

public interface IPersonagem {
     void salvar(Personagem personagem);

    public Jutsu buscarJutsu(Long id);

    public Personagem buscar(Long id);

    public Personagem buscar(String nome);

}
