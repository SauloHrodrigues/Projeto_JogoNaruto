package com.naruto.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("NINJA_DE_TAIJUTSU")
public class NinjaDeTaijutsu extends Personagem  {
    @Override
    public void usarJutsu(Jutsu jutsu) {
        System.out.println("o personagem está usando um golpe de Taijutsu.");
    }

    @Override
    public String desviar() {
        System.out.println("o personagem está desviando de um ataque usando sua habilidade em Taijutsu");
        return null;
    }
}
