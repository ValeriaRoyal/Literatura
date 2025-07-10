package com.exemplo.literatura.service;

import com.exemplo.literatura.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço especializado para conversão de dados JSON em objetos Java
 * usando as classes DTO com anotações Jackson
 */
@Service
public class ConversaoDadosService {
    
    private final ObjectMapper objectMapper;
    
    public ConversaoDadosService() {
        this.objectMapper = new ObjectMapper();
        
        // Configurações para conversão robusta
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }
    
    /**
     * Converte JSON da API Gutendx para GutendxResponseCompleta
     */
    public GutendxResponseCompleta converterResposta(String jsonResposta) {
        try {
            return objectMapper.readValue(jsonResposta, GutendxResponseCompleta.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter resposta JSON: " + e.getMessage(), e);
        }
    }
    
    /**
     * Converte JSON de um livro individual para LivroCompletoDto
     */
    public LivroCompletoDto converterLivro(String jsonLivro) {
        try {
            return objectMapper.readValue(jsonLivro, LivroCompletoDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter livro JSON: " + e.getMessage(), e);
        }
    }
    
    /**
     * Converte JSON de um autor individual para AutorCompletoDto
     */
    public AutorCompletoDto converterAutor(String jsonAutor) {
        try {
            return objectMapper.readValue(jsonAutor, AutorCompletoDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter autor JSON: " + e.getMessage(), e);
        }
    }
    
    /**
     * Converte lista de livros completos para simplificados
     */
    public List<LivroSimplificadoDto> converterParaSimplificados(List<LivroCompletoDto> livrosCompletos) {
        return livrosCompletos.stream()
                .map(LivroCompletoDto::paraSimplificado)
                .collect(Collectors.toList());
    }
    
    /**
     * Converte lista de autores completos para simplificados
     */
    public List<AutorSimplificadoDto> converterAutoresParaSimplificados(List<AutorCompletoDto> autoresCompletos) {
        return autoresCompletos.stream()
                .map(AutorCompletoDto::paraSimplificado)
                .collect(Collectors.toList());
    }
    
    /**
     * Converte objeto Java para JSON
     */
    public String converterParaJson(Object objeto) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objeto);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter objeto para JSON: " + e.getMessage(), e);
        }
    }
    
    /**
     * Valida se um JSON é válido
     */
    public boolean isJsonValido(String json) {
        try {
            objectMapper.readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Obtém informações sobre a conversão
     */
    public String getInformacoesConversao() {
        return "ConversaoDadosService configurado com:\n" +
               "- FAIL_ON_UNKNOWN_PROPERTIES: false\n" +
               "- ACCEPT_EMPTY_STRING_AS_NULL_OBJECT: true\n" +
               "- ACCEPT_SINGLE_VALUE_AS_ARRAY: true\n" +
               "- Suporte a @JsonIgnoreProperties e @JsonAlias\n" +
               "- Conversão bidirecional JSON ↔ Java";
    }
}
