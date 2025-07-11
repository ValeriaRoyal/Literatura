package com.exemplo.literatura.repository;

import com.exemplo.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações com autores
 */
@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    
    // Consulta derivada para buscar autor por nome exato
    Optional<Autor> findByNome(String nome);
    
    // Consulta derivada para buscar autores por nome (case insensitive)
    List<Autor> findByNomeContainingIgnoreCase(String nome);
    
    // Consulta derivada para buscar autores vivos (ano de morte nulo)
    List<Autor> findByAnoMorteIsNull();
    
    // Consulta derivada para buscar autores por período
    List<Autor> findByAnoNascimentoBetween(Integer anoInicio, Integer anoFim);
    
    // Consulta derivada para buscar autores ordenados por nome
    List<Autor> findAllByOrderByNome();
    
    // Consulta personalizada para autores com mais livros
    @Query("SELECT a FROM Autor a JOIN a.livros l GROUP BY a ORDER BY COUNT(l) DESC")
    List<Autor> findAutoresComMaisLivros();
}
