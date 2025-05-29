package com.naruto.model;

import jakarta.persistence.Entity;

@Entity
public class NinjaDeGenjutsu extends Personagem {


    @Override
    public void usarJutsu(Jutsu jutsu) {

    }

    @Override
    public String desviar() {
        return null;
    }
}
