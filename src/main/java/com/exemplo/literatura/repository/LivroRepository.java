package com.exemplo.literatura.repository;

import com.exemplo.literatura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações com livros
 * Inclui consultas derivadas para busca por idioma
 */
@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    
    // Consulta derivada para buscar livros por idioma
    List<Livro> findByIdioma(String idioma);
    
    // Consulta derivada para buscar livros por idioma ordenados por título
    List<Livro> findByIdiomaOrderByTitulo(String idioma);
    
    // Consulta derivada para buscar livros por idioma ordenados por downloads (decrescente)
    List<Livro> findByIdiomaOrderByNumeroDownloadsDesc(String idioma);
    
    // Consulta derivada para buscar por título (case insensitive)
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    
    // Consulta derivada para buscar por nome do autor
    List<Livro> findByAutorNomeContainingIgnoreCase(String nomeAutor);
    
    // Consulta para verificar se já existe um livro com o mesmo Gutenberg ID
    Optional<Livro> findByGutenbergId(Long gutenbergId);
    
    // Consulta para buscar todos os livros ordenados por título
    List<Livro> findAllByOrderByTitulo();
    
    // Consulta para buscar todos os livros ordenados por downloads (decrescente)
    List<Livro> findAllByOrderByNumeroDownloadsDesc();
    
    // Consulta personalizada para estatísticas por idioma
    @Query("SELECT l.idioma, COUNT(l) FROM Livro l GROUP BY l.idioma ORDER BY COUNT(l) DESC")
    List<Object[]> contarLivrosPorIdioma();
    
    // Consulta personalizada para livros mais populares
    @Query("SELECT l FROM Livro l WHERE l.numeroDownloads IS NOT NULL ORDER BY l.numeroDownloads DESC")
    List<Livro> findLivrosMaisPopulares();
    
    // Consulta para verificar se existe livro com título e autor específicos
    boolean existsByTituloAndAutorNome(String titulo, String nomeAutor);
}
