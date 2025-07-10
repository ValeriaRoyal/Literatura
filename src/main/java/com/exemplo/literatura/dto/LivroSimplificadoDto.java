package com.exemplo.literatura.dto;

import java.util.Objects;

/**
 * DTO simplificado para representar informações básicas de um livro
 */
public class LivroSimplificadoDto {
    
    private Long id;
    private String titulo;
    private String autores;
    private String idioma;
    private Integer downloads;
    
    // Construtores
    public LivroSimplificadoDto() {}
    
    public LivroSimplificadoDto(Long id, String titulo, String autores, String idioma, Integer downloads) {
        this.id = id;
        this.titulo = titulo;
        this.autores = autores;
        this.idioma = idioma;
        this.downloads = downloads;
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
    
    public String getAutores() {
        return autores;
    }
    
    public void setAutores(String autores) {
        this.autores = autores;
    }
    
    public String getIdioma() {
        return idioma;
    }
    
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    
    public Integer getDownloads() {
        return downloads;
    }
    
    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }
    
    // Métodos específicos
    public String getResumoUmaLinha() {
        return String.format("%s - %s (%s) - %d downloads", 
                titulo, autores, idioma, downloads != null ? downloads : 0);
    }
    
    @Override
    public String toString() {
        return "LivroSimplificadoDto{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autores='" + autores + '\'' +
                ", idioma='" + idioma + '\'' +
                ", downloads=" + downloads +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LivroSimplificadoDto that = (LivroSimplificadoDto) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
