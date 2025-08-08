package com.naruto.integracao;

import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.dto.personagem.NovoPersonagemDTO;
import com.naruto.dto.personagem.PersonagemResponseDto;
import com.naruto.fixture.JutsuFixture;
import com.naruto.fixture.PersonagemFixture;
import com.naruto.model.Jutsu;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/cria_tabelas_no_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = {"/reset_banco.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PersonagemIt {

    @Autowired
    TestRestTemplate template;


    @Test
    public void contextLoad(){
    }

    @Test
    @DisplayName("Deve adicionar um novo personagem.")
    public void deveCriarUmPersonagemComSucesso(){
        JutsuRequestDto jutsu = JutsuFixture.requestDto("soco",10,15);
        NovoPersonagemDTO dto = PersonagemFixture.novoDto("Tereza","NINJA_DE_NINJUTSU",50,10,10, jutsu);
        ResponseEntity<PersonagemResponseDto> sut= template.postForEntity("/personagem",dto, PersonagemResponseDto.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(sut.getBody().id()).isNotNull();
        assertThat(sut.getBody().nome()).isEqualTo(dto.nome().toLowerCase());
        assertThat(sut.getBody().chakra()).isEqualTo(dto.chakra());
        assertThat(sut.getBody().idade()).isEqualTo(dto.idade());
        assertThat(sut.getBody().jutsus().size()).isEqualTo(1);

    }

    @Test
    @DisplayName("Deve retornar uma lista com todos os personagem cadastrados no banco.")
    @Sql(scripts = {"/cria_tres_personagens_no_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void listarPersonagens() {
        ResponseEntity<List<PersonagemResponseDto>> resposta = template.exchange(
                "/personagem",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PersonagemResponseDto>>() {}
        );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Deve adicionar um jutsu a um personagem.")
    void deveAdicionarJutsuComSucesso() {
        JutsuRequestDto jutsu = JutsuFixture.requestDto("soco",10,15);
        NovoPersonagemDTO dto = PersonagemFixture.novoDto("Tereza","NINJA_DE_NINJUTSU",50,10,10, jutsu);
        PersonagemResponseDto personagem = (template.postForEntity("/personagem",dto, PersonagemResponseDto.class)).getBody();
        Long id = personagem.id();
        JutsuRequestDto novoJutso = JutsuFixture.requestDto("chute",40,60);

        ResponseEntity<PersonagemResponseDto> resposta = template.postForEntity("/personagem/"+id+"/adicionar_jutsu",novoJutso, PersonagemResponseDto.class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resposta.getBody().jutsus().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Deve deletar um personagem do banco.")
    void deveDeletarUmpersonagemDoBanco() {
        JutsuRequestDto jutsu = JutsuFixture.requestDto("soco",10,15);
        NovoPersonagemDTO dto = PersonagemFixture.novoDto("Tereza","NINJA_DE_NINJUTSU",50,10,10, jutsu);
        PersonagemResponseDto personagem = (template.postForEntity("/personagem",dto, PersonagemResponseDto.class)).getBody();
        Long id = personagem.id();

        ResponseEntity<Void> resposta = template.exchange(
                "/personagem/" + id+"/apagar",
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(resposta.getBody()).isNull();
    }
}