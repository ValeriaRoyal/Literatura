package com.exemplo.literatura.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Entidade JPA para representar um autor no catálogo
 */
@Entity
@Table(name = "autores")
public class Autor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String nome;
    
    @Column(name = "ano_nascimento")
    private Integer anoNascimento;
    
    @Column(name = "ano_morte")
    private Integer anoMorte;
    
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Livro> livros;
    
    // Construtores
    public Autor() {}
    
    public Autor(String nome) {
        this.nome = nome;
    }
    
    public Autor(String nome, Integer anoNascimento, Integer anoMorte) {
        this.nome = nome;
        this.anoNascimento = anoNascimento;
        this.anoMorte = anoMorte;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Integer getAnoNascimento() {
        return anoNascimento;
    }
    
    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }
    
    public Integer getAnoMorte() {
        return anoMorte;
    }
    
    public void setAnoMorte(Integer anoMorte) {
        this.anoMorte = anoMorte;
    }
    
    public List<Livro> getLivros() {
        return livros;
    }
    
    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }
    
    // Métodos utilitários
    public String getNomeFormatado() {
        if (nome == null || nome.trim().isEmpty()) {
            return "Autor desconhecido";
        }
        
        // Se o nome contém vírgula (formato "Sobrenome, Nome"), inverte
        if (nome.contains(",")) {
            String[] partes = nome.split(",", 2);
            if (partes.length == 2) {
                return partes[1].trim() + " " + partes[0].trim();
            }
        }
        
        return nome.trim();
    }
    
    public String getPeriodoVida() {
        if (anoNascimento == null && anoMorte == null) {
            return "Período desconhecido";
        }
        
        StringBuilder periodo = new StringBuilder();
        
        if (anoNascimento != null) {
            periodo.append(anoNascimento);
        } else {
            periodo.append("?");
        }
        
        periodo.append(" - ");
        
        if (anoMorte != null) {
            periodo.append(anoMorte);
        } else {
            periodo.append("presente");
        }
        
        return periodo.toString();
    }
    
    public boolean estaVivo() {
        return anoMorte == null;
    }
    
    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", anoNascimento=" + anoNascimento +
                ", anoMorte=" + anoMorte +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autor autor = (Autor) o;
        return Objects.equals(nome, autor.nome);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
