package com.naruto.exceptions.Jogo;

public class JogoInativoException extends RuntimeException {
    public JogoInativoException(String mensagem){
        super(mensagem);
    }
}