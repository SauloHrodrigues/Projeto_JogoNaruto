package com.naruto.service.implementacao;

import com.naruto.dto.jogo.AtaqueRequestDto;
import com.naruto.dto.jogo.AtaqueResponseDto;
import com.naruto.dto.jogo.NovoJogoRequestDto;
import com.naruto.dto.jogo.NovoJogoResponseDto;
import com.naruto.exceptions.Jogo.InaptoParaDefesaException;
import com.naruto.exceptions.Jogo.JogoPendenteException;
import com.naruto.fixture.JogoFixture;
import com.naruto.fixture.JutsuFixture;
import com.naruto.fixture.PersonagemFixture;
import com.naruto.mappers.PersonagemMapper;
import com.naruto.model.Jutsu;
import com.naruto.model.NinjaDeGenjutsu;
import com.naruto.model.NinjaDeNinjutsu;
import com.naruto.model.NinjaDeTaijutsu;
import com.naruto.model.Personagem;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JogoServiceImplTest {

    @InjectMocks
    private JogoServiceImpl service;

    @Mock
    private PersonagemServiceIpml personagemServiceIpml;

    private Jutsu raseganJutsu;
    private Jutsu hachimonJutsu;
    private Jutsu punhoSuaveJutsu;
    private NinjaDeNinjutsu naruto;
    private NinjaDeTaijutsu sasuke;
    private NinjaDeGenjutsu kakashi;


    private PersonagemMapper personagemMapper;

    @BeforeEach
    void setUp() {
       personagemMapper = Mappers.getMapper(PersonagemMapper.class);

        raseganJutsu = JutsuFixture.toEntity(1L, "Rasengan", 20, 10);
        hachimonJutsu = JutsuFixture.toEntity(2L, "Hachimon", 25, 8);
        punhoSuaveJutsu = JutsuFixture.toEntity(3L, "Punho Suave", 5, 2);

        naruto = (NinjaDeNinjutsu) PersonagemFixture.toEntity(1L,"naruto uzumaki","NINJA_DE_NINJUTSU",10, 10,20);
        sasuke = (NinjaDeTaijutsu) PersonagemFixture.toEntity(2L, "sasuke uchiha", "NINJA_DE_TAIJUTSU",15,5, 10);
        kakashi = (NinjaDeGenjutsu) PersonagemFixture.toEntity(3L, "kakashi hatake","NINJA_DE_GENJUTSU", 5,10,10);

        naruto.adicionarJutsu(punhoSuaveJutsu);
        sasuke.adicionarJutsu(raseganJutsu);
        kakashi.adicionarJutsu(hachimonJutsu);
    }

    @Test
    void deveIniciarNovoJogoQuandoNaoHaJogoAtivo() {
        NovoJogoRequestDto dto = JogoFixture.novoJogoDto("Naruto Uzumaki","Sasuke Uchiha");
        String mensagem= "Batalha: " + dto.nomeDoCombatente01().toUpperCase() + " X " + dto.nomeDoCombatente02().toUpperCase() + " foi travada!!";
        when(personagemServiceIpml.buscar(dto.nomeDoCombatente01().toLowerCase())).thenReturn(naruto);
        when(personagemServiceIpml.buscar(dto.nomeDoCombatente02().toLowerCase())).thenReturn(sasuke);

        when(personagemServiceIpml.converterResponseDto(naruto)).thenReturn(personagemMapper.toResponseDto(naruto));
        when(personagemServiceIpml.converterResponseDto(sasuke)).thenReturn(personagemMapper.toResponseDto(sasuke));

       NovoJogoResponseDto resposta = service.novoJogo(dto);

        assertTrue(resposta.mensagem().equals(mensagem));
        assertTrue(resposta.jogador01().id().equals(naruto.getId()));
        assertTrue(resposta.jogador01().nome().equals(naruto.getNome()));
        assertTrue(resposta.jogador01().jutsus().size() == naruto.getJutsus().size());

        verify(personagemServiceIpml).buscar(dto.nomeDoCombatente01().toLowerCase());
        verify(personagemServiceIpml).buscar(dto.nomeDoCombatente02().toLowerCase());
    }

    @Test
    void deveLancarExcecaoQuandoJogoEstiverAtivo() {

        NovoJogoRequestDto novoJogoRequestDto = new NovoJogoRequestDto("naruto","sasuke");

        ReflectionTestUtils.setField(service, "jogoAtivo", true);

        JogoPendenteException excessao = assertThrows(JogoPendenteException.class,
                () -> service.novoJogo(novoJogoRequestDto));

        assertTrue(excessao.getMessage().equals("Há um jogo em andamento. Finalize para iniciar novo jogo."));
    }

    @Test
    void deveAtacarDeJutsuComSucesso() {
        AtaqueRequestDto ataqueRequestDto = JogoFixture.ataqueDto(kakashi.getId(), hachimonJutsu.getId());
        Map<Long, Personagem> jogadores = new HashMap<>();
        jogadores.put(kakashi.getId(),kakashi);
        jogadores.put(sasuke.getId(),sasuke);

        when(personagemServiceIpml.buscarJutsu(hachimonJutsu.getId())).thenReturn(hachimonJutsu);

        ReflectionTestUtils.setField(service, "jogoAtivo", true);
        ReflectionTestUtils.setField(service, "jogadores", jogadores);
        ReflectionTestUtils.setField(service, "ninjaAtacante", kakashi);
        ReflectionTestUtils.setField(service, "ninjaAtacado", sasuke);
        ReflectionTestUtils.setField(service, "jutsuDeAtaque", hachimonJutsu);

        AtaqueResponseDto response = service.atacarDeJutsu(ataqueRequestDto);

        assertEquals(sasuke.getId(),response.idInimigo());
        assertEquals(hachimonJutsu.getDano(),response.danoACausar());
        assertTrue(response.mensagem().contains("O ninja de GENJUTSU:"));
        assertEquals(hachimonJutsu.getDano(), response.danoACausar());
        assertEquals(sasuke.getId(), response.idInimigo());

        verify(personagemServiceIpml).salvar(kakashi);
    }

    @Test
    void validarDesvioBemSucedido() {
        NinjaDeNinjutsu ninjaAtacante = naruto;
        NinjaDeTaijutsu ninjaAtacado = sasuke;

        Map<Long, Personagem> jogadores = new HashMap<>();
        jogadores.put(ninjaAtacante.getId(), ninjaAtacante);
        jogadores.put(ninjaAtacado.getId(), ninjaAtacado);

        JogoServiceImpl spyService = spy(service);

        ReflectionTestUtils.setField(spyService, "jogoAtivo", true);
        ReflectionTestUtils.setField(spyService, "defesaPendente", true);
        ReflectionTestUtils.setField(spyService, "jogadores", jogadores);
        ReflectionTestUtils.setField(spyService, "ninjaAtacante", ninjaAtacante);
        ReflectionTestUtils.setField(spyService, "ninjaAtacado", ninjaAtacado);
        ReflectionTestUtils.setField(spyService, "jutsuDeAtaque", hachimonJutsu);

        doReturn(true).when(spyService).respostaRandimica();

        String retorno = spyService.desviar(ninjaAtacado.getId());

        assertEquals(ninjaAtacado.desviar()+" usando seu poderes conseguiu exito ao " +
                "se defender do ataque lhe inferido.", retorno);
    }

    @Test
    void validarDesvioMalSucedido() {
        NinjaDeNinjutsu ninjaAtacado = naruto;
        NinjaDeGenjutsu ninjaAtacante = kakashi;

        Map<Long, Personagem> jogadores = new HashMap<>();
        jogadores.put(ninjaAtacado.getId(),ninjaAtacado);
        jogadores.put(ninjaAtacante.getId(),ninjaAtacante);

        JogoServiceImpl spyService = Mockito.spy(service);

        ReflectionTestUtils.setField(spyService, "jogoAtivo", true);
        ReflectionTestUtils.setField(spyService, "defesaPendente", true);
        ReflectionTestUtils.setField(spyService, "jogadores", jogadores);
        ReflectionTestUtils.setField(spyService, "ninjaAtacante", ninjaAtacante);
        ReflectionTestUtils.setField(spyService, "ninjaAtacado", ninjaAtacado);
        ReflectionTestUtils.setField(spyService, "jutsuDeAtaque", hachimonJutsu);


        doReturn(false).when(spyService).respostaRandimica();

        String resposta = spyService.desviar(ninjaAtacado.getId());

        assertEquals(ninjaAtacado.desviar()+"não conseguiu exito ao se defender do" +
                " ataque lhe inferido, e perdeu "+ hachimonJutsu.getDano()+" vidas!", resposta);
    }

    @Test
    void deveLancarExcecaoQuandoADefesaNãoEstiverPendente() {
        NinjaDeNinjutsu ninjaAtacado = naruto;
        NinjaDeGenjutsu ninjaAtacante = kakashi;

        Map<Long, Personagem> jogadores = new HashMap<>();
        jogadores.put(ninjaAtacado.getId(),ninjaAtacado);
        jogadores.put(ninjaAtacante.getId(),ninjaAtacante);

        JogoServiceImpl spyService = Mockito.spy(service);

        ReflectionTestUtils.setField(spyService, "jogoAtivo", true);
        ReflectionTestUtils.setField(spyService, "defesaPendente", false);
        ReflectionTestUtils.setField(spyService, "jogadores", jogadores);

        InaptoParaDefesaException excessao = assertThrows(InaptoParaDefesaException.class,
                () -> spyService.desviar(naruto.getId()));

        assertEquals("Não há defesa a ser feita",excessao.getMessage());
    }
}