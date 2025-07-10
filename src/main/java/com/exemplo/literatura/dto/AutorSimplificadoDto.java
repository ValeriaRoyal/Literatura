package com.exemplo.literatura.dto;

import java.util.Objects;

/**
 * DTO simplificado para representar informações básicas de um autor
 */
public class AutorSimplificadoDto {
    
    private String nome;
    private String periodo;
    
    // Construtores
    public AutorSimplificadoDto() {}
    
    public AutorSimplificadoDto(String nome, String periodo) {
        this.nome = nome;
        this.periodo = periodo;
    }
    
    // Getters e Setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getPeriodo() {
        return periodo;
    }
    
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
    // Métodos específicos
    public String getResumoUmaLinha() {
        return nome + " (" + periodo + ")";
    }
    
    @Override
    public String toString() {
        return "AutorSimplificadoDto{" +
                "nome='" + nome + '\'' +
                ", periodo='" + periodo + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutorSimplificadoDto that = (AutorSimplificadoDto) o;
        return Objects.equals(nome, that.nome);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
