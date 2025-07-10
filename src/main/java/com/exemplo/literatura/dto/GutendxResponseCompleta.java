package com.exemplo.literatura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * DTO completo para representar a resposta da API Gutendx
 * com todas as anotações Jackson e métodos de manipulação
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GutendxResponseCompleta {
    
    @JsonProperty("count")
    @JsonAlias({"total", "total_count"})
    private Integer totalLivros;
    
    @JsonProperty("next")
    @JsonAlias({"next_page", "next_url"})
    private String proximaPagina;
    
    @JsonProperty("previous")
    @JsonAlias({"prev_page", "prev_url", "previous_page"})
    private String paginaAnterior;
    
    @JsonProperty("results")
    @JsonAlias({"books", "data", "items"})
    private List<LivroCompletoDto> livros;
    
    // Construtores
    public GutendxResponseCompleta() {}
    
    public GutendxResponseCompleta(List<LivroCompletoDto> livros) {
        this.livros = livros;
        this.totalLivros = livros != null ? livros.size() : 0;
    }
    
    // Getters e Setters
    public Integer getTotalLivros() {
        return totalLivros;
    }
    
    public void setTotalLivros(Integer totalLivros) {
        this.totalLivros = totalLivros;
    }
    
    public String getProximaPagina() {
        return proximaPagina;
    }
    
    public void setProximaPagina(String proximaPagina) {
        this.proximaPagina = proximaPagina;
    }
    
    public String getPaginaAnterior() {
        return paginaAnterior;
    }
    
    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }
    
    public List<LivroCompletoDto> getLivros() {
        return livros;
    }
    
    public void setLivros(List<LivroCompletoDto> livros) {
        this.livros = livros;
    }
    
    // Métodos específicos para manipulação de dados
    
    /**
     * Verifica se há mais páginas disponíveis
     */
    public boolean temProximaPagina() {
        return proximaPagina != null && !proximaPagina.trim().isEmpty();
    }
    
    /**
     * Verifica se há página anterior
     */
    public boolean temPaginaAnterior() {
        return paginaAnterior != null && !paginaAnterior.trim().isEmpty();
    }
    
    /**
     * Obtém o número de livros na página atual
     */
    public int getNumeroLivrosPagina() {
        return livros != null ? livros.size() : 0;
    }
    
    /**
     * Verifica se a resposta está vazia
     */
    public boolean estaVazia() {
        return livros == null || livros.isEmpty();
    }
    
    /**
     * Obtém todos os autores únicos dos livros
     */
    public List<AutorCompletoDto> getTodosAutores() {
        if (livros == null) return List.of();
        
        return livros.stream()
                .filter(livro -> livro.getAutores() != null)
                .flatMap(livro -> livro.getAutores().stream())
                .distinct()
                .collect(Collectors.toList());
    }
    
    /**
     * Obtém todos os idiomas únicos dos livros
     */
    public List<String> getTodosIdiomas() {
        if (livros == null) return List.of();
        
        return livros.stream()
                .filter(livro -> livro.getIdiomas() != null)
                .flatMap(livro -> livro.getIdiomas().stream())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
    
    /**
     * Filtra livros por idioma
     */
    public List<LivroCompletoDto> getLivrosPorIdioma(String idioma) {
        if (livros == null) return List.of();
        
        return livros.stream()
                .filter(livro -> livro.estaEmIdioma(idioma))
                .collect(Collectors.toList());
    }
    
    /**
     * Filtra livros de domínio público
     */
    public List<LivroCompletoDto> getLivrosDominioPublico() {
        if (livros == null) return List.of();
        
        return livros.stream()
                .filter(LivroCompletoDto::isDominioPublico)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtém os livros mais populares (por número de downloads)
     */
    public List<LivroCompletoDto> getLivrosMaisPopulares(int limite) {
        if (livros == null) return List.of();
        
        return livros.stream()
                .filter(livro -> livro.getNumeroDownloads() != null)
                .sorted((l1, l2) -> Integer.compare(l2.getNumeroDownloads(), l1.getNumeroDownloads()))
                .limit(limite)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtém estatísticas da resposta
     */
    public String getEstatisticas() {
        if (estaVazia()) {
            return "Nenhum livro encontrado";
        }
        
        StringBuilder stats = new StringBuilder();
        stats.append("Total na base: ").append(totalLivros != null ? totalLivros : "N/A");
        stats.append(" | Nesta página: ").append(getNumeroLivrosPagina());
        stats.append(" | Idiomas únicos: ").append(getTodosIdiomas().size());
        stats.append(" | Autores únicos: ").append(getTodosAutores().size());
        
        long livrosDominioPublico = getLivrosDominioPublico().size();
        stats.append(" | Domínio público: ").append(livrosDominioPublico);
        
        return stats.toString();
    }
    
    /**
     * Converte para lista de livros simplificados
     */
    public List<LivroSimplificadoDto> paraLivrosSimplificados() {
        if (livros == null) return List.of();
        
        return livros.stream()
                .map(LivroCompletoDto::paraSimplificado)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtém resumo da resposta
     */
    public String getResumo() {
        StringBuilder resumo = new StringBuilder();
        resumo.append("Resposta Gutendx: ");
        
        if (estaVazia()) {
            resumo.append("Nenhum resultado encontrado");
        } else {
            resumo.append(getNumeroLivrosPagina()).append(" livro(s) encontrado(s)");
            
            if (totalLivros != null && totalLivros > getNumeroLivrosPagina()) {
                resumo.append(" de ").append(totalLivros).append(" total");
            }
            
            if (temProximaPagina()) {
                resumo.append(" (há mais páginas)");
            }
        }
        
        return resumo.toString();
    }
    
    @Override
    public String toString() {
        return "GutendxResponseCompleta{" +
                "totalLivros=" + totalLivros +
                ", proximaPagina='" + proximaPagina + '\'' +
                ", paginaAnterior='" + paginaAnterior + '\'' +
                ", livros=" + (livros != null ? livros.size() + " livro(s)" : "null") +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GutendxResponseCompleta that = (GutendxResponseCompleta) o;
        return Objects.equals(livros, that.livros);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(livros);
    }
}
