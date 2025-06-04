package com.naruto.exceptions;

import com.naruto.exceptions.Jogo.InsuficienciaDeChakrasException;
import com.naruto.exceptions.Jogo.JogadorForaDoJogoException;
import com.naruto.exceptions.Jogo.JutsuNaoPertenceAoJogadoException;
import com.naruto.exceptions.personagem.PersonagemNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PersonagemNaoEncontradoException.class)
    public ResponseEntity<Object> handlerPersonagemNaoEncontradoException(PersonagemNaoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Erro. ", ex.getMessage()));
    }
    @ExceptionHandler(JogadorForaDoJogoException.class)
    public ResponseEntity<Object> handlerJogadorNaoCadastradoException(JogadorForaDoJogoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Erro. ", ex.getMessage()));
    }
    @ExceptionHandler(JutsuNaoPertenceAoJogadoException.class)
    public ResponseEntity<Object> handlerJutsuNaoPertenceAoJogadoException(JutsuNaoPertenceAoJogadoException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Erro. ", ex.getMessage()));
    }

    @ExceptionHandler(InsuficienciaDeChakrasException.class)
    public ResponseEntity<Object> handlerInsuficienciaDeChakrasException(InsuficienciaDeChakrasException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Erro. ", ex.getMessage()));
    }



    static class ErrorResponse {
        private String tipoErro;
        private String mensagem;

        public ErrorResponse(String tipoErro, String mensagem) {
            this.tipoErro = tipoErro;
            this.mensagem = mensagem;
        }

        public String getTipoErro() {
            return tipoErro;
        }

        public String getMensagem() {
            return mensagem;
        }
    }
}