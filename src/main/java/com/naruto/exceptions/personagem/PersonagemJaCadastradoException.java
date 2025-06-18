package com.naruto.exceptions.personagem;

public class PersonagemJaCadastradoException extends RuntimeException{
    public PersonagemJaCadastradoException(String mensagem){
        super(mensagem);
    }
}