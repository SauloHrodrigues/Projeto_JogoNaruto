package com.naruto.service;

import com.naruto.dto.personagem.NovoPersonagemDTO;
import com.naruto.dto.personagem.PersonagemResponseDto;
import com.naruto.dto.jutsu.JutsuRequestDto;
import java.util.List;

public interface PersonagemService {

    public PersonagemResponseDto novoPersonagem(NovoPersonagemDTO dto);

    public List<PersonagemResponseDto> listarPersonagens();

    public PersonagemResponseDto adicionarJutsu(Long id, JutsuRequestDto dto);

    public void apagar(Long id);
}
