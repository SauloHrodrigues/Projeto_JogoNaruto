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
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private int idade;

    @Setter(value = AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @MapKeyColumn(name = "nome")
    private Map<String, Jutsu> jutsus = new HashMap<>();

    @Setter(value = AccessLevel.PRIVATE)
    private int chakra;

    @Setter(value = AccessLevel.PRIVATE)
    private int vida;

    public void setNome(String nome) {
        this.nome = (nome != null) ? nome.toLowerCase() : null;
    }

    public void adicionarJutsu(Jutsu jutsu) {
        jutsus.put(jutsu.getNome().toLowerCase(), jutsu);
        jutsu.setPersonagem(this);
    }

    public void adicionarChakra(int chakra) {
        this.chakra += chakra;
    }

    public void dimunuirChakra(int chakra) {
        this.chakra -= chakra;
    }

    public void diminuirVidas(int vidas) {
        if ((this.vida - vidas) <= 0) {
            this.vida = 0;
        } else {
            this.vida -= vidas;
        }
    }

    public void aumentarVidas(int vidas) {
        this.vida += vidas;
    }

    public abstract String usarJutsu(Jutsu jutsu, String nomeDoOponente);

    public abstract String desviar();


    @Override
    public String toString() {
        return "Personagem{" +
                "id =" + id +
                ", nome ='" + nome + '\'' +
                ", idade = "+ idade +
                ", jutsus =" + jutsus +
                ", chakra =" + chakra +
                ", vida =" + vida +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Personagem that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}