package com.naruto.exceptions.personagem;

public class PersonagemNaoEncontradoException extends RuntimeException{
    public PersonagemNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}