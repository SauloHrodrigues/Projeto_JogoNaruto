package com.naruto.model;

import jakarta.persistence.Entity;

@Entity
public class NinjaDeNinjutsu extends Personagem {
    @Override
    public void usarJutsu(Jutsu jutsu) {

    }

    @Override
    public String desviar() {
        return null;
    }
}
