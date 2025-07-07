package com.naruto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.dto.jutsu.JutsuResponseDto;
import com.naruto.dto.personagem.NovoPersonagemDTO;
import com.naruto.dto.personagem.PersonagemResponseDto;
import com.naruto.exceptions.personagem.PersonagemJaCadastradoException;
import com.naruto.fixture.JutsuFixture;
import com.naruto.fixture.PersonagemFixture;
import com.naruto.model.Jutsu;
import com.naruto.model.NinjaDeGenjutsu;
import com.naruto.model.NinjaDeNinjutsu;
import com.naruto.model.NinjaDeTaijutsu;
import com.naruto.model.Personagem;
import com.naruto.service.implementacao.PersonagemServiceIpml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonagemController.class)
class PersonagemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;   // já configurado pelo Spring Boot Test

    @MockBean
    private PersonagemServiceIpml service;

    private NovoPersonagemDTO narutoRequestDto;
    private NovoPersonagemDTO sasukeRequestDto;
    private NovoPersonagemDTO kakashiRequestDto;
    private JutsuRequestDto punhoSuaveRequestDto;
    private JutsuRequestDto socoRequestDto;
    private JutsuRequestDto raseganRequestDto;

    private Map<String,Jutsu> jutsus;



    @BeforeEach
    void setUp() {
        socoRequestDto = JutsuFixture.requestDto("soco",5,10);
        punhoSuaveRequestDto = JutsuFixture.requestDto("Punho Suave", 20, 30);
        raseganRequestDto = JutsuFixture.requestDto("Rasengan", 10, 25);

        narutoRequestDto =  PersonagemFixture.novoDto("naruto uzumaki","NINJA_DE_NINJUTSU",10, 10,20,socoRequestDto);
        sasukeRequestDto = PersonagemFixture.novoDto( "sasuke uchiha", "NINJA_DE_TAIJUTSU",15,5, 10,socoRequestDto);
        kakashiRequestDto = PersonagemFixture.novoDto( "kakashi hatake","NINJA_DE_GENJUTSU", 5,10,10,socoRequestDto);
        jutsus = new HashMap<>();
    }


    @Test
    void cadastrar_deveRetornar201_quandoSucesso() throws Exception {
        Jutsu jutsu = JutsuFixture.toEntity(1L,socoRequestDto);
        NinjaDeNinjutsu personagem = (NinjaDeNinjutsu) PersonagemFixture.toEntity(1L,narutoRequestDto);
        personagem.adicionarJutsu(jutsu);
        PersonagemResponseDto resposta = PersonagemFixture.toResponseDto(personagem);

        when(service.novoPersonagem(any())).thenReturn(resposta);

        mockMvc.perform(post("/personagem").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(narutoRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",equalTo(resposta.id().intValue())))
                .andExpect(jsonPath("$.nome", is(resposta.nome())))
                .andExpect(jsonPath("$.chakra", is(resposta.chakra())))
                .andExpect(jsonPath("$.jutsus.soco").exists())
                ;
    }


    @Test
     void cadastrar_deveRetornar409_PersonagemJaCadastrado() throws Exception {

        when(service.novoPersonagem(kakashiRequestDto)).thenThrow(new PersonagemJaCadastradoException(
                "O personagem kakashi hatake já está cadastrado no sistema"));

        mockMvc.perform(post("/personagem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(kakashiRequestDto)))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mensagem", is("O personagem "+ kakashiRequestDto.nome()+" já está cadastrado no sistema")));
    }

    @Test
    void listar() throws Exception {
        NinjaDeTaijutsu personagem = (NinjaDeTaijutsu) PersonagemFixture.toEntity(1L,sasukeRequestDto);
        PersonagemResponseDto resposta = PersonagemFixture.toResponseDto(personagem);
        List<PersonagemResponseDto> listPersonagemResponseDtos = new ArrayList<>();
        listPersonagemResponseDtos.add(resposta);

        when(service.listarPersonagens()).thenReturn(listPersonagemResponseDtos);

        mockMvc.perform(get("/personagem"))
                .andExpect(status().isOk())
                        .andExpect(jsonPath("$.length()").value(1))
                        .andExpect(jsonPath("$.[0].id").value(resposta.id()))
                        .andExpect(jsonPath("$.[0].nome").value(resposta.nome()))
                        .andExpect(jsonPath("$.[0].idade").value(resposta.idade()))
                        .andExpect(jsonPath("$.[0].jutsus.length()").value(resposta.jutsus().size()))
                        .andExpect(jsonPath("$.[0].chakra").value(resposta.chakra()))
                        .andExpect(jsonPath("$.[0].vida").value(resposta.vida())
                        );

    }



    @Test
    void adicionarJutsu() throws Exception {

        Long id = 5L;
        NinjaDeGenjutsu personagem= (NinjaDeGenjutsu) PersonagemFixture.toEntity(5L,kakashiRequestDto);
        Jutsu jutsu = JutsuFixture.toEntity(2L,raseganRequestDto);
        personagem.adicionarJutsu(jutsu);

        PersonagemResponseDto resposta = PersonagemFixture.toResponseDto(personagem);

        when(service.adicionarJutsu(id,raseganRequestDto)).thenReturn(resposta);

        mockMvc.perform(post("/personagem/{id}/adicionar_jutsu",id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(raseganRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.jutsus.['Rasengan']").exists())
                .andExpect(jsonPath("$.id",equalTo(resposta.id().intValue())))
                .andExpect(jsonPath("$.jutsus.['Rasengan']").value(jutsu))
         ;
    }

    @Test
    void apagar() {
    }
}