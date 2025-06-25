package com.naruto.service.implementacao;

import com.naruto.dto.jogo.AtaqueRequestDto;
import com.naruto.dto.jogo.AtaqueResponseDto;
import com.naruto.dto.jogo.NovoJogoRequestDto;
import com.naruto.dto.jogo.NovoJogoResponseDto;
import com.naruto.dto.personagem.PersonagemResponseDto;
import com.naruto.exceptions.Jogo.InaptoParaDefesaException;
import com.naruto.exceptions.Jogo.InsuficienciaDeChakrasException;
import com.naruto.exceptions.Jogo.JogadorForaDoJogoException;
import com.naruto.exceptions.Jogo.JogoPendenteException;
import com.naruto.exceptions.Jogo.JutsuNaoPertenceAoJogadoException;
import com.naruto.model.Jutsu;
import com.naruto.model.Personagem;
import com.naruto.service.JogoService;
import com.naruto.service.PersonagemSeviceUsoInterno;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JogoServiceImpl implements JogoService {

    private final PersonagemSeviceUsoInterno personagemService;
    private final Map<Long, Personagem> jogadores = new HashMap<>();
    private Personagem ninjaAtacado;
    private Personagem ninjaAtacante;
    private Jutsu jutsuDeAtaque;
    private boolean jogoAtivo;
    private boolean defesaPendente = false;


    @Override
    public NovoJogoResponseDto novoJogo(NovoJogoRequestDto jogadores) {
        if (jogoAtivo) {
            throw new JogoPendenteException("Há um jogo em andamento. Finalize para iniciar novo jogo.");
        }

        Personagem ninja01 = personagemService.buscar(jogadores.nomeDoCombatente01().toLowerCase());
        Personagem ninja02 = personagemService.buscar(jogadores.nomeDoCombatente02().toLowerCase());
        this.jogadores.put(ninja01.getId(), ninja01);
        this.jogadores.put(ninja02.getId(), ninja02);
        jogoAtivo = true;
        PersonagemResponseDto personagemResponseDto01 = personagemService.converterResponseDto(ninja01);
        PersonagemResponseDto personagemResponseDto02 = personagemService.converterResponseDto(ninja02);
        String mensagem = "Batalha: " + ninja01.getNome().toUpperCase() + " X " + ninja02.getNome().toUpperCase() + " foi travada!!";
        return new NovoJogoResponseDto(personagemResponseDto01, personagemResponseDto02, mensagem);
    }


    @Override
    public AtaqueResponseDto atacarDeJutsu(AtaqueRequestDto dto) {
        validaJogo();
        posicionarJogador(dto.idPersonagem());
        validaJutso(dto.idDoJutsu());
        validarDescontarChakra(ninjaAtacante);
        String mensagem = ninjaAtacante.usarJutsu(jutsuDeAtaque, ninjaAtacado.getNome());
        defesaPendente = true;
        personagemService.salvar(jogadores.get(ninjaAtacante.getId()));
        return new AtaqueResponseDto(mensagem, jutsuDeAtaque.getDano(), ninjaAtacado.getId());
    }

    @Override
    public String desviar(Long id) {
        validaJogo();
        validaJogador(id);
        validaDefesaPendente();
        validaJogadorAptoParaDefesa(id);
        String resposta;

        if (respostaRandimica()) {
            resposta = ninjaAtacado.desviar() + " usando seu poderes conseguiu exito ao" +
                    " se defender do ataque lhe inferido.";
        } else {
            ninjaAtacado.diminuirVidas(jutsuDeAtaque.getDano());
            personagemService.salvar(jogadores.get(ninjaAtacado.getId()));
            resposta = ninjaAtacado.desviar() + "não conseguiu exito ao" +
                    " se defender do ataque lhe inferido, e perdeu " + jutsuDeAtaque.getDano()
                    + " vidas!";
        }
        consumirDefesa();
        return resposta;
    }

    private void posicionarJogador(Long idDoAtacante) {
        validaJogador(idDoAtacante);
        for (Long id : jogadores.keySet()) {
            if (id.equals(idDoAtacante)) {
                ninjaAtacante = jogadores.get(id);
            } else {
                ninjaAtacado = jogadores.get(id);
            }
        }
    }


    private void validarDescontarChakra(Personagem personagem) {
        if (personagem.getChakra() >= jutsuDeAtaque.getConsumoDeChakra()) {
            jogadores.get(personagem.getId()).dimunuirChakra(jutsuDeAtaque.getConsumoDeChakra());
        } else {
            throw new InsuficienciaDeChakrasException("O ninja \'" + personagem.getNome() + "\' não " +
                    " possui Chakra suficiente para realizar a jogada.");
        }
    }


    private void validaDefesaPendente() {
        if (!defesaPendente) {
            throw new InaptoParaDefesaException("Não há defesa a ser feita");
        }
    }

    private void validaJogadorAptoParaDefesa(Long id) {
        if (ninjaAtacante.getId() == id) {
            throw new InaptoParaDefesaException(ninjaAtacante.getNome() +
                    ", você acabou de atacar, aguarde sua vez para jogar!");
        }
    }

    private void validaJogador(Long id) {
        if (!jogadores.containsKey(id)) {
            throw new JogadorForaDoJogoException("O jogador de id: \'" +
                    id + "\' não está jogando.");
        }
    }

    private void validaJutso(Long idJutsu) {
        Jutsu jutsu = personagemService.buscarJutsu(idJutsu);

        if (ninjaAtacante.getJutsus().containsValue(jutsu)) {
            jutsuDeAtaque = jutsu;
        } else {
            throw new JutsuNaoPertenceAoJogadoException("O jutsu de id: \'" +
                    idJutsu + "\' não pertence ao ninja " + ninjaAtacante.getNome());
        }
    }

    private void consumirDefesa() {
        ninjaAtacado = null;
        ninjaAtacante = null;
        jutsuDeAtaque = null;
        defesaPendente = false;
    }

    private void validaJogo() {
        if (!jogoAtivo) {
            throw new JogadorForaDoJogoException("Não há jogo ativo. Inicie um novo jogo!");
        }
    }

    protected boolean respostaRandimica() {
        Random random = new Random();
        return random.nextBoolean();
    }

    private void finalizarJogo() {
        jogoAtivo = false;
        ninjaAtacante = null;
        ninjaAtacado = null;
    }
}