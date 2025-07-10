package com.exemplo.literatura.controller;

import com.exemplo.literatura.service.JsonAnalysisService;
import com.exemplo.literatura.service.GutendxHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller REST para demonstrar análise avançada de JSON
 * usando Jackson 2.16 na Fase 4 do desafio
 */
@RestController
@RequestMapping("/api/json-analysis")
public class JsonAnalysisController {
    
    @Autowired
    private JsonAnalysisService jsonAnalysisService;
    
    @Autowired
    private GutendxHttpService gutendxHttpService;
    
    /**
     * Analisa a estrutura de uma resposta da API Gutendx
     */
    @GetMapping("/estrutura")
    public ResponseEntity<Map<String, Object>> analisarEstrutura(
            @RequestParam(defaultValue = "shakespeare") String busca) {
        
        try {
            // Obter JSON da API
            String jsonResponse = gutendxHttpService.obterJsonResposta(busca);
            
            // Analisar estrutura
            Map<String, Object> analise = jsonAnalysisService.analisarEstrutura(jsonResponse);
            
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("termoBusca", busca);
            resultado.put("analiseEstrutura", analise);
            
            return ResponseEntity.ok(resultado);
            
        } catch (Exception e) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }
    
    /**
     * Extrai todos os títulos de livros de uma busca
     */
    @GetMapping("/titulos")
    public ResponseEntity<Map<String, Object>> extrairTitulos(
            @RequestParam(defaultValue = "machado") String busca) {
        
        try {
            String jsonResponse = gutendxHttpService.obterJsonResposta(busca);
            List<String> titulos = jsonAnalysisService.extrairValoresPropriedade(jsonResponse, "title");
            
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("termoBusca", busca);
            resultado.put("totalTitulos", titulos.size());
            resultado.put("titulos", titulos);
            
            return ResponseEntity.ok(resultado);
            
        } catch (Exception e) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }
    
    /**
     * Conta tipos de dados na resposta JSON
     */
    @GetMapping("/tipos-dados")
    public ResponseEntity<Map<String, Object>> contarTiposDados(
            @RequestParam(defaultValue = "dom casmurro") String busca) {
        
        try {
            String jsonResponse = gutendxHttpService.obterJsonResposta(busca);
            Map<String, Integer> tiposDados = jsonAnalysisService.contarTiposDados(jsonResponse);
            
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("termoBusca", busca);
            resultado.put("tiposDados", tiposDados);
            
            return ResponseEntity.ok(resultado);
            
        } catch (Exception e) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }
    
    /**
     * Encontra propriedades vazias ou nulas
     */
    @GetMapping("/propriedades-vazias")
    public ResponseEntity<Map<String, Object>> encontrarPropriedadesVazias(
            @RequestParam(defaultValue = "literatura") String busca) {
        
        try {
            String jsonResponse = gutendxHttpService.obterJsonResposta(busca);
            List<String> propriedadesVazias = jsonAnalysisService.encontrarPropriedadesVazias(jsonResponse);
            
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("termoBusca", busca);
            resultado.put("totalPropriedadesVazias", propriedadesVazias.size());
            resultado.put("propriedadesVazias", propriedadesVazias);
            
            return ResponseEntity.ok(resultado);
            
        } catch (Exception e) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }
    
    /**
     * Analisa arrays na resposta JSON
     */
    @GetMapping("/analise-arrays")
    public ResponseEntity<Map<String, Object>> analisarArrays(
            @RequestParam(defaultValue = "portuguese") String busca) {
        
        try {
            String jsonResponse = gutendxHttpService.obterJsonResposta(busca);
            Map<String, Object> analiseArrays = jsonAnalysisService.analisarArrays(jsonResponse);
            
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("termoBusca", busca);
            resultado.put("analiseArrays", analiseArrays);
            
            return ResponseEntity.ok(resultado);
            
        } catch (Exception e) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }
    
    /**
     * Valida e analisa JSON customizado
     */
    @PostMapping("/validar")
    public ResponseEntity<Map<String, Object>> validarJson(@RequestBody String jsonString) {
        
        Map<String, Object> validacao = jsonAnalysisService.validarJson(jsonString);
        
        if ((Boolean) validacao.get("valido")) {
            // Se válido, fazer análise completa
            Map<String, Object> analiseCompleta = new HashMap<>();
            analiseCompleta.put("validacao", validacao);
            analiseCompleta.put("estrutura", jsonAnalysisService.analisarEstrutura(jsonString));
            analiseCompleta.put("tiposDados", jsonAnalysisService.contarTiposDados(jsonString));
            
            return ResponseEntity.ok(analiseCompleta);
        } else {
            return ResponseEntity.badRequest().body(validacao);
        }
    }
    
    /**
     * Demonstração completa de análise JSON
     */
    @GetMapping("/demo-completa")
    public ResponseEntity<Map<String, Object>> demonstracaoCompleta(
            @RequestParam(defaultValue = "pride and prejudice") String busca) {
        
        try {
            String jsonResponse = gutendxHttpService.obterJsonResposta(busca);
            
            Map<String, Object> demonstracao = new HashMap<>();
            demonstracao.put("termoBusca", busca);
            demonstracao.put("tamanhoJson", jsonResponse.length());
            
            // Análises diversas
            demonstracao.put("estrutura", jsonAnalysisService.analisarEstrutura(jsonResponse));
            demonstracao.put("tiposDados", jsonAnalysisService.contarTiposDados(jsonResponse));
            demonstracao.put("analiseArrays", jsonAnalysisService.analisarArrays(jsonResponse));
            
            // Extrações específicas
            demonstracao.put("titulos", jsonAnalysisService.extrairValoresPropriedade(jsonResponse, "title"));
            demonstracao.put("autores", jsonAnalysisService.extrairValoresPropriedade(jsonResponse, "name"));
            demonstracao.put("idiomas", jsonAnalysisService.extrairValoresPropriedade(jsonResponse, "languages"));
            
            // Propriedades vazias
            List<String> propriedadesVazias = jsonAnalysisService.encontrarPropriedadesVazias(jsonResponse);
            demonstracao.put("propriedadesVazias", propriedadesVazias.size());
            
            return ResponseEntity.ok(demonstracao);
            
        } catch (Exception e) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("erro", e.getMessage());
            erro.put("tipoErro", e.getClass().getSimpleName());
            return ResponseEntity.badRequest().body(erro);
        }
    }
}
