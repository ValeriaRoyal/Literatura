package com.exemplo.literatura.model;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Entidade JPA para representar um livro no catálogo
 * Atributos conforme especificação: Título, Autor, Idiomas, Número de Downloads
 */
@Entity
@Table(name = "livros")
public class Livro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 500)
    private String titulo;
    
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;
    
    @Column(nullable = false, length = 10)
    private String idioma; // Apenas o primeiro idioma da lista
    
    @Column(name = "numero_downloads")
    private Integer numeroDownloads;
    
    @Column(name = "gutenberg_id", unique = true)
    private Long gutenbergId; // ID original da API para evitar duplicatas
    
    // Construtores
    public Livro() {}
    
    public Livro(String titulo, Autor autor, String idioma, Integer numeroDownloads) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDownloads = numeroDownloads;
    }
    
    public Livro(String titulo, Autor autor, String idioma, Integer numeroDownloads, Long gutenbergId) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDownloads = numeroDownloads;
        this.gutenbergId = gutenbergId;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public Autor getAutor() {
        return autor;
    }
    
    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    
    public String getIdioma() {
        return idioma;
    }
    
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    
    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }
    
    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }
    
    public Long getGutenbergId() {
        return gutenbergId;
    }
    
    public void setGutenbergId(Long gutenbergId) {
        this.gutenbergId = gutenbergId;
    }
    
    // Métodos utilitários
    public String getNomeIdioma() {
        return switch (idioma != null ? idioma.toLowerCase() : "") {
            case "pt" -> "Português";
            case "en" -> "Inglês";
            case "fr" -> "Francês";
            case "es" -> "Espanhol";
            case "de" -> "Alemão";
            case "it" -> "Italiano";
            default -> idioma != null ? idioma.toUpperCase() : "Desconhecido";
        };
    }
    
    public String getResumo() {
        return String.format("📚 %s - %s (%s) - %,d downloads",
            titulo,
            autor != null ? autor.getNome() : "Autor desconhecido",
            getNomeIdioma(),
            numeroDownloads != null ? numeroDownloads : 0);
    }
    
    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor=" + (autor != null ? autor.getNome() : "null") +
                ", idioma='" + idioma + '\'' +
                ", numeroDownloads=" + numeroDownloads +
                ", gutenbergId=" + gutenbergId +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(gutenbergId, livro.gutenbergId) ||
               (Objects.equals(titulo, livro.titulo) && Objects.equals(autor, livro.autor));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(titulo, autor, gutenbergId);
    }
}
