package com.naruto.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "Tabela_de_Jogos")
@RequiredArgsConstructor
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id;
    @ManyToOne
    @JoinColumn(name = "personagem1_id")
    private final Personagem ninja01;

    @ManyToOne
    @JoinColumn(name = "personagem2_id")
    private final Personagem ninja02;

    private Integer pontosNinja01=0;
    private Integer pontosNinja02=0;

}
