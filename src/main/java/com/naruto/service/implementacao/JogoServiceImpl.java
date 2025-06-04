package com.naruto.service.implementacao;

import com.naruto.dto.jogo.AtaqueRequestDto;
import com.naruto.dto.jogo.AtaqueResponseDto;
import com.naruto.dto.jogo.NovoJogoDto;
import com.naruto.exceptions.Jogo.InsuficienciaDeChakrasException;
import com.naruto.exceptions.Jogo.JogadorForaDoJogoException;
import com.naruto.exceptions.Jogo.JutsuNaoPertenceAoJogadoException;
import com.naruto.mappers.JogoMapper;
import com.naruto.model.Jogo;
import com.naruto.model.Jutsu;
import com.naruto.model.Personagem;
import com.naruto.repository.JogoRepository;
import com.naruto.service.IPersonagem;
import com.naruto.service.JogoService;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JogoServiceImpl implements JogoService {

    private final JogoRepository repository;
    private final IPersonagem iPersonagem;
    private final JogoMapper mapper;
    private final Map<Long,Personagem>ninjas = new HashMap<>();
    private Personagem ninjaAtacado;
    private Personagem ninjaAtacante;
    private Jutsu jutsuDeAtaque;
    private Jogo jogo;


    @Override
    public void novoJogo(NovoJogoDto jogadores) {

        Personagem ninja01 = iPersonagem.buscar(jogadores.nomeDoCombatente01().toLowerCase());
        Personagem ninja02 = iPersonagem.buscar(jogadores.nomeDoCombatente02().toLowerCase());
        jogo = mapper.toEntity(ninja01,ninja02);
        repository.save(jogo);
        ninjas.put(jogo.getNinja01().getId(),jogo.getNinja01());
        ninjas.put(jogo.getNinja02().getId(),jogo.getNinja02());
    }


    @Override
    public AtaqueResponseDto atacarDeJutsu(AtaqueRequestDto dto) {
        ninjaAtacante = validaJogador(dto.idPersonagem(),dto.idDoJutsu());
        jutsuDeAtaque = validaJutsoDoJogado(ninjaAtacante, dto.idDoJutsu());
        descontarChakra(ninjaAtacante,1);
        ninjaAtacado = retornaNinjaAtacado();
        String mensagem =  ninjaAtacante.usarJutsu(jutsuDeAtaque, ninjaAtacado.getNome());
        return mapper.toResponse(mensagem, jutsuDeAtaque.getDano(), ninjaAtacado.getId());
    }


    @Override
    public void desviar() {
//        if(getResultadoRadomico()){
//            //deviado - ninja atacante perde 1 ponto
//            ninjas.get(ninjaAtacante.getId()).dimunuirChakra(1);
//
//        } else {
//            não deviado regra: cada 3 danos - uma vida
            int vidasPerdidas = ninjaAtacado.getVida() - (jutsuDeAtaque.getDano()/3);
            ninjas.get(ninjaAtacado).diminuirVidas(vidasPerdidas);
//        }
        validaNovaRodada();

    }

    private void validaNovaRodada(){
        validarChakra();
        // validar vida

        ninjaAtacante= null;
        ninjaAtacado = null;
    }

    @Override
    public void placarDoJogo() {

    }

    @Override
    public void finalizarJogo() {

    }

    @Override
    public void visualizarHistórico() {

    }

    private boolean getResultadoRadomico(){
        Random random = new Random();
        return random.nextBoolean();
    }

    private boolean descontarChakra(Personagem personagem, int chakra){
        validarChakra(personagem,chakra);
        ninjas.get(personagem.getId()).dimunuirChakra(chakra);
        return true;
    }
    private boolean aumentarChakra(Personagem personagem, int chakra){
        personagem.adicionarChakra(chakra);
        return true;
    }

    private Personagem retornaNinjaAtacado(){
        return ninjas.entrySet().stream()
                .filter(entry -> !entry.getValue().equals(ninjaAtacante))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    private void validarChakra(){
        validarChakra(ninjaAtacante, ninjaAtacado.getChakra());
        validarChakra(ninjaAtacado, ninjaAtacado.getChakra());
    }
    private void validarChakra(Personagem personagem, int chakra){
        if(personagem.getChakra() <= chakra){
            throw new InsuficienciaDeChakrasException("O ninja \'"+ personagem.getNome()+"\' não " +
                    " possui Chakra suficiente para realizar o ataque.");
        }

        if(chakra <= 0){
            throw new InsuficienciaDeChakrasException("O ninja \'"+ personagem.getNome()+" não" +
                    " possui Chakras para nova jogada.");
        }
    }

    private Jutsu validaJutsoDoJogado(Personagem personagem, Long idJutsu){
        Jutsu jutsu = validaJutsu(idJutsu);

        if (personagem.getJutsus().contains(jutsu)){
            return jutsu;
        }
        throw new JutsuNaoPertenceAoJogadoException("O jutsu de id: \'"+idJutsu+"\' não pertence ao ninja "+
                personagem.getNome());
    }


    private Personagem validaJogador(Long idPersonagem, Long idJutsu){
        Personagem personagem = validarPersonagem(idPersonagem);

        if (!ninjas.containsValue(personagem)) {
            throw new JogadorForaDoJogoException("O jogador de id: \'"+idPersonagem+"\' não está jogando.");
        }

        return personagem;
    }

    private Jutsu validaJutsu(Long idJutsu){
        return iPersonagem.buscarJutsu(idJutsu);
    }

    private Personagem validarPersonagem(Long id){
        return iPersonagem.buscar(id);
    }

}