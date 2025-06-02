package com.naruto.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("NINJA_DE_NINJUTSU")
public class NinjaDeNinjutsu extends Personagem {
    @Override
    public void usarJutsu(Jutsu jutsu) {

    }

    @Override
    public String desviar() {
        return null;
    }
}
