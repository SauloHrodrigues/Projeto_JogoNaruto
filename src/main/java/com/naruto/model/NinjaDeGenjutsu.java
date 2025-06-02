package com.naruto.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("NINJA_DE_GENJUTSU")
public class NinjaDeGenjutsu extends Personagem {


    @Override
    public void usarJutsu(Jutsu jutsu) {

    }

    @Override
    public String desviar() {
        return null;
    }
}
