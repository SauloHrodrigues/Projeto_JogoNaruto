package com.naruto.controller;

import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.dto.personagem.NovoPersonagemDTO;
import com.naruto.dto.personagem.PersonagemResponseDto;
import com.naruto.service.PersonagemService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/personagem")
public class PersonagemController {


    private final PersonagemService service;

    @PostMapping
    public ResponseEntity<PersonagemResponseDto> cadastrar(@Valid @RequestBody NovoPersonagemDTO novoPersonagem) {
        PersonagemResponseDto novoPersonagemCriado = service.novoPersonagem(novoPersonagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPersonagemCriado);
    }

    @GetMapping
    public ResponseEntity<List<PersonagemResponseDto>> listar() {
        List<PersonagemResponseDto> lista = service.listarPersonagens();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @PostMapping("/{id}/adicionar_jutsu")
    public ResponseEntity<PersonagemResponseDto> adicionarJutsu(@PathVariable Long id, @Valid @RequestBody JutsuRequestDto dto) {
        PersonagemResponseDto personagemComNovoJutsu = service.adicionarJutsu(id,dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(personagemComNovoJutsu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonagemResponseDto> apagar (@PathVariable Long id) {
        service.apagar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}