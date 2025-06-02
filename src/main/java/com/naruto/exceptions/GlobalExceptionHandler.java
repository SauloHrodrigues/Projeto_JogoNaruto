package com.naruto.exceptions;

import com.naruto.exceptions.personagem.JogadorNaoCadastradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JogadorNaoCadastradoException.class)
    public ResponseEntity<Object> handlerJogadorNaoCadastradoException(JogadorNaoCadastradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Erro. ", ex.getMessage()));
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