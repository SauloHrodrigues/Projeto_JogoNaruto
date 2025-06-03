package com.naruto.repository;

import com.naruto.model.Personagem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonagemRepository extends JpaRepository<Personagem,Long> {

   Optional<Personagem> findByNome(String nome);
}
