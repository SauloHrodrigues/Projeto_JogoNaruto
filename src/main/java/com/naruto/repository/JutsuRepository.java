package com.naruto.repository;

import com.naruto.model.Jutsu;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JutsuRepository extends JpaRepository<Jutsu,Long> {

    Optional<Jutsu> findByNome(String nome);
}
