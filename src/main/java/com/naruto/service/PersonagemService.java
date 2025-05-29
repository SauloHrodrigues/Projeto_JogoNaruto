package com.naruto.service;

import com.naruto.dto.NovoPersonagemDTO;
import com.naruto.dto.PersonagemResponseDto;
import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.model.Jutsu;
import java.util.List;

public interface PersonagemService {

    public PersonagemResponseDto novoPersonagem(NovoPersonagemDTO dto);

    public List<PersonagemResponseDto> listarPersonagens();

    public PersonagemResponseDto adicionarJutsu(Long id, JutsuRequestDto dto);

    public void apagar(Long id);
}
