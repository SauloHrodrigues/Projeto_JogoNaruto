package com.naruto.controller;

import com.naruto.dto.jogo.AtaqueRequestDto;
import com.naruto.dto.jogo.AtaqueResponseDto;
import com.naruto.dto.jogo.NovoJogoDto;
import com.naruto.dto.personagem.PersonagemResponseDto;
import com.naruto.service.JogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/jogo")
public class JogoContoller {

    private final JogoService service;

    @PostMapping
    public ResponseEntity<PersonagemResponseDto> cadastrar(@RequestBody NovoJogoDto jogadores) {
        service.novoJogo(jogadores);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/atacar")
    public ResponseEntity atacarDeJutsu(@RequestBody AtaqueRequestDto dto) {
        AtaqueResponseDto ataqueDeJutsu = service.atacarDeJutsu(dto);
        return ResponseEntity.status(HttpStatus.OK).body(ataqueDeJutsu);
    }
    @PostMapping("/{id}/desviar")
    public ResponseEntity desviar(@PathVariable Long id) {
        String mensagem = service.desviar(id);
        return ResponseEntity.status(HttpStatus.OK).body(mensagem);
    }
    
}
