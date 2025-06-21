package org.example.challengeliteralura.repository;

import org.example.challengeliteralura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNomeIgnoreCase(String nome);

    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.livros")
    List<Autor> buscarAutoresRegistrados();

    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoFalecimento >= :ano OR a.anoFalecimento IS NULL)")
    List<Autor> findAutoresVivosNoAnoJPQL(Integer ano);


}
