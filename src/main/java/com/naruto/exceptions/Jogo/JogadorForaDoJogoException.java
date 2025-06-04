package com.naruto.exceptions.Jogo;

public class JogadorForaDoJogoException extends RuntimeException {
    public JogadorForaDoJogoException(String mensagem){
        super(mensagem);
    }
}