package com.naruto.exceptions;

import com.naruto.exceptions.Jogo.InaptoParaDefesaException;
import com.naruto.exceptions.Jogo.InsuficienciaDeChakrasException;
import com.naruto.exceptions.Jogo.JogadorForaDoJogoException;
import com.naruto.exceptions.Jogo.JogoInativoException;
import com.naruto.exceptions.Jogo.JogoPendenteException;
import com.naruto.exceptions.Jogo.JutsuNaoPertenceAoJogadoException;
import com.naruto.exceptions.personagem.JutsuJaExistenteException;
import com.naruto.exceptions.personagem.PersonagemJaCadastradoException;
import com.naruto.exceptions.personagem.PersonagemNaoEncontradoException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PersonagemNaoEncontradoException.class)
    public ResponseEntity<Object> handlerPersonagemNaoEncontradoException(PersonagemNaoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Erro. ", ex.getMessage()));
    }

    @ExceptionHandler(PersonagemJaCadastradoException.class)
    public ResponseEntity<Object> handlerPersonagemJaCadastradoException(PersonagemJaCadastradoException ex){
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

    @ExceptionHandler(InaptoParaDefesaException.class)
    public ResponseEntity<Object> handlerInaptoParaDefesaException(InaptoParaDefesaException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Erro. ", ex.getMessage()));
    }

   @ExceptionHandler(JogoInativoException.class)
    public ResponseEntity<Object> handlerJogoInativoException (JogoInativoException  ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Erro. ", ex.getMessage()));
    }

@ExceptionHandler(JogoPendenteException.class)
    public ResponseEntity<Object> handlerJogoPendenteException (JogoPendenteException  ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Erro. ", ex.getMessage()));
    }
@ExceptionHandler(JutsuJaExistenteException.class)
    public ResponseEntity<Object> handlerJutsuJaExistenteException  (JutsuJaExistenteException   ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Erro. ", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(erro -> {
            erros.put(erro.getField(), erro.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(erros);
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