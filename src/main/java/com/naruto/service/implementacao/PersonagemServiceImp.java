package com.naruto.service.implementacao;

import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.dto.personagem.NovoPersonagemDTO;
import com.naruto.dto.personagem.PersonagemResponseDto;
import com.naruto.exceptions.Jogo.JogadorForaDoJogoException;
import com.naruto.exceptions.personagem.JutsuJaExistenteEncontradoException;
import com.naruto.exceptions.personagem.PersonagemNaoEncontradoException;
import com.naruto.mappers.JutsuMapper;
import com.naruto.mappers.PersonagemMapper;
import com.naruto.model.Jutsu;
import com.naruto.model.Personagem;
import com.naruto.repository.JutsuRepository;
import com.naruto.repository.PersonagemRepository;
import com.naruto.service.PersonagemService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonagemServiceImp implements PersonagemService {

    private final PersonagemMapper personagemMapper;
    private final JutsuMapper jutsuMapper;
    private final PersonagemRepository repository;
    private final JutsuRepository jutsuRepository;

    public PersonagemResponseDto novoPersonagem(NovoPersonagemDTO dto) {
        Personagem personagem = personagemMapper.toEntity(normalize(dto));
        salvarJutsu(personagem,dto.jutsuRequestDto());

        return null;
    }

    public List<PersonagemResponseDto> listarPersonagens(){
        List<Personagem> personagems = repository.findAll();
        return personagemMapper.toResponseDto(personagems);
    }

    public PersonagemResponseDto adicionarJutsu(Long id, JutsuRequestDto jutsuDto){
        Personagem personagem = buscar(id);
        salvarJutsu(personagem, normalize(jutsuDto));
        return personagemMapper.toResponseDto(personagem);
    }

    public void apagar(Long id){
        Personagem personagem = buscar(id);
        repository.delete(personagem);
    }


    private Jutsu salvarJutsu(Personagem personagem, JutsuRequestDto jutsuDto){
        validarSeJutsuJaExiste(jutsuDto.nome());
        Jutsu jutsu = jutsuMapper.toEntity(jutsuDto);
        jutsuRepository.save(jutsu);
        jutsu.setPersonagem(personagem);
        personagem.adicionarJutsu(jutsu);
        repository.save(personagem);
        return jutsu;
    }

    public void validarSeJutsuJaExiste(String nome){
        Optional<Jutsu> jutsu= jutsuRepository.findByNome(nome);

        if(jutsu.isPresent()){
            throw new JutsuJaExistenteEncontradoException("O Jutsu já existe no banco.");
        }
    }

    protected NovoPersonagemDTO normalize(NovoPersonagemDTO dto){
        return new NovoPersonagemDTO(
                dto.nome().toLowerCase(),
                dto.categoriaNinja(),
                dto.chakra(), dto.vida(), dto.jutsuRequestDto()
        );
    }


    protected JutsuRequestDto normalize(JutsuRequestDto dto){
        return new JutsuRequestDto(
                dto.nome().toLowerCase(),
                dto.dano(),
                dto.consumoDeChakra()
        );
    }

    public void salvar(Personagem personagem){
        repository.save(personagem);
    }


    public Jutsu buscarJutsu(Long id){
        return jutsuRepository.findById(id).orElseThrow(()->new RuntimeException(
                "O jutsu com ID \' " + id +"\' não foi encontrado."));
    }


    public Personagem buscar(Long id){
        return repository.findById(id).orElseThrow(()->new PersonagemNaoEncontradoException(
                "O personagem com id: \'" + id +"\' não foi encontrado."));
    }


    public Personagem buscar(String nome){
        return repository.findByNome(nome).orElseThrow(()-> new JogadorForaDoJogoException(
                "O personagem com o nome \' " + nome+"\'  foi encontrado."));
    }
}