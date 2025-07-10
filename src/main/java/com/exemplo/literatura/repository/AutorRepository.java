package com.exemplo.literatura.repository;

import com.exemplo.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    
    Optional<Autor> findByNome(String nome);
    
    List<Autor> findByNacionalidade(String nacionalidade);
    
    @Query("SELECT a FROM Autor a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Autor> findByNomeContainingIgnoreCase(@Param("nome") String nome);
    
    @Query("SELECT a FROM Autor a WHERE a.dataMorte IS NULL")
    List<Autor> findAutoresVivos();
    
}
