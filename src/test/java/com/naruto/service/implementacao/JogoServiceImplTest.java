package com.naruto.service.implementacao;

import com.naruto.dto.jogo.AtaqueRequestDto;
import com.naruto.dto.jogo.AtaqueResponseDto;
import com.naruto.dto.jogo.NovoJogoDto;
import com.naruto.exceptions.Jogo.JogoPendenteException;
import com.naruto.fixture.JogoFixture;
import com.naruto.fixture.JutsuFixture;
import com.naruto.fixture.PersonagemFixture;
import com.naruto.model.Jutsu;
import com.naruto.model.NinjaDeNinjutsu;
import com.naruto.model.NinjaDeTaijutsu;
import com.naruto.model.Personagem;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JogoServiceImplTest {

    @InjectMocks
    private JogoServiceImpl service;

    @Mock
    PersonagemServiceImp personagemServiceImp;


    @Test
    void deveIniciarNovoJogoQuandoNaoHaJogoAtivo() {

        NovoJogoDto novoJogoDto = JogoFixture.novoJogoDto("naruto","sasuke");

        NinjaDeTaijutsu ninjaSasuke = PersonagemFixture.ninjaDeTaijutsuEntity(1L,novoJogoDto.nomeDoCombatente01());
        NinjaDeNinjutsu ninjaNaruto = PersonagemFixture.ninjaDeNinjutsuEntity(3L,novoJogoDto.nomeDoCombatente02());

        when(personagemServiceImp.buscar("naruto")).thenReturn(ninjaNaruto);
        when(personagemServiceImp.buscar("sasuke")).thenReturn(ninjaSasuke);

        service.novoJogo(novoJogoDto);

        verify(personagemServiceImp).buscar("naruto");
        verify(personagemServiceImp).buscar("sasuke");
    }

    @Test
    void deveLancarExcecaoQuandoJogoEstiverAtivo() {
        NovoJogoDto novoJogoDto = JogoFixture.novoJogoDto("naruto","sasuke");

        NinjaDeTaijutsu ninjaSasuke = PersonagemFixture.ninjaDeTaijutsuEntity(1L,novoJogoDto.nomeDoCombatente01());
        NinjaDeNinjutsu ninjaNaruto = PersonagemFixture.ninjaDeNinjutsuEntity(2L,novoJogoDto.nomeDoCombatente02());

        when(personagemServiceImp.buscar("naruto")).thenReturn(ninjaNaruto);
        when(personagemServiceImp.buscar("sasuke")).thenReturn(ninjaSasuke);

        service.novoJogo(novoJogoDto);

        JogoPendenteException excessao = assertThrows(JogoPendenteException.class, () -> service.novoJogo(novoJogoDto));

        assertTrue(excessao.getMessage().equals("Há um jogo em andamento. Finalize para iniciar novo jogo."));
    }

    @Test
    void deveAtacarDeJutsuComSucesso() {
        Long idAtacante = 1L;
        Long idAtacado = 2L;
        Long idDoJutsu = 1L;

        AtaqueRequestDto ataqueRequestDto = JogoFixture.ataqueDto(idAtacante,idDoJutsu);

        NinjaDeNinjutsu atacante = PersonagemFixture.ninjaDeNinjutsuEntity(idAtacante,"naruto");
        NinjaDeTaijutsu atacado = PersonagemFixture.ninjaDeTaijutsuEntity(idAtacado,"Sasuke");

        Jutsu jutsu = JutsuFixture.entity(idDoJutsu, "Rasengan", 20, 10);
        atacante.adicionarJutsu(jutsu);

        Map<Long, Personagem> jogadores = new HashMap<>();
        jogadores.put(atacante.getId(), atacante);
        jogadores.put(atacado.getId(), atacado);

        ReflectionTestUtils.setField(service, "jogoAtivo", true);
        ReflectionTestUtils.setField(service, "jogadores", jogadores);
        ReflectionTestUtils.setField(service, "ninjaAtacante", atacante);
        ReflectionTestUtils.setField(service, "ninjaAtacado", atacado);
        ReflectionTestUtils.setField(service, "jutsuDeAtaque", jutsu);

        when(personagemServiceImp.buscarJutsu(idDoJutsu)).thenReturn(jutsu);

        AtaqueResponseDto response = service.atacarDeJutsu(ataqueRequestDto);

        assertEquals("O ninja de NINJUTSU: naruto, infere o jutso: RASENGAN" +
                " contra o inimigo: Sasuke para causar danos.", response.mensagem());
        assertEquals(jutsu.getDano(), response.danoACausar());
        assertEquals(idAtacado, response.idInimigo());

        verify(personagemServiceImp).salvar(atacante);
    }


    @Test
    void validarDesvioBemSucedido() {
        Long idDeDefesa = 1L;
        Long idAtacante = 2L;
        Long idDoJutsu = 1L;

        NinjaDeNinjutsu atacante = PersonagemFixture.ninjaDeNinjutsuEntity(idAtacante,"naruto");
        NinjaDeTaijutsu atacado = PersonagemFixture.ninjaDeTaijutsuEntity(idDeDefesa,"Sasuke");

        Jutsu jutsu = JutsuFixture.entity(idDoJutsu, "Rasengan", 20, 10);
        atacante.adicionarJutsu(jutsu);

        Map<Long, Personagem> jogadores = new HashMap<>();
        jogadores.put(atacante.getId(), atacante);
        jogadores.put(atacado.getId(), atacado);

        ReflectionTestUtils.setField(service, "jogoAtivo", true);
        ReflectionTestUtils.setField(service, "defesaPendente", true);
        ReflectionTestUtils.setField(service, "jogadores", jogadores);
        ReflectionTestUtils.setField(service, "ninjaAtacante", atacante);
        ReflectionTestUtils.setField(service, "ninjaAtacado", atacado);
        ReflectionTestUtils.setField(service, "jutsuDeAtaque", jutsu);

        JogoServiceImpl spyService = Mockito.spy(service);
        doReturn(true).when(spyService).respostaRandimica();

        String resposta = spyService.desviar(idDeDefesa);

        assertEquals("O ninja de TAIJUTSU, usado seu poderes conseguiu exito ao " +
                "se defender do ataque lhe inferido.", resposta);
    }

    @Test
    void validarDesvioMalSucedido() {
        Long idDeDefesa = 1L;
        Long idAtacante = 2L;
        Long idDoJutsu = 1L;

        NinjaDeNinjutsu atacante = PersonagemFixture.ninjaDeNinjutsuEntity(idAtacante,"naruto");
        NinjaDeTaijutsu atacado = PersonagemFixture.ninjaDeTaijutsuEntity(idDeDefesa,"Sasuke");

        Jutsu jutsu = JutsuFixture.entity(idDoJutsu, "Rasengan", 20, 10);
        atacante.adicionarJutsu(jutsu);

        Map<Long, Personagem> jogadores = new HashMap<>();
        jogadores.put(atacante.getId(), atacante);
        jogadores.put(atacado.getId(), atacado);

        ReflectionTestUtils.setField(service, "jogoAtivo", true);
        ReflectionTestUtils.setField(service, "defesaPendente", true);
        ReflectionTestUtils.setField(service, "jogadores", jogadores);
        ReflectionTestUtils.setField(service, "ninjaAtacante", atacante);
        ReflectionTestUtils.setField(service, "ninjaAtacado", atacado);
        ReflectionTestUtils.setField(service, "jutsuDeAtaque", jutsu);

        JogoServiceImpl spyService = Mockito.spy(service);
        doReturn(false).when(spyService).respostaRandimica();

        String resposta = spyService.desviar(idDeDefesa);

        assertEquals("O ninja de TAIJUTSU, não conseguiu exito ao se defender do" +
                " ataque lhe inferido, e perdeu 20 vidas!", resposta);
    }
}