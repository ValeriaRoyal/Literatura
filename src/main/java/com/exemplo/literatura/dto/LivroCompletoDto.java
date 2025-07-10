package com.exemplo.literatura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * DTO completo para representar um livro da API Gutendx
 * com todas as anotações Jackson e métodos de manipulação
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LivroCompletoDto {
    
    @JsonProperty("id")
    @JsonAlias({"book_id", "gutenberg_id"})
    private Long id;
    
    @JsonProperty("title")
    @JsonAlias({"book_title", "name"})
    private String titulo;
    
    @JsonProperty("authors")
    @JsonAlias({"book_authors", "writers"})
    private List<AutorCompletoDto> autores;
    
    @JsonProperty("translators")
    private List<AutorCompletoDto> tradutores;
    
    @JsonProperty("subjects")
    @JsonAlias({"book_subjects", "topics"})
    private List<String> assuntos;
    
    @JsonProperty("bookshelves")
    @JsonAlias({"shelves", "categories"})
    private List<String> estantes;
    
    @JsonProperty("languages")
    @JsonAlias({"book_languages", "langs"})
    private List<String> idiomas;
    
    @JsonProperty("copyright")
    @JsonAlias({"has_copyright", "copyrighted"})
    private Boolean temCopyright;
    
    @JsonProperty("media_type")
    @JsonAlias({"type", "content_type"})
    private String tipoMidia;
    
    @JsonProperty("formats")
    @JsonAlias({"download_links", "files"})
    private Map<String, String> formatos;
    
    @JsonProperty("download_count")
    @JsonAlias({"downloads", "download_number"})
    private Integer numeroDownloads;
    
    // Construtores
    public LivroCompletoDto() {}
    
    public LivroCompletoDto(Long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
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
    
    public List<AutorCompletoDto> getAutores() {
        return autores;
    }
    
    public void setAutores(List<AutorCompletoDto> autores) {
        this.autores = autores;
    }
    
    public List<AutorCompletoDto> getTradutores() {
        return tradutores;
    }
    
    public void setTradutores(List<AutorCompletoDto> tradutores) {
        this.tradutores = tradutores;
    }
    
    public List<String> getAssuntos() {
        return assuntos;
    }
    
    public void setAssuntos(List<String> assuntos) {
        this.assuntos = assuntos;
    }
    
    public List<String> getEstantes() {
        return estantes;
    }
    
    public void setEstantes(List<String> estantes) {
        this.estantes = estantes;
    }
    
    public List<String> getIdiomas() {
        return idiomas;
    }
    
    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }
    
    public Boolean getTemCopyright() {
        return temCopyright;
    }
    
    public void setTemCopyright(Boolean temCopyright) {
        this.temCopyright = temCopyright;
    }
    
    public String getTipoMidia() {
        return tipoMidia;
    }
    
    public void setTipoMidia(String tipoMidia) {
        this.tipoMidia = tipoMidia;
    }
    
    public Map<String, String> getFormatos() {
        return formatos;
    }
    
    public void setFormatos(Map<String, String> formatos) {
        this.formatos = formatos;
    }
    
    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }
    
    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }
    
    // Métodos específicos para manipulação de dados
    
    /**
     * Obtém o primeiro autor do livro
     */
    public AutorCompletoDto getPrimeiroAutor() {
        return (autores != null && !autores.isEmpty()) ? autores.get(0) : null;
    }
    
    /**
     * Obtém os nomes de todos os autores concatenados
     */
    public String getNomesAutores() {
        if (autores == null || autores.isEmpty()) {
            return "Autor desconhecido";
        }
        return autores.stream()
                .map(AutorCompletoDto::getNome)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));
    }
    
    /**
     * Verifica se o livro tem um formato específico
     */
    public boolean temFormato(String tipoFormato) {
        if (formatos == null) return false;
        return formatos.keySet().stream()
                .anyMatch(formato -> formato.toLowerCase().contains(tipoFormato.toLowerCase()));
    }
    
    /**
     * Obtém URL de um formato específico
     */
    public String getUrlFormato(String tipoFormato) {
        if (formatos == null) return null;
        return formatos.entrySet().stream()
                .filter(entry -> entry.getKey().toLowerCase().contains(tipoFormato.toLowerCase()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Verifica se o livro está em um idioma específico
     */
    public boolean estaEmIdioma(String idioma) {
        return idiomas != null && idiomas.contains(idioma.toLowerCase());
    }
    
    /**
     * Obtém o idioma principal (primeiro da lista)
     */
    public String getIdiomaPrincipal() {
        return (idiomas != null && !idiomas.isEmpty()) ? idiomas.get(0) : "Desconhecido";
    }
    
    /**
     * Verifica se o livro é de domínio público
     */
    public boolean isDominioPublico() {
        return temCopyright == null || !temCopyright;
    }
    
    /**
     * Obtém uma descrição resumida do livro
     */
    public String getResumo() {
        StringBuilder resumo = new StringBuilder();
        resumo.append("ID: ").append(id != null ? id : "N/A");
        resumo.append(" | Título: ").append(titulo != null ? titulo : "Sem título");
        resumo.append(" | Autor(es): ").append(getNomesAutores());
        resumo.append(" | Idioma: ").append(getIdiomaPrincipal());
        resumo.append(" | Downloads: ").append(numeroDownloads != null ? numeroDownloads : 0);
        return resumo.toString();
    }
    
    /**
     * Obtém estatísticas do livro
     */
    public String getEstatisticas() {
        StringBuilder stats = new StringBuilder();
        stats.append("Formatos disponíveis: ").append(formatos != null ? formatos.size() : 0);
        stats.append(" | Assuntos: ").append(assuntos != null ? assuntos.size() : 0);
        stats.append(" | Idiomas: ").append(idiomas != null ? idiomas.size() : 0);
        stats.append(" | Autores: ").append(autores != null ? autores.size() : 0);
        if (tradutores != null && !tradutores.isEmpty()) {
            stats.append(" | Tradutores: ").append(tradutores.size());
        }
        return stats.toString();
    }
    
    /**
     * Converte para uma representação simplificada
     */
    public LivroSimplificadoDto paraSimplificado() {
        return new LivroSimplificadoDto(
            this.id,
            this.titulo,
            this.getNomesAutores(),
            this.getIdiomaPrincipal(),
            this.numeroDownloads
        );
    }
    
    @Override
    public String toString() {
        return "LivroCompletoDto{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autores=" + (autores != null ? autores.size() + " autor(es)" : "0") +
                ", idiomas=" + idiomas +
                ", numeroDownloads=" + numeroDownloads +
                ", temCopyright=" + temCopyright +
                ", tipoMidia='" + tipoMidia + '\'' +
                ", formatos=" + (formatos != null ? formatos.size() + " formato(s)" : "0") +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LivroCompletoDto that = (LivroCompletoDto) o;
        return Objects.equals(id, that.id) && Objects.equals(titulo, that.titulo);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, titulo);
    }
}
