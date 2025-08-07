package com.naruto.integracao;

import com.naruto.dto.jutsu.JutsuRequestDto;
import com.naruto.dto.personagem.NovoPersonagemDTO;
import com.naruto.dto.personagem.PersonagemResponseDto;
import com.naruto.fixture.PersonagemFixture;
import org.springframework.core.env.Environment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/limpa_banco.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PersonagemIt {

    @Autowired
    TestRestTemplate template;

    @Autowired
    private Environment environment;

    @Test
    public void contextLoad(){
//        RODA O DOCKER
// $ docker-compose -f docker-compose.test.yml up -d
    }

    @Test
    public void deveCriarUmPersonagemComSucesso(){
        NovoPersonagemDTO dto = PersonagemFixture.novoDto("saulo","NINJA_DE_NINJUTSU",50,10,10,
                new JutsuRequestDto("chute",10,12));
        ResponseEntity<PersonagemResponseDto> sut= template.postForEntity("/personagem",dto, PersonagemResponseDto.class);
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(sut.getBody().id()).isNotNull();
        assertThat(sut.getBody().nome()).isEqualTo(dto.nome());
        assertThat(sut.getBody().chakra()).isEqualTo(dto.chakra());
        assertThat(sut.getBody().idade()).isEqualTo(dto.idade());
        assertThat(sut.getBody().jutsus().size()).isEqualTo(1);

    }
}