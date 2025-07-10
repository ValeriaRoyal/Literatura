package com.exemplo.literatura.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "livros")
public class Livro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String titulo;
    
    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;
    
    private String genero;
    
    @Column(columnDefinition = "TEXT")
    private String sinopse;
    
    private String isbn;
    
    @Column(name = "numero_paginas")
    private Integer numeroPaginas;
    
    private String editora;
    
    private String idioma;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;
    
    // Construtores
    public Livro() {}
    
    public Livro(String titulo, LocalDate dataPublicacao, String genero, Autor autor) {
        this.titulo = titulo;
        this.dataPublicacao = dataPublicacao;
        this.genero = genero;
        this.autor = autor;
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
    
    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }
    
    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public String getSinopse() {
        return sinopse;
    }
    
    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }
    
    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }
    
    public String getEditora() {
        return editora;
    }
    
    public void setEditora(String editora) {
        this.editora = editora;
    }
    
    public String getIdioma() {
        return idioma;
    }
    
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    
    public Autor getAutor() {
        return autor;
    }
    
    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}
