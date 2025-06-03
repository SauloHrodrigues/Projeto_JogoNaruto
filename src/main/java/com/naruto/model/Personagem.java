package com.naruto.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "personagem")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "categoria_ninja")
public abstract class Personagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Jutsu> jutsus = new ArrayList<>();

    private int chakra;
    private int vida;

    public void adicionarJutsu(Jutsu jutsu){
        jutsus.add(jutsu);
    }

    public abstract void usarJutsu(Jutsu jutsu);

    public abstract String desviar();


    @Override
    public String toString() {
        return "Personagem{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", jutsus=" + jutsus +
                ", chakra=" + chakra +
                ", vida=" + vida +
                '}';
    }
}
