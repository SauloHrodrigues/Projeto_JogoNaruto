package com.naruto.service.implementacao;

import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.dto.jutsu.JutsuResponseDto;
import com.naruto.dto.personagem.NovoPersonagemDTO;
import com.naruto.dto.personagem.PersonagemResponseDto;
import com.naruto.exceptions.personagem.JutsuJaExistenteException;
import com.naruto.exceptions.personagem.JutsuNaoEncontradoException;
import com.naruto.exceptions.personagem.PersonagemJaCadastradoException;
import com.naruto.exceptions.personagem.PersonagemNaoEncontradoException;
import com.naruto.fixture.JutsuFixture;
import com.naruto.fixture.PersonagemFixture;
import com.naruto.mappers.JutsuMapper;
import com.naruto.mappers.PersonagemMapper;
import com.naruto.model.Jutsu;
import com.naruto.model.NinjaDeGenjutsu;
import com.naruto.model.NinjaDeNinjutsu;
import com.naruto.model.NinjaDeTaijutsu;
import com.naruto.model.Personagem;
import com.naruto.repository.JutsuRepository;
import com.naruto.repository.PersonagemRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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

    Personagem personagemEntity;
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

    NinjaDeTaijutsu ninjaDeTaijutsuEntity;
    NinjaDeNinjutsu ninjaDeNinjutsuEntity;

    NinjaDeGenjutsu ninjaDeGenjutsuEntity;
    Jutsu jutsuEntity;


    @BeforeEach
    void setUp() {
        raseganRequestDto = JutsuFixture.requestDto("Rasengan", 10, 25);
        chidoriRequestDto = JutsuFixture.requestDto("Chidori", 10, 25);
        punhoSuaveRequestDto = JutsuFixture.requestDto("Punho Suave", 10, 25);

        raseganResponseDto = JutsuFixture.response(1L,raseganRequestDto);
        chidoriResponseDto = JutsuFixture.response(2L,chidoriRequestDto);
        punhoSuaveResponseDto = JutsuFixture.response(3L, punhoSuaveRequestDto);

        narutoRequestDTO = PersonagemFixture.novoDto("Naruto Uzumaki", "NINJA_DE_NINJUTSU",5,10, raseganRequestDto);
        sasukeRequestDTO = PersonagemFixture.novoDto("Sasuke Uchiha", "NINJA_DE_TAIJUTSU", 5,10, chidoriRequestDto);
        kakashiRequestDTO = PersonagemFixture.novoDto("Kakashi Hatake", "NINJA_DE_GENJUTSU",5,10, punhoSuaveRequestDto);

        narutoResponseDTO = PersonagemFixture.responseDto(1L,narutoRequestDTO,raseganResponseDto);
        sasukeResponseDTO = PersonagemFixture.responseDto(2L,sasukeRequestDTO,chidoriResponseDto);
        kakashiResponseDTO = PersonagemFixture.responseDto(3L, kakashiRequestDTO, punhoSuaveResponseDto);

        ninjaDeTaijutsuEntity = mock(NinjaDeTaijutsu.class);
        ninjaDeGenjutsuEntity = mock(NinjaDeGenjutsu.class);
        ninjaDeNinjutsuEntity = mock(NinjaDeNinjutsu.class);

        personagemEntity = mock(Personagem.class);

        jutsuEntity = mock(Jutsu.class);

    }

    @Test
    void novoPersonagem() {

        when(personagemMapper.toEntity(any())).thenReturn(ninjaDeTaijutsuEntity);
        when(jutsuMapper.toEntity(narutoRequestDTO.jutsuRequestDto())).thenReturn(jutsuEntity);
        when(repository.save(ninjaDeTaijutsuEntity)).thenReturn(ninjaDeTaijutsuEntity);
        when(personagemMapper.toResponseDto(ninjaDeTaijutsuEntity)).thenReturn(narutoResponseDTO);

        PersonagemResponseDto result = service.novoPersonagem(narutoRequestDTO);

        assertEquals(1L, result.id());
        assertEquals(narutoRequestDTO.nome(), result.nome());
        assertEquals(narutoRequestDTO.categoriaNinja(), result.categoriaNinja());
        assertNotNull(result.jutsus());

        verify(jutsuRepository).save(jutsuEntity);
        verify(repository).save(ninjaDeTaijutsuEntity);
        verify(personagemMapper).toEntity(any());
        verify(jutsuMapper).toEntity(narutoRequestDTO.jutsuRequestDto());
        verify(personagemMapper).toResponseDto(ninjaDeTaijutsuEntity);
    }

    @Test
    void listarPersonagens() {

        List<Personagem> personagens = List.of(ninjaDeNinjutsuEntity, ninjaDeTaijutsuEntity, ninjaDeGenjutsuEntity);
        List<PersonagemResponseDto> dtos = List.of(narutoResponseDTO, sasukeResponseDTO,kakashiResponseDTO);
        Mockito.when(repository.findAll()).thenReturn(personagens);
        Mockito.when(personagemMapper.toResponseDto(personagens)).thenReturn(dtos);

        List<PersonagemResponseDto> resultado = service.listarPersonagens();

        assertEquals(3, resultado.size());
        assertEquals(narutoRequestDTO.nome(), resultado.get(0).nome());
        assertEquals(sasukeRequestDTO.nome(), resultado.get(1).nome());
        assertEquals(kakashiRequestDTO.nome(), resultado.get(2).nome());

        Mockito.verify(repository).findAll();
        Mockito.verify(personagemMapper).toResponseDto(personagens);
    }

    @Test
    void adicionarJutsu() {

        Long idPersonagem = 1L;

        when(repository.findById(idPersonagem)).thenReturn(Optional.of(personagemEntity));

        when(jutsuMapper.toEntity(raseganRequestDto)).thenReturn(jutsuEntity);
        when(jutsuRepository.save(jutsuEntity)).thenReturn(jutsuEntity);
        doNothing().when(personagemEntity).adicionarJutsu(jutsuEntity);
        when(repository.save(personagemEntity)).thenReturn(personagemEntity);
        when(personagemMapper.toResponseDto(personagemEntity)).thenReturn(narutoResponseDTO);

        PersonagemResponseDto resultado = service.adicionarJutsu(idPersonagem, raseganRequestDto);

        assertEquals(narutoResponseDTO, resultado);

        verify(repository).findById(idPersonagem);
        verify(jutsuMapper).toEntity(raseganRequestDto);
        verify(jutsuRepository).save(jutsuEntity);
        verify(personagemEntity).adicionarJutsu(jutsuEntity);
        verify(repository).save(personagemEntity);
        verify(personagemMapper).toResponseDto(personagemEntity);
    }

    @Test
    void deveLancarExcecaoQuandoJutsuJaExisteNoPersonagem() {

        Long idPersonagem = 1L;
        JutsuRequestDto jutsuDto = raseganRequestDto;
        Jutsu jutsu = JutsuFixture.entity(raseganRequestDto);

        Map<String, Jutsu> jutsusMap = new HashMap<>();
        jutsusMap.put(jutsu.getNome().toLowerCase(), jutsu);

        when(personagemEntity.getJutsus()).thenReturn(jutsusMap);
        when(personagemEntity.getNome()).thenReturn("Naruto Uzumaki");
        when(repository.findById(idPersonagem)).thenReturn(Optional.of(personagemEntity));

        JutsuJaExistenteException excecao = assertThrows(JutsuJaExistenteException.class,
                () -> service.adicionarJutsu(idPersonagem, jutsuDto));

        assertTrue(excecao.getMessage().equals("O personagem Naruto Uzumaki, já possue o Jutsu: RASENGAN"));
        assertTrue(excecao.getMessage().contains(jutsuDto.nome().toUpperCase()));

        verify(jutsuRepository, never()).save(any());
        verify(repository, never()).save(any());
    }


    @Test
    void apagar() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(personagemEntity));

        // Act
        service.apagar(id);

        // Assert
        verify(repository).delete(personagemEntity);
    }

    @Test
    void validaPersonagemSemOJutsu() {
        // Arrange
        Personagem personagemTeste = new NinjaDeNinjutsu();
        Jutsu jutsu = JutsuFixture.entity(raseganRequestDto);
        personagemTeste.adicionarJutsu(jutsu);

        assertDoesNotThrow(() -> service.validaPersonagemSemOJutsu(personagemTeste, "qualquer coisa"));
    }

    @Test
    void lancaExcecaoPersonagemJáPossueDeterminadoJutsu(){
        // Arrange
        Personagem personagemNaruto = PersonagemFixture.ninjaDeNinjutsuEntity(1L,"Naruto");
        Jutsu jutsu = JutsuFixture.entity(raseganRequestDto);
        personagemNaruto.adicionarJutsu(jutsu);

        JutsuJaExistenteException ex = assertThrows(JutsuJaExistenteException.class,
                () -> service.validaPersonagemSemOJutsu(personagemNaruto,jutsu.getNome().toLowerCase()));

        assertTrue(ex.getMessage().equals("O personagem "+personagemNaruto.getNome()+", já possue o Jutsu: RASENGAN"));
    }

    @Test
    void deveValidarNovoPersonagem() {

        Personagem personagemNaruto = PersonagemFixture.ninjaDeNinjutsuEntity(1L,"Naruto");
        when(repository.findByNome(personagemNaruto.getNome().toLowerCase()))
                .thenReturn(Optional.of(personagemNaruto));

        PersonagemJaCadastradoException ex = assertThrows(PersonagemJaCadastradoException.class,
                () -> service.validarNovoPersonagem(personagemNaruto.getNome())
        );

        assertTrue(ex.getMessage().equals("O personagem "+personagemNaruto.getNome()+" já está cadastrado no sistema"));
        verify(repository).findByNome(personagemNaruto.getNome().toLowerCase());
    }

    @Test
    void salvar() {
        service.salvar(personagemEntity);
        verify(repository).save(personagemEntity);
    }

    @Test
    void aoBuscarJutsuDeveRetornarJutsuQuandoExistente() {

        Long id = 1L;
        Jutsu jutsu = JutsuFixture.entity(raseganRequestDto);

        when(jutsuRepository.findById(id)).thenReturn(Optional.of(jutsu));

        Jutsu resultado = service.buscarJutsu(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals(jutsu.getNome(), resultado.getNome());
        verify(jutsuRepository).findById(id);
    }

    @Test
    void aoBuscarJutsuDeveLancarExcessaoQuandoJutsuInxistente(){
        Long id = 2L;

        when(jutsuRepository.findById(id)).thenReturn(Optional.empty());

        JutsuNaoEncontradoException ex = assertThrows(JutsuNaoEncontradoException.class, () -> service.buscarJutsu(id));

        assertTrue(ex.getMessage().equals("O jutsu com ID \' "+id+"\' não foi encontrado."));

        verify(jutsuRepository).findById(id);
    }

    @Test
    void aoBuscarPersonagemPorIdDeveRetornarUmPersonagemExistente() {
       Long id = 2L;

       when(repository.findById(id)).thenReturn(Optional.of(personagemEntity));

       Personagem resposta = service.buscar(id);

       assertEquals(resposta,personagemEntity);

        verify(repository).findById(id);

    }

    @Test
    void aoBuscarPersonagemPorIdDeveRetornarUmaExcecaoDePersonagemNaoEncontrado() {
       Long id = 3L;

       when(repository.findById(id)).thenReturn(Optional.empty());

        PersonagemNaoEncontradoException ex = assertThrows(PersonagemNaoEncontradoException.class,
                () -> service.buscar(id));

        assertTrue(ex.getMessage().equals("O personagem com id: '"+id+"' não foi encontrado."));

        verify(repository).findById(id);
    }

    @Test
    void aoBuscarPersonagemPorNomeDeveRetornarUmPersonagemExistente() {
        String nomeBuscado = "Naruto";
        Personagem personagem = PersonagemFixture.ninjaDeNinjutsuEntity(1L,nomeBuscado);

        when(repository.findByNome(nomeBuscado)).thenReturn(Optional.of(personagem));

        Personagem resultado = service.buscar(nomeBuscado);

        assertNotNull(resultado);
        assertEquals(nomeBuscado, resultado.getNome());
        verify(repository).findByNome(nomeBuscado);
    }

    @Test
    void aoBuscarPersonagemPorNomeDeveRetornarUmaExcecaoDePersonagemNaoEncontrado(){
        String nomeBuscado = "Naruto";

        when(repository.findByNome(nomeBuscado)).thenReturn(Optional.empty());

        PersonagemNaoEncontradoException ex = assertThrows(PersonagemNaoEncontradoException.class,
                () -> service.buscar(nomeBuscado));

        assertTrue(ex.getMessage().equals("O personagem com o nome '"+nomeBuscado+"' não foi encontrado."));

        verify(repository).findByNome(nomeBuscado);
    }
}