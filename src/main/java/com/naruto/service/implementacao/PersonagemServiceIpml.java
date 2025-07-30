package com.naruto.service.implementacao;

import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.dto.personagem.NovoPersonagemDTO;
import com.naruto.dto.personagem.PersonagemResponseDto;
import com.naruto.exceptions.personagem.JutsuJaExistenteException;
import com.naruto.exceptions.personagem.JutsuNaoEncontradoException;
import com.naruto.exceptions.personagem.PersonagemJaCadastradoException;
import com.naruto.exceptions.personagem.PersonagemNaoEncontradoException;
import com.naruto.mappers.JutsuMapper;
import com.naruto.mappers.PersonagemMapper;
import com.naruto.model.Jutsu;
import com.naruto.model.Personagem;
import com.naruto.repository.JutsuRepository;
import com.naruto.repository.PersonagemRepository;
import com.naruto.service.PersonagemService;
import com.naruto.service.PersonagemSeviceUsoInterno;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PersonagemServiceIpml implements PersonagemService, PersonagemSeviceUsoInterno {

    private PersonagemMapper personagemMapper = PersonagemMapper.INSTANCE;
    private JutsuMapper jutsuMapper = JutsuMapper.INSTANCE;

    private final PersonagemRepository repository;
    private final JutsuRepository jutsuRepository;

    @Override
    public PersonagemResponseDto novoPersonagem(NovoPersonagemDTO dto) {
        validarNovoPersonagem(dto.nome());
        Personagem personagem = personagemMapper.toEntity(normalize(dto));
        Jutsu jutsu = jutsuMapper.toEntity(dto.jutsuRequestDto());
        Jutsu jutsuSalvo= jutsuRepository.save(jutsu);
        personagem.adicionarJutsu(jutsuSalvo);
        Personagem personagemSalvo = repository.save(personagem);
        return personagemMapper.toResponseDto(personagemSalvo);
    }

    @Override
    public List<PersonagemResponseDto> listarPersonagens(){
        List<Personagem> personagems = repository.findAll();
        return personagemMapper.toResponseDtoList(personagems);
    }

    @Override
    public Personagem salvar(Personagem personagem){
        return repository.save(personagem);
    }

    @Override
    public Jutsu buscarJutsu(Long id){
        return jutsuRepository.findById(id).orElseThrow(()->new JutsuNaoEncontradoException(
                "O jutsu com ID \' " + id +"\' não foi encontrado."));
    }

    @Override
    public Personagem buscar(Long id){
        Personagem personagem= repository.findById(id).orElseThrow(()->new PersonagemNaoEncontradoException(
                "O personagem com id: \'" + id +"\' não foi encontrado."));
        return personagem;
    }

    @Override
    public Personagem buscar(String nome){
        return repository.findByNome(nome).orElseThrow(()-> new PersonagemNaoEncontradoException(
                "O personagem com o nome \'" + nome+"\' não foi encontrado."));
    }

    @Override
    public PersonagemResponseDto converterResponseDto(Personagem personagem){
        return personagemMapper.toResponseDto(personagem);
    }

    @Transactional
    public PersonagemResponseDto adicionarJutsu(Long idPersonagem, JutsuRequestDto jutsuDto){
        Personagem personagem = buscar(idPersonagem);
        validaPersonagemSemOJutsu(personagem,jutsuDto.nome());
        Jutsu jutsu = jutsuMapper.toEntity(jutsuDto);
        Jutsu novoJutsu = jutsuRepository.save(jutsu);
        personagem.adicionarJutsu(novoJutsu);
        Personagem personagemSalvo = repository.save(personagem);
        return personagemMapper.toResponseDto(personagemSalvo);
    }

    @Override
    public void apagar(Long id){
        Personagem personagem = buscar(id);
        repository.delete(personagem);
    }

    public void validaPersonagemSemOJutsu(Personagem personagem, String nomeDojutsu){
        String chaveJutsu = nomeDojutsu.toLowerCase();
        if(personagem.getJutsus().containsKey(chaveJutsu)){
            throw new JutsuJaExistenteException(
                    "O personagem "+personagem.getNome()+", já possue o Jutsu: "+
                    nomeDojutsu.toUpperCase());
        }
    }

    protected void validarNovoPersonagem(String nome){
        Optional<Personagem> personagem = repository.findByNome(nome.toLowerCase());

        if(personagem.isPresent()){
            throw new PersonagemJaCadastradoException(
                    "O personagem "+nome+" já está cadastrado no sistema");
        }
    }


    private NovoPersonagemDTO normalize(NovoPersonagemDTO dto){
        return new NovoPersonagemDTO(
                dto.nome().toLowerCase(),
                dto.categoriaNinja(), dto.idade(),
                dto.chakra(), dto.vida(), dto.jutsuRequestDto()
        );
    }


    private JutsuRequestDto normalize(JutsuRequestDto dto){
        return new JutsuRequestDto(
                dto.nome().toLowerCase(),
                dto.dano(),
                dto.consumoDeChakra()
        );
    }
}