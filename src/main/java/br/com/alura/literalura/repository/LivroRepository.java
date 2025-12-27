package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {


    @Query("SELECT l FROM Livro l WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('% ', :palavra, ' %')) " +
            "OR LOWER(l.titulo) LIKE LOWER(CONCAT(:palavra, ' %')) " +
            "OR LOWER(l.titulo) LIKE LOWER(CONCAT('% ', :palavra)) " +
            "OR LOWER(l.titulo) = LOWER(:palavra)")
    List<Livro> findByPalavraNoTitulo(@Param("palavra") String palavra);


    Optional<Livro> findByTituloIgnoreCase(String titulo);


    List<Livro> findByIdioma(String idioma);


    Long countByIdioma(String idioma);


    @Query("SELECT DISTINCT l.idioma FROM Livro l ORDER BY l.idioma")
    List<String> findDistinctIdiomas();
}