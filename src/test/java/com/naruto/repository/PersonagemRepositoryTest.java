package com.naruto.repository;

import com.naruto.model.NinjaDeNinjutsu;
import com.naruto.model.Personagem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
public class PersonagemRepositoryTest {

    @Autowired
    private PersonagemRepository repository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void validaCriacaoDePersonagemNoBanco(){
        Personagem personagem = new NinjaDeNinjutsu();
        personagem.setNome("miria");
        personagem.adicionarChakra(10);
        personagem.setIdade(21);
        Personagem personagemSalvo= repository.save(personagem);
        Personagem personagemResposta = testEntityManager.find(Personagem.class,personagemSalvo.getId());

        assertEquals(personagemSalvo.getId(),personagemResposta.getId());
        assertEquals(personagemSalvo.getNome(),personagemResposta.getNome());
        assertEquals(personagemSalvo.getChakra(), personagemResposta.getChakra());
    }

    @Test
    void deveLancarExcecao_QuandoNomeForNulo() {
        var personagem = new NinjaDeNinjutsu();

        assertThrows(DataIntegrityViolationException.class, () -> repository.save(personagem));
    }
}
