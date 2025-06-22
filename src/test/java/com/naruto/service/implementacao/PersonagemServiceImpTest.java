package com.naruto.service.implementacao;

import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.dto.jutsu.JutsuResponseDto;
import com.naruto.dto.personagem.NovoPersonagemDTO;
import com.naruto.dto.personagem.PersonagemResponseDto;
import com.naruto.fixture.JutsuFixture;
import com.naruto.fixture.PersonagemFixture;
import com.naruto.mappers.JutsuMapper;
import com.naruto.mappers.PersonagemMapper;
import com.naruto.model.Jutsu;
import com.naruto.model.NinjaDeNinjutsu;
import com.naruto.model.NinjaDeTaijutsu;
import com.naruto.model.Personagem;
import com.naruto.repository.JutsuRepository;
import com.naruto.repository.PersonagemRepository;
import com.naruto.service.PersonagemService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonagemServiceImpTest {

    @InjectMocks
    private PersonagemServiceImp service;

    @Mock
    private PersonagemMapper personagemMapper;

    @Mock
    private JutsuMapper jutsuMapper;

    @Mock
    private JutsuRepository jutsuRepository;

    @Mock
    private PersonagemRepository repository;


    @Test
    void novoPersonagem() {
        // Arrange
        JutsuRequestDto jutsuDto = JutsuFixture.requestDto("Resengan",10,25);
        NovoPersonagemDTO dto = PersonagemFixture.novoDto("Naruto","NINJA_DE_NINJUTSU",jutsuDto);

        NinjaDeTaijutsu personagemEntity = mock(NinjaDeTaijutsu.class);
        Jutsu jutsuEntity = mock(Jutsu.class);

        JutsuResponseDto jutsuResponseDto = JutsuFixture.response(1L,"Resengan",10,25);
        Map<String, JutsuResponseDto> jutsusMap = JutsuFixture.mapJustsuDto(jutsuResponseDto);

        PersonagemResponseDto responseDto = PersonagemFixture.responseDto(dto, jutsusMap);

        // Simulações
        when(personagemMapper.toEntity(any())).thenReturn(personagemEntity);
        when(jutsuMapper.toEntity(dto.jutsuRequestDto())).thenReturn(jutsuEntity);
        when(repository.save(personagemEntity)).thenReturn(personagemEntity);
        when(personagemMapper.toResponseDto(personagemEntity)).thenReturn(responseDto);

        // Act
        PersonagemResponseDto result = service.novoPersonagem(dto);

        // Assert
        assertEquals(1L, result.id());
        assertEquals("Naruto", result.nome());
        assertEquals("NINJA_DE_NINJUTSU", result.categoriaNinja());
        assertNotNull(result.jutsus());

        verify(jutsuRepository).save(jutsuEntity);
        verify(repository).save(personagemEntity);
        verify(personagemMapper).toEntity(any());
        verify(jutsuMapper).toEntity(dto.jutsuRequestDto());
        verify(personagemMapper).toResponseDto(personagemEntity);
    }

    @Test
    void listarPersonagens() {
    }

    @Test
    void adicionarJutsu() {
    }

    @Test
    void apagar() {
    }

    @Test
    void validarJutsu() {
    }

    @Test
    void validarNovoPersonagem() {
    }

    @Test
    void salvar() {
    }

    @Test
    void buscarJutsu() {
    }

    @Test
    void buscar() {
    }

    @Test
    void testBuscar() {
    }


}