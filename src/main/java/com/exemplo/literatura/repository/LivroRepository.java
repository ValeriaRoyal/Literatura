package com.exemplo.literatura.repository;

import com.exemplo.literatura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    
    Optional<Livro> findByTitulo(String titulo);
    
    Optional<Livro> findByIsbn(String isbn);
    
    List<Livro> findByGenero(String genero);
    
    List<Livro> findByAutorId(Long autorId);
    
    List<Livro> findByAutorNome(String nomeAutor);
    
    @Query("SELECT l FROM Livro l WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))")
    List<Livro> findByTituloContainingIgnoreCase(@Param("titulo") String titulo);
    
    @Query("SELECT l FROM Livro l WHERE l.dataPublicacao BETWEEN :dataInicio AND :dataFim")
    List<Livro> findByDataPublicacaoBetween(@Param("dataInicio") LocalDate dataInicio, 
                                           @Param("dataFim") LocalDate dataFim);
    
    List<Livro> findByEditora(String editora);
    
    List<Livro> findByIdioma(String idioma);
    
}
