package com.exemplo.literatura.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Serviço especializado para análise avançada de dados JSON
 * usando Jackson 2.16 com técnicas de extração e manipulação
 */
@Service
public class JsonAnalysisService {
    
    private final ObjectMapper objectMapper;
    
    public JsonAnalysisService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    /**
     * Analisa a estrutura completa de um JSON
     */
    public Map<String, Object> analisarEstrutura(String jsonString) {
        Map<String, Object> analise = new HashMap<>();
        
        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);
            
            analise.put("tipo", getNodeType(rootNode));
            analise.put("tamanho", jsonString.length());
            
            if (rootNode.isObject()) {
                analise.put("propriedades", contarPropriedades(rootNode));
                analise.put("estrutura", mapearEstrutura(rootNode));
            } else if (rootNode.isArray()) {
                analise.put("elementos", rootNode.size());
                if (rootNode.size() > 0) {
                    analise.put("tipoElementos", getNodeType(rootNode.get(0)));
                }
            }
            
        } catch (Exception e) {
            analise.put("erro", e.getMessage());
        }
        
        return analise;
    }
    
    /**
     * Extrai todos os valores de uma propriedade específica
     */
    public List<String> extrairValoresPropriedade(String jsonString, String propriedade) {
        List<String> valores = new ArrayList<>();
        
        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);
            extrairValoresRecursivo(rootNode, propriedade, valores);
        } catch (Exception e) {
            System.err.println("Erro ao extrair propriedade: " + e.getMessage());
        }
        
        return valores;
    }
    
    /**
     * Conta ocorrências de diferentes tipos de dados
     */
    public Map<String, Integer> contarTiposDados(String jsonString) {
        Map<String, Integer> contadores = new HashMap<>();
        
        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);
            contarTiposRecursivo(rootNode, contadores);
        } catch (Exception e) {
            contadores.put("erro", 1);
        }
        
        return contadores;
    }
    
    /**
     * Encontra propriedades com valores nulos ou vazios
     */
    public List<String> encontrarPropriedadesVazias(String jsonString) {
        List<String> propriedadesVazias = new ArrayList<>();
        
        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);
            encontrarVaziosRecursivo(rootNode, "", propriedadesVazias);
        } catch (Exception e) {
            propriedadesVazias.add("Erro: " + e.getMessage());
        }
        
        return propriedadesVazias;
    }
    
    /**
     * Extrai estatísticas de arrays no JSON
     */
    public Map<String, Object> analisarArrays(String jsonString) {
        Map<String, Object> estatisticas = new HashMap<>();
        List<Map<String, Object>> arrays = new ArrayList<>();
        
        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);
            analisarArraysRecursivo(rootNode, "", arrays);
            
            estatisticas.put("totalArrays", arrays.size());
            estatisticas.put("detalhes", arrays);
            
            if (!arrays.isEmpty()) {
                OptionalDouble mediaTamanho = arrays.stream()
                    .mapToInt(arr -> (Integer) arr.get("tamanho"))
                    .average();
                estatisticas.put("tamanhoMedio", mediaTamanho.orElse(0.0));
            }
            
        } catch (Exception e) {
            estatisticas.put("erro", e.getMessage());
        }
        
        return estatisticas;
    }
    
    /**
     * Converte JSON para Map genérico para análise flexível
     */
    public Map<String, Object> jsonParaMap(String jsonString) {
        try {
            TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
            return objectMapper.readValue(jsonString, typeRef);
        } catch (Exception e) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("erro", e.getMessage());
            return erro;
        }
    }
    
    /**
     * Valida se o JSON está bem formado
     */
    public Map<String, Object> validarJson(String jsonString) {
        Map<String, Object> resultado = new HashMap<>();
        
        try {
            JsonNode node = objectMapper.readTree(jsonString);
            resultado.put("valido", true);
            resultado.put("tipo", getNodeType(node));
            resultado.put("tamanho", jsonString.length());
            
            if (node.isObject()) {
                resultado.put("propriedades", node.size());
            } else if (node.isArray()) {
                resultado.put("elementos", node.size());
            }
            
        } catch (Exception e) {
            resultado.put("valido", false);
            resultado.put("erro", e.getMessage());
            resultado.put("tipoErro", e.getClass().getSimpleName());
        }
        
        return resultado;
    }
    
    // Métodos auxiliares privados
    
    private String getNodeType(JsonNode node) {
        if (node.isArray()) return "Array";
        if (node.isObject()) return "Object";
        if (node.isTextual()) return "String";
        if (node.isNumber()) return "Number";
        if (node.isBoolean()) return "Boolean";
        if (node.isNull()) return "Null";
        return "Unknown";
    }
    
    private int contarPropriedades(JsonNode node) {
        if (node.isObject()) {
            return node.size();
        }
        return 0;
    }
    
    private Map<String, String> mapearEstrutura(JsonNode node) {
        Map<String, String> estrutura = new HashMap<>();
        
        if (node.isObject()) {
            node.fieldNames().forEachRemaining(fieldName -> {
                JsonNode fieldValue = node.get(fieldName);
                estrutura.put(fieldName, getNodeType(fieldValue));
            });
        }
        
        return estrutura;
    }
    
    private void extrairValoresRecursivo(JsonNode node, String propriedade, List<String> valores) {
        if (node.isObject()) {
            if (node.has(propriedade)) {
                JsonNode valorNode = node.get(propriedade);
                if (!valorNode.isNull()) {
                    valores.add(valorNode.asText());
                }
            }
            
            node.fieldNames().forEachRemaining(fieldName -> {
                extrairValoresRecursivo(node.get(fieldName), propriedade, valores);
            });
            
        } else if (node.isArray()) {
            for (JsonNode elemento : node) {
                extrairValoresRecursivo(elemento, propriedade, valores);
            }
        }
    }
    
    private void contarTiposRecursivo(JsonNode node, Map<String, Integer> contadores) {
        String tipo = getNodeType(node);
        contadores.merge(tipo, 1, Integer::sum);
        
        if (node.isObject()) {
            node.fieldNames().forEachRemaining(fieldName -> {
                contarTiposRecursivo(node.get(fieldName), contadores);
            });
        } else if (node.isArray()) {
            for (JsonNode elemento : node) {
                contarTiposRecursivo(elemento, contadores);
            }
        }
    }
    
    private void encontrarVaziosRecursivo(JsonNode node, String caminho, List<String> vazios) {
        if (node.isObject()) {
            node.fieldNames().forEachRemaining(fieldName -> {
                JsonNode fieldValue = node.get(fieldName);
                String novoCaminho = caminho.isEmpty() ? fieldName : caminho + "." + fieldName;
                
                if (fieldValue.isNull() || 
                    (fieldValue.isTextual() && fieldValue.asText().isEmpty()) ||
                    (fieldValue.isArray() && fieldValue.size() == 0) ||
                    (fieldValue.isObject() && fieldValue.size() == 0)) {
                    vazios.add(novoCaminho);
                } else {
                    encontrarVaziosRecursivo(fieldValue, novoCaminho, vazios);
                }
            });
        } else if (node.isArray()) {
            for (int i = 0; i < node.size(); i++) {
                String novoCaminho = caminho + "[" + i + "]";
                encontrarVaziosRecursivo(node.get(i), novoCaminho, vazios);
            }
        }
    }
    
    private void analisarArraysRecursivo(JsonNode node, String caminho, List<Map<String, Object>> arrays) {
        if (node.isArray()) {
            Map<String, Object> infoArray = new HashMap<>();
            infoArray.put("caminho", caminho.isEmpty() ? "root" : caminho);
            infoArray.put("tamanho", node.size());
            
            if (node.size() > 0) {
                infoArray.put("tipoElementos", getNodeType(node.get(0)));
            }
            
            arrays.add(infoArray);
            
            // Analisar elementos do array
            for (int i = 0; i < node.size(); i++) {
                String novoCaminho = caminho + "[" + i + "]";
                analisarArraysRecursivo(node.get(i), novoCaminho, arrays);
            }
            
        } else if (node.isObject()) {
            node.fieldNames().forEachRemaining(fieldName -> {
                String novoCaminho = caminho.isEmpty() ? fieldName : caminho + "." + fieldName;
                analisarArraysRecursivo(node.get(fieldName), novoCaminho, arrays);
            });
        }
    }
}
