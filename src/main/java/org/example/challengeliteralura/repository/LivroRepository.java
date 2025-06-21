package org.example.challengeliteralura.repository;

import org.example.challengeliteralura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByTitulo(String titulo);

    List<Livro> findAllByIdiomaContainingIgnoreCase(String idioma);

    @Query("SELECT l FROM Livro l ORDER BY l.quantidadeDownloads DESC LIMIT 10")
    List<Livro> buscarTop10LivrosMaisBaixados();
}
