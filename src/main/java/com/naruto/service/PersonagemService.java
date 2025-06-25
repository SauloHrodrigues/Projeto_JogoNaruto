package com.naruto.service;

import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.dto.personagem.NovoPersonagemDTO;
import com.naruto.dto.personagem.PersonagemResponseDto;
import java.util.List;

public interface PersonagemService {

    PersonagemResponseDto novoPersonagem(NovoPersonagemDTO dto);

    List<PersonagemResponseDto> listarPersonagens();

    PersonagemResponseDto adicionarJutsu(Long id, JutsuRequestDto dto);

    void apagar(Long id);
}
