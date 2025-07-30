package com.naruto.service.implementacao;

import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.dto.jutsu.JutsuResponseDto;
import com.naruto.dto.personagem.NovoPersonagemDTO;
import com.naruto.dto.personagem.PersonagemResponseDto;
import com.naruto.exceptions.personagem.JutsuJaExistenteException;
import com.naruto.exceptions.personagem.PersonagemJaCadastradoException;
import com.naruto.exceptions.personagem.PersonagemNaoEncontradoException;
import com.naruto.fixture.JutsuFixture;
import com.naruto.fixture.PersonagemFixture;
import com.naruto.mappers.JutsuMapper;
import com.naruto.mappers.PersonagemMapper;
import com.naruto.model.Jutsu;
import com.naruto.model.NinjaDeNinjutsu;
import com.naruto.model.Personagem;
import com.naruto.repository.JutsuRepository;
import com.naruto.repository.PersonagemRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonagemServiceIpmlTest {

    @InjectMocks
    private PersonagemServiceIpml service;

    @Mock
    private JutsuRepository jutsuRepository;
    @Mock
    private PersonagemRepository repository;

    private NovoPersonagemDTO ninjaNarutoRequestDTO;
    private NinjaDeNinjutsu ninjaNarutoEntity;
    private Jutsu raseganEntity;
    private Jutsu punhoSuaveEntity;
    private PersonagemResponseDto ninjaNarutoResponseDTO;
    private JutsuRequestDto raseganRequestDto;
    private JutsuRequestDto punhoSuaveRequestDto;
    private JutsuResponseDto punhoSuaveResponseDto;


    @BeforeEach
    void setUp() {

        raseganRequestDto = JutsuFixture.requestDto("Rasengan", 10, 25);
        raseganEntity = JutsuFixture.toEntity(1L, raseganRequestDto);

        punhoSuaveRequestDto = JutsuFixture.requestDto("Punho Suave", 20, 30);
        punhoSuaveEntity = JutsuFixture.toEntity(2L, punhoSuaveRequestDto);
        punhoSuaveResponseDto = JutsuFixture.toResponseDto(punhoSuaveEntity);


        ninjaNarutoRequestDTO = PersonagemFixture.novoDto("naruto uzumaki", "NINJA_DE_NINJUTSU", 5, 10, 10, raseganRequestDto);
        ninjaNarutoEntity = (NinjaDeNinjutsu) PersonagemFixture.toEntity(1L, ninjaNarutoRequestDTO);
        ninjaNarutoEntity.adicionarJutsu(raseganEntity);
        ninjaNarutoResponseDTO = PersonagemFixture.toResponseDto(ninjaNarutoEntity);
    }

    @Test
    @DisplayName("Deve adicionar um novo personagem.")
    void novoPersonagem() {
        NovoPersonagemDTO dto = ninjaNarutoRequestDTO;
        Personagem personagem = PersonagemFixture.toEntity(1L,dto);
        Jutsu jutsu = JutsuFixture.toEntity(1L,dto.jutsuRequestDto());
        personagem.adicionarJutsu(jutsu);

        when(jutsuRepository.save(any(Jutsu.class))).thenReturn(jutsu);
        when(repository.save(any(Personagem.class))).thenReturn(personagem);

        PersonagemResponseDto result = service.novoPersonagem(ninjaNarutoRequestDTO);

        assertEquals(1L, result.id());
        assertEquals(ninjaNarutoRequestDTO.nome(), result.nome());
        assertEquals(ninjaNarutoRequestDTO.categoriaNinja(), result.categoriaNinja());
        assertNotNull(result.jutsus());

    }


    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar novo personagem já cadastrado.")
    void deveValidarExcecaoQuandoNovoPersonagemJaExiste() {
        JutsuRequestDto jutsuRequestDto = new JutsuRequestDto("Rasengan", 10, 25);
        NovoPersonagemDTO dto = new NovoPersonagemDTO("Naruto Uzumaki", "NINJA_DE_NINJUTSU", 5, 10, 10, jutsuRequestDto);

        PersonagemServiceIpml spyService = Mockito.spy(service);
        doThrow(new PersonagemJaCadastradoException("O personagem já está cadastrado"))
                .when(spyService).validarNovoPersonagem("Naruto Uzumaki");

        PersonagemJaCadastradoException exception = assertThrows(
                PersonagemJaCadastradoException.class,
                () -> spyService.novoPersonagem(dto)
        );

        assertEquals("O personagem já está cadastrado", exception.getMessage());

    }


    @Test
    @DisplayName("Deve retornar uma lista com todos os personagem cadastrados no banco.")
    void listarPersonagens() {

        List<Personagem> personagens = List.of(ninjaNarutoEntity);
        List<PersonagemResponseDto> dtos = List.of(ninjaNarutoResponseDTO);

        Mockito.when(repository.findAll()).thenReturn(personagens);

        List<PersonagemResponseDto> resultado = service.listarPersonagens();

        assertEquals(1, resultado.size());
        assertEquals(ninjaNarutoRequestDTO.nome(), resultado.get(0).nome());

        Mockito.verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve adicionar um jutsu a um personagem.")
    void deveAdicionarJutsuComSucesso() {

        Long idPersonagem = 1L;
        Personagem personagem = PersonagemFixture.toEntity(idPersonagem,ninjaNarutoRequestDTO);
        JutsuRequestDto requestDto = raseganRequestDto;
        Jutsu jutsu = JutsuFixture.toEntity(2L,requestDto);

        when(repository.findById(idPersonagem)).thenReturn(Optional.of(personagem));
        when(jutsuRepository.save(any(Jutsu.class))).thenReturn(jutsu);
        when(repository.save(any(Personagem.class))).thenReturn(personagem);

        PersonagemResponseDto resultado = service.adicionarJutsu(idPersonagem, requestDto);

        assertEquals(idPersonagem, resultado.id());
        assertEquals(personagem.getNome(),resultado.nome());
        assertTrue(resultado.jutsus().containsKey(jutsu.getNome().toLowerCase()));

        verify(repository).findById(idPersonagem);
    }

    @Test
    @DisplayName("Deve lançar exceção quando personagem não for encontrado")
    void deveLancarExcecao_QuandoPersonagemNaoEncontrado() {

        when(repository.findById(0L)).thenReturn(Optional.empty());

        PersonagemNaoEncontradoException resposta = assertThrows(PersonagemNaoEncontradoException.class,
                () -> service.adicionarJutsu(0L, punhoSuaveRequestDto));

        assertTrue(resposta.getMessage().contains("O personagem com id: '0' não foi encontrado."));

        verify(repository).findById(0L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando personagem já tiver o judsu.")
    void deveLancarExcecao_QuandoPersonagemJaPossuiOJutsu() {
        Long idPersonagem = ninjaNarutoEntity.getId();
        String nomeJutsu = raseganEntity.getNome();
        Jutsu jutsu = raseganEntity;
        JutsuRequestDto jutsuRequestDto = raseganRequestDto;
        Personagem personagem = ninjaNarutoEntity;
        personagem.adicionarJutsu(jutsu);

        when(repository.findById(idPersonagem)).thenReturn(java.util.Optional.of(personagem));

        JutsuJaExistenteException resposta = assertThrows(JutsuJaExistenteException.class, () -> {
            service.adicionarJutsu(idPersonagem, jutsuRequestDto);
        });

        assertTrue(resposta.getMessage().equals("O personagem " + personagem.getNome() + ", já possue o Jutsu: "
                + nomeJutsu.toUpperCase()));

    }

    @Test
    @DisplayName("Deve apagar o personagem.")
    void apagar() {
        Long id = ninjaNarutoEntity.getId();
        Personagem personagem = ninjaNarutoEntity;

        when(repository.findById(id)).thenReturn(Optional.of(personagem));

        service.apagar(id);

        verify(repository).delete(personagem);
    }
}