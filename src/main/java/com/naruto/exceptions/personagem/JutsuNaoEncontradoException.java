package com.naruto.exceptions.personagem;

public class JutsuNaoEncontradoException extends RuntimeException{
    public JutsuNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}