package com.naruto.service.implementacao;

import com.naruto.dto.jogo.AtaqueDto;
import com.naruto.dto.jogo.NovoJogoDto;
import com.naruto.mappers.JogoMapper;
import com.naruto.model.Jogo;
import com.naruto.model.Jutsu;
import com.naruto.model.Personagem;
import com.naruto.repository.JogoRepository;
import com.naruto.service.JogoService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JogoServiceImpl implements JogoService {

    private final JogoRepository repository;
    private final PersonagemServiceImp personagemService;
    private final JogoMapper mapper;
    private final Map<Long,Personagem>ninjas = new HashMap<>();

    private Jogo jogo;

    private long jogadorAtacante;

    @Override
    public void novoJogo(NovoJogoDto jogadores) {

        Personagem ninja01 = personagemService.buscarPersonagem(jogadores.nomeDoCombatente01().toLowerCase());
        Personagem ninja02 = personagemService.buscarPersonagem(jogadores.nomeDoCombatente02().toLowerCase());
        jogo = mapper.toEntity(ninja01,ninja02);
        repository.save(jogo);
        ninjas.put(jogo.getNinja01().getId(),jogo.getNinja01());
        ninjas.put(jogo.getNinja02().getId(),jogo.getNinja02());
    }

    @Override
    public String atacarDeJutsu(AtaqueDto dto) {
        System.out.println("Entrou no atacar");
        validaJogador(dto.idPersonagem());
        validaJutsoDoJogado(dto.idPersonagem(), dto.idDoJutsu());
        validaAtaque(dto.idPersonagem(), dto.idDoJutsu());
        return null;
    }

    @Override
    public void desviar() {

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

    private boolean validaAtaque(Long idNinja, Long idJutsu){
        validaJogador(idNinja);
        validaJutsoDoJogado(idNinja, idJutsu);
        Personagem ninjaAtacante = ninjas.get(idNinja);

        if(ninjaAtacante.getChakra()<= 0){
            throw new RuntimeException("O Id \'"+idNinja+"\' não está jogando.");
        }
        return true;
    }

    private Personagem ninjaAtacado(Long ninjaAtacante){
        ninjas.forEach((id, ninja) -> {
            System.out.println("ID: " + id + ", Ninja: " + ninja.getNome());
        });
        return null;
    }
    private boolean validaJogador(Long id){
        if (ninjas.get(id) == null) {
            throw new RuntimeException("O Id \'"+id+"\' não está jogando.");
        }
        return true;
    }

    private boolean validaJutsoDoJogado(Long idJogador, Long idJutsu){
        Personagem ninja = ninjas.get(idJogador);
        Jutsu jutsu = personagemService.retornaJutsu(idJutsu);
        if((ninja.getJutsus().stream().anyMatch(j -> j.getId().equals(idJutsu)))){
            return true;
        }
        throw new RuntimeException("O Jutsu de Id \'"+idJutsu+"\' não consta como um jutsu do ninja"+
                ninja.getNome()+".");
    }
}
