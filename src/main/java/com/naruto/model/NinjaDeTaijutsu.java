package com.naruto.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@DiscriminatorValue("NINJA_DE_TAIJUTSU")
@Builder
public class NinjaDeTaijutsu extends Personagem  {
    @Override
    public String usarJutsu(Jutsu jutsu, String nomeDoOponente) {
        return "O ninja de TAIJUTSU: "+getNome()+", infere o jutso:"+
                jutsu.getNome().toUpperCase()+" contra o inimigo: "+
                nomeDoOponente+" para causar danos.";
    }

    @Override
    public String desviar() {
        return "O ninja de TAIJUTSU, ";
    }
}