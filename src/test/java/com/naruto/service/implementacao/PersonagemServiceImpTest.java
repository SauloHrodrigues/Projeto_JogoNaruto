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
import com.naruto.model.NinjaDeTaijutsu;
import com.naruto.repository.JutsuRepository;
import com.naruto.repository.PersonagemRepository;
import org.junit.jupiter.api.BeforeEach;
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

    NovoPersonagemDTO narutoRequestDTO;
    NovoPersonagemDTO sasukeRequestDTO;
    NovoPersonagemDTO kakashiRequestDTO;
    PersonagemResponseDto narutoResponseDTO;
    PersonagemResponseDto sasukeResponseDTO;
    PersonagemResponseDto kakashiResponseDTO;

    JutsuRequestDto raseganRequestDto;
    JutsuRequestDto chidoriRequestDto;
    JutsuRequestDto punhoSuaveRequestDto;

    JutsuResponseDto raseganResponseDto;
    JutsuResponseDto chidoriResponseDto;
    JutsuResponseDto punhoSuaveResponseDto;


    @BeforeEach
    void setUp() {
        raseganRequestDto = JutsuFixture.requestDto("Resengan", 10, 25);
        chidoriRequestDto = JutsuFixture.requestDto("Chidori", 10, 25);
        punhoSuaveRequestDto = JutsuFixture.requestDto("Punho Suave", 10, 25);

        raseganResponseDto = JutsuFixture.response(1L,raseganRequestDto);
        chidoriResponseDto = JutsuFixture.response(2L,chidoriRequestDto);
        punhoSuaveResponseDto = JutsuFixture.response(3L, punhoSuaveRequestDto);

        narutoRequestDTO = PersonagemFixture.novoDto("Naruto Uzumaki", "NINJA_DE_NINJUTSU", raseganRequestDto);
        sasukeRequestDTO = PersonagemFixture.novoDto("Sasuke Uchiha", "NINJA_DE_TAIJUTSU", chidoriRequestDto);
        kakashiRequestDTO = PersonagemFixture.novoDto("Kakashi Hatake", "NINJA_DE_GENJUTSU", punhoSuaveRequestDto);

        narutoResponseDTO = PersonagemFixture.responseDto(1L,narutoRequestDTO,raseganResponseDto);
        sasukeResponseDTO = PersonagemFixture.responseDto(2L,sasukeRequestDTO,chidoriResponseDto);
        kakashiResponseDTO = PersonagemFixture.responseDto(3L, kakashiRequestDTO, punhoSuaveResponseDto);


    }

    @Test
    void novoPersonagem() {

        NinjaDeTaijutsu personagemEntity = mock(NinjaDeTaijutsu.class);
        Jutsu jutsuEntity = mock(Jutsu.class);

        // Simulações
        when(personagemMapper.toEntity(any())).thenReturn(personagemEntity);
        when(jutsuMapper.toEntity(narutoRequestDTO.jutsuRequestDto())).thenReturn(jutsuEntity);
        when(repository.save(personagemEntity)).thenReturn(personagemEntity);
        when(personagemMapper.toResponseDto(personagemEntity)).thenReturn(narutoResponseDTO);

        // Act
        PersonagemResponseDto result = service.novoPersonagem(narutoRequestDTO);

        // Assert
        assertEquals(1L, result.id());
        assertEquals("Naruto", result.nome());
        assertEquals("NINJA_DE_NINJUTSU", result.categoriaNinja());
        assertNotNull(result.jutsus());

        verify(jutsuRepository).save(jutsuEntity);
        verify(repository).save(personagemEntity);
        verify(personagemMapper).toEntity(any());
        verify(jutsuMapper).toEntity(narutoRequestDTO.jutsuRequestDto());
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