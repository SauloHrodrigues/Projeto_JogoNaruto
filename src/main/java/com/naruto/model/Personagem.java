package com.naruto.model;

import com.naruto.enuns.CategoriaNinja;
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
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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

    @Setter(value = AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Jutsu> jutsus = new ArrayList<>();

    @Setter(value = AccessLevel.PRIVATE)
    private int chakra;
    @Setter(value = AccessLevel.PRIVATE)
    private int vida;

    public void adicionarJutsu(Jutsu jutsu){
        jutsus.add(jutsu);
    }

    public void adicionarChakra(int chakra){
        this.chakra += chakra;
    }
    public void dimunuirChakra(int chakra){
        this.chakra -= chakra;
    }

    public void diminuirVidas(int vidas){
        if((this.vida - vidas)<=0){
            this.vida = 0;
        } else {
            this.vida -= vidas;
        }
    }
    public void aumentarVidas(int vidas){
        this.vida += vidas;
    }

    public abstract String usarJutsu(Jutsu jutsu, String nomeDoOponente);

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