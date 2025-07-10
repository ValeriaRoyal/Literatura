package com.exemplo.literatura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.Year;
import java.util.Objects;

/**
 * DTO completo para representar um autor da API Gutendx
 * com todas as anotações Jackson e métodos de manipulação
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutorCompletoDto {
    
    @JsonProperty("name")
    @JsonAlias({"author_name", "full_name", "writer_name"})
    private String nome;
    
    @JsonProperty("birth_year")
    @JsonAlias({"born", "birth", "year_born"})
    private Integer anoNascimento;
    
    @JsonProperty("death_year")
    @JsonAlias({"died", "death", "year_died"})
    private Integer anoMorte;
    
    // Construtores
    public AutorCompletoDto() {}
    
    public AutorCompletoDto(String nome) {
        this.nome = nome;
    }
    
    public AutorCompletoDto(String nome, Integer anoNascimento, Integer anoMorte) {
        this.nome = nome;
        this.anoNascimento = anoNascimento;
        this.anoMorte = anoMorte;
    }
    
    // Getters e Setters
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
    
    // Métodos específicos para manipulação de dados
    
    /**
     * Obtém o nome formatado (apenas primeiro e último nome)
     */
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
    
    /**
     * Obtém apenas o primeiro nome
     */
    public String getPrimeiroNome() {
        String nomeFormatado = getNomeFormatado();
        String[] partes = nomeFormatado.split("\\s+");
        return partes.length > 0 ? partes[0] : nomeFormatado;
    }
    
    /**
     * Obtém apenas o sobrenome
     */
    public String getSobrenome() {
        String nomeFormatado = getNomeFormatado();
        String[] partes = nomeFormatado.split("\\s+");
        return partes.length > 1 ? partes[partes.length - 1] : "";
    }
    
    /**
     * Calcula a idade que o autor viveu
     */
    public Integer getIdadeVivida() {
        if (anoNascimento == null) return null;
        
        int anoFinal = (anoMorte != null) ? anoMorte : Year.now().getValue();
        return anoFinal - anoNascimento;
    }
    
    /**
     * Verifica se o autor ainda está vivo
     */
    public boolean estaVivo() {
        return anoMorte == null;
    }
    
    /**
     * Obtém o período de vida formatado
     */
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
    
    /**
     * Obtém o século em que o autor nasceu
     */
    public String getSeculoNascimento() {
        if (anoNascimento == null) return "Desconhecido";
        
        int seculo = ((anoNascimento - 1) / 100) + 1;
        return "Século " + seculo;
    }
    
    /**
     * Verifica se o autor viveu em um período específico
     */
    public boolean viveuNoPeriodo(int anoInicio, int anoFim) {
        if (anoNascimento == null) return false;
        
        int anoFinalVida = (anoMorte != null) ? anoMorte : Year.now().getValue();
        
        // Verifica se há sobreposição entre os períodos
        return anoNascimento <= anoFim && anoFinalVida >= anoInicio;
    }
    
    /**
     * Verifica se é um autor clássico (morreu há mais de 100 anos)
     */
    public boolean eAutorClassico() {
        if (anoMorte == null) return false;
        return (Year.now().getValue() - anoMorte) > 100;
    }
    
    /**
     * Obtém uma descrição completa do autor
     */
    public String getDescricaoCompleta() {
        StringBuilder descricao = new StringBuilder();
        descricao.append(getNomeFormatado());
        
        String periodo = getPeriodoVida();
        if (!"Período desconhecido".equals(periodo)) {
            descricao.append(" (").append(periodo).append(")");
        }
        
        if (anoNascimento != null) {
            descricao.append(" - ").append(getSeculoNascimento());
        }
        
        if (eAutorClassico()) {
            descricao.append(" - Autor Clássico");
        } else if (estaVivo()) {
            descricao.append(" - Autor Contemporâneo");
        }
        
        Integer idade = getIdadeVivida();
        if (idade != null) {
            descricao.append(" - Viveu ").append(idade).append(" anos");
        }
        
        return descricao.toString();
    }
    
    /**
     * Converte para uma representação simplificada
     */
    public AutorSimplificadoDto paraSimplificado() {
        return new AutorSimplificadoDto(
            this.getNomeFormatado(),
            this.getPeriodoVida()
        );
    }
    
    /**
     * Compara com outro autor por nome
     */
    public boolean temMesmoNome(AutorCompletoDto outroAutor) {
        if (outroAutor == null || outroAutor.getNome() == null) return false;
        return this.getNomeFormatado().equalsIgnoreCase(outroAutor.getNomeFormatado());
    }
    
    @Override
    public String toString() {
        return "AutorCompletoDto{" +
                "nome='" + nome + '\'' +
                ", anoNascimento=" + anoNascimento +
                ", anoMorte=" + anoMorte +
                ", periodo='" + getPeriodoVida() + '\'' +
                ", idade=" + getIdadeVivida() +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutorCompletoDto that = (AutorCompletoDto) o;
        return Objects.equals(getNomeFormatado().toLowerCase(), 
                             that.getNomeFormatado().toLowerCase());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getNomeFormatado().toLowerCase());
    }
}
