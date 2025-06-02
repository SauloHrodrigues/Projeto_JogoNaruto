package com.naruto.exceptions.personagem;

public class JogadorNaoCadastradoException extends RuntimeException {
    public JogadorNaoCadastradoException(String mensagem){
        super(mensagem);
    }
}