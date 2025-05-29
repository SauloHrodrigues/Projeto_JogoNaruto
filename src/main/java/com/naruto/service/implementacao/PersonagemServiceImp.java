package com.naruto.service.implementacao;

import com.naruto.dto.NovoPersonagemDTO;
import com.naruto.dto.PersonagemResponseDto;
import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.mappers.JutsuMapper;
import com.naruto.mappers.PersonagemMapper;
import com.naruto.model.Jutsu;
import com.naruto.model.NinjaDeGenjutsu;
import com.naruto.model.NinjaDeNinjutsu;
import com.naruto.model.NinjaDeTaijutsu;
import com.naruto.model.Personagem;
import com.naruto.repository.JutsuRepository;
import com.naruto.repository.PersonagemRepository;
import com.naruto.service.PersonagemService;
import java.util.List;
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
        switch (dto.categoriaNinja()){
            case NINJATAIJUTSU:
                NinjaDeTaijutsu taijutsu = personagemMapper.toEntityTaijutsu(dto);
                salvarJutsu(taijutsu, dto.jutsuRequestDto());
                repository.save(taijutsu);
                return personagemMapper.toResponse(taijutsu);

            case NINJANINJUTSU:
                NinjaDeNinjutsu ninjutsu = personagemMapper.toEntityNinjutsu(dto);
                salvarJutsu(ninjutsu, dto.jutsuRequestDto());
                repository.save(ninjutsu);
                return personagemMapper.toResponse(ninjutsu);

            case NINJAGENJUTSU:
                NinjaDeGenjutsu genjutsu = personagemMapper.toEntityGenjutsu(dto);
                salvarJutsu(genjutsu, dto.jutsuRequestDto());
                repository.save(genjutsu);
                return personagemMapper.toResponse(genjutsu);

            default:
                return null;
        }
    }

    public List<PersonagemResponseDto> listarPersonagens(){
        List<Personagem> personagems = repository.findAll();
        return personagemMapper.toResponse(personagems);
    }

    public PersonagemResponseDto adicionarJutsu(Long id, JutsuRequestDto jutsuDto){
        Personagem personagem = buscarPersonagem(id);
        salvarJutsu(personagem,jutsuDto);
        return personagemMapper.toResponse(personagem);
    }

    public void apagar(Long id){
        Personagem personagem = buscarPersonagem(id);
        repository.delete(personagem);
    }


    private Jutsu salvarJutsu(Personagem personagem, JutsuRequestDto jutsuDto){
        Jutsu jutsu = jutsuMapper.toEntity(jutsuDto);
        jutsuRepository.save(jutsu);
        jutsu.setPersonagem(personagem);
        personagem.adicionarJutsu(jutsu);
        repository.save(personagem);
        return jutsu;
    }

    private Personagem buscarPersonagem(Long id){
        return repository.findById(id).orElseThrow(()-> new RuntimeException("Personagem " +
                "não encontrao."));
    }




}
