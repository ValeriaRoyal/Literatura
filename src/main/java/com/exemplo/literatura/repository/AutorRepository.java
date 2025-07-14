package com.exemplo.literatura.repository;

import com.exemplo.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações com autores
 * Inclui consultas derivadas para as funcionalidades obrigatórias
 */
@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    
    // Consulta derivada para buscar autor por nome exato
    Optional<Autor> findByNome(String nome);
    
    // Consulta derivada para buscar autores por nome (case insensitive)
    List<Autor> findByNomeContainingIgnoreCase(String nome);
    
    // FUNCIONALIDADE OBRIGATÓRIA 1: Lista de autores ordenados por nome
    List<Autor> findAllByOrderByNome();
    
    // FUNCIONALIDADE OBRIGATÓRIA 2: Autores vivos em determinado ano
    // Um autor estava vivo em um ano se:
    // - Nasceu antes ou no ano especificado E
    // - Morreu depois do ano especificado OU ainda está vivo (ano_morte é null)
    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoMorte IS NULL OR a.anoMorte >= :ano)")
    List<Autor> findAutoresVivosNoAno(@Param("ano") Integer ano);
    
    // Consulta derivada para buscar autores vivos (ano de morte nulo)
    List<Autor> findByAnoMorteIsNull();
    
    // Consulta derivada para buscar autores por período de nascimento
    List<Autor> findByAnoNascimentoBetween(Integer anoInicio, Integer anoFim);
    
    // Consulta personalizada para autores com mais livros
    @Query("SELECT a FROM Autor a JOIN a.livros l GROUP BY a ORDER BY COUNT(l) DESC")
    List<Autor> findAutoresComMaisLivros();
    
    // Consulta para contar total de autores
    @Query("SELECT COUNT(a) FROM Autor a")
    Long contarTotalAutores();
    
    // Consulta para autores por século de nascimento
    @Query("SELECT a FROM Autor a WHERE a.anoNascimento BETWEEN :anoInicio AND :anoFim ORDER BY a.anoNascimento")
    List<Autor> findAutoresPorSeculo(@Param("anoInicio") Integer anoInicio, @Param("anoFim") Integer anoFim);
}
