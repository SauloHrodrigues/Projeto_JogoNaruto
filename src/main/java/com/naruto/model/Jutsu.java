package com.naruto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Jutsu")
public class Jutsu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private int dano;
    private int consumoDeChakra;

    @ManyToOne
    @JoinColumn(name = "personagem_id")
    private Personagem personagem;


    @Override
    public String toString() {
        return "Jutsu{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dano=" + dano +
                ", consumoDeChakra=" + consumoDeChakra +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jutsu jutsu)) return false;
        return Objects.equals(id, jutsu.id) && Objects.equals(nome, jutsu.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}
