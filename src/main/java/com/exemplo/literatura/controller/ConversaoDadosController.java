package com.exemplo.literatura.controller;

import com.exemplo.literatura.dto.*;
import com.exemplo.literatura.service.ConversaoDadosService;
import com.exemplo.literatura.service.GutendxHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller REST para demonstrar conversões de dados JSON para classes Java
 * usando as anotações @JsonIgnoreProperties e @JsonAlias
 */
@RestController
@RequestMapping("/api/conversao")
public class ConversaoDadosController {
    
    @Autowired
    private ConversaoDadosService conversaoService;
    
    @Autowired
    private GutendxHttpService gutendxHttpService;
    
    /**
     * Demonstra conversão completa de dados da API
     */
    @GetMapping("/completa")
    public ResponseEntity<Map<String, Object>> conversaoCompleta(
            @RequestParam(defaultValue = "shakespeare") String busca) {
        
        try {
            // Obter JSON da API
            String jsonResposta = gutendxHttpService.obterJsonResposta(busca);
            
            // Converter para objetos Java
            GutendxResponseCompleta resposta = conversaoService.converterResposta(jsonResposta);
            
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("termoBusca", busca);
            resultado.put("jsonOriginalTamanho", jsonResposta.length());
            resultado.put("conversaoSucesso", true);
            
            // Informações da resposta convertida
            resultado.put("totalLivros", resposta.getTotalLivros());
            resultado.put("livrosNestaPagina", resposta.getNumeroLivrosPagina());
            resultado.put("temProximaPagina", resposta.temProximaPagina());
            resultado.put("idiomasUnicos", resposta.getTodosIdiomas());
            resultado.put("autoresUnicos", resposta.getTodosAutores().size());
            resultado.put("estatisticas", resposta.getEstatisticas());
            resultado.put("resumo", resposta.getResumo());
            
            // Livros convertidos
            resultado.put("livros", resposta.getLivros());
            
            return ResponseEntity.ok(resultado);
            
        } catch (Exception e) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("erro", e.getMessage());
            erro.put("conversaoSucesso", false);
            return ResponseEntity.badRequest().body(erro);
        }
    }
    
    /**
     * Demonstra conversão com dados simplificados
     */
    @GetMapping("/simplificada")
    public ResponseEntity<Map<String, Object>> conversaoSimplificada(
            @RequestParam(defaultValue = "machado") String busca) {
        
        try {
            String jsonResposta = gutendxHttpService.obterJsonResposta(busca);
            GutendxResponseCompleta resposta = conversaoService.converterResposta(jsonResposta);
            
            // Converter para versões simplificadas
            List<LivroSimplificadoDto> livrosSimples = conversaoService.converterParaSimplificados(resposta.getLivros());
            List<AutorSimplificadoDto> autoresSimples = conversaoService.converterAutoresParaSimplificados(resposta.getTodosAutores());
            
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("termoBusca", busca);
            resultado.put("livrosCompletos", resposta.getLivros().size());
            resultado.put("livrosSimplificados", livrosSimples.size());
            resultado.put("autoresSimplificados", autoresSimples.size());
            
            resultado.put("livrosSimples", livrosSimples);
            resultado.put("autoresSimples", autoresSimples);
            
            return ResponseEntity.ok(resultado);
            
        } catch (Exception e) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }
    
    /**
     * Demonstra métodos específicos dos DTOs
     */
    @GetMapping("/metodos-especificos")
    public ResponseEntity<Map<String, Object>> metodosEspecificos(
            @RequestParam(defaultValue = "dom casmurro") String busca) {
        
        try {
            String jsonResposta = gutendxHttpService.obterJsonResposta(busca);
            GutendxResponseCompleta resposta = conversaoService.converterResposta(jsonResposta);
            
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("termoBusca", busca);
            
            if (!resposta.estaVazia()) {
                LivroCompletoDto primeiroLivro = resposta.getLivros().get(0);
                
                // Métodos específicos do livro
                Map<String, Object> metodosLivro = new HashMap<>();
                metodosLivro.put("primeiroAutor", primeiroLivro.getPrimeiroAutor());
                metodosLivro.put("nomesAutores", primeiroLivro.getNomesAutores());
                metodosLivro.put("idiomaPrincipal", primeiroLivro.getIdiomaPrincipal());
                metodosLivro.put("isDominioPublico", primeiroLivro.isDominioPublico());
                metodosLivro.put("temFormatoPDF", primeiroLivro.temFormato("pdf"));
                metodosLivro.put("temFormatoEPUB", primeiroLivro.temFormato("epub"));
                metodosLivro.put("urlPDF", primeiroLivro.getUrlFormato("pdf"));
                metodosLivro.put("resumo", primeiroLivro.getResumo());
                metodosLivro.put("estatisticas", primeiroLivro.getEstatisticas());
                
                resultado.put("metodosLivro", metodosLivro);
                
                // Métodos específicos do autor (se existir)
                if (primeiroLivro.getPrimeiroAutor() != null) {
                    AutorCompletoDto autor = primeiroLivro.getPrimeiroAutor();
                    
                    Map<String, Object> metodosAutor = new HashMap<>();
                    metodosAutor.put("nomeFormatado", autor.getNomeFormatado());
                    metodosAutor.put("primeiroNome", autor.getPrimeiroNome());
                    metodosAutor.put("sobrenome", autor.getSobrenome());
                    metodosAutor.put("periodoVida", autor.getPeriodoVida());
                    metodosAutor.put("idadeVivida", autor.getIdadeVivida());
                    metodosAutor.put("seculoNascimento", autor.getSeculoNascimento());
                    metodosAutor.put("estaVivo", autor.estaVivo());
                    metodosAutor.put("eAutorClassico", autor.eAutorClassico());
                    metodosAutor.put("viveuSeculoXIX", autor.viveuNoPeriodo(1801, 1900));
                    metodosAutor.put("viveuSeculoXX", autor.viveuNoPeriodo(1901, 2000));
                    metodosAutor.put("descricaoCompleta", autor.getDescricaoCompleta());
                    
                    resultado.put("metodosAutor", metodosAutor);
                }
            }
            
            return ResponseEntity.ok(resultado);
            
        } catch (Exception e) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }
    
    /**
     * Demonstra filtros e análises da coleção
     */
    @GetMapping("/analises")
    public ResponseEntity<Map<String, Object>> analisesColecao(
            @RequestParam(defaultValue = "literature") String busca,
            @RequestParam(defaultValue = "en") String idioma) {
        
        try {
            String jsonResposta = gutendxHttpService.obterJsonResposta(busca);
            GutendxResponseCompleta resposta = conversaoService.converterResposta(jsonResposta);
            
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("termoBusca", busca);
            resultado.put("filtroIdioma", idioma);
            
            // Análises da coleção
            List<LivroCompletoDto> livrosPorIdioma = resposta.getLivrosPorIdioma(idioma);
            List<LivroCompletoDto> livrosDominioPublico = resposta.getLivrosDominioPublico();
            List<LivroCompletoDto> maisPopulares = resposta.getLivrosMaisPopulares(5);
            
            resultado.put("totalLivros", resposta.getNumeroLivrosPagina());
            resultado.put("livrosPorIdioma", livrosPorIdioma.size());
            resultado.put("livrosDominioPublico", livrosDominioPublico.size());
            resultado.put("top5Populares", maisPopulares.size());
            
            // Detalhes dos mais populares
            List<Map<String, Object>> detalhesPopulares = maisPopulares.stream()
                    .map(livro -> {
                        Map<String, Object> detalhe = new HashMap<>();
                        detalhe.put("titulo", livro.getTitulo());
                        detalhe.put("autores", livro.getNomesAutores());
                        detalhe.put("downloads", livro.getNumeroDownloads());
                        detalhe.put("idioma", livro.getIdiomaPrincipal());
                        return detalhe;
                    })
                    .toList();
            
            resultado.put("detalhesPopulares", detalhesPopulares);
            resultado.put("estatisticasGerais", resposta.getEstatisticas());
            
            return ResponseEntity.ok(resultado);
            
        } catch (Exception e) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }
    
    /**
     * Testa conversão com JSON customizado
     */
    @PostMapping("/testar-json")
    public ResponseEntity<Map<String, Object>> testarConversaoJson(@RequestBody String jsonCustomizado) {
        
        Map<String, Object> resultado = new HashMap<>();
        
        try {
            // Validar JSON
            boolean jsonValido = conversaoService.isJsonValido(jsonCustomizado);
            resultado.put("jsonValido", jsonValido);
            
            if (!jsonValido) {
                resultado.put("erro", "JSON inválido");
                return ResponseEntity.badRequest().body(resultado);
            }
            
            // Tentar converter como resposta completa
            try {
                GutendxResponseCompleta resposta = conversaoService.converterResposta(jsonCustomizado);
                resultado.put("tipoConversao", "GutendxResponseCompleta");
                resultado.put("conversaoSucesso", true);
                resultado.put("totalLivros", resposta.getTotalLivros());
                resultado.put("livrosEncontrados", resposta.getNumeroLivrosPagina());
                resultado.put("resumo", resposta.getResumo());
                
            } catch (Exception e1) {
                // Tentar converter como livro individual
                try {
                    LivroCompletoDto livro = conversaoService.converterLivro(jsonCustomizado);
                    resultado.put("tipoConversao", "LivroCompletoDto");
                    resultado.put("conversaoSucesso", true);
                    resultado.put("titulo", livro.getTitulo());
                    resultado.put("autores", livro.getNomesAutores());
                    resultado.put("resumo", livro.getResumo());
                    
                } catch (Exception e2) {
                    // Tentar converter como autor individual
                    try {
                        AutorCompletoDto autor = conversaoService.converterAutor(jsonCustomizado);
                        resultado.put("tipoConversao", "AutorCompletoDto");
                        resultado.put("conversaoSucesso", true);
                        resultado.put("nome", autor.getNomeFormatado());
                        resultado.put("periodo", autor.getPeriodoVida());
                        resultado.put("descricao", autor.getDescricaoCompleta());
                        
                    } catch (Exception e3) {
                        resultado.put("conversaoSucesso", false);
                        resultado.put("erro", "Não foi possível converter para nenhum tipo conhecido");
                    }
                }
            }
            
            return ResponseEntity.ok(resultado);
            
        } catch (Exception e) {
            resultado.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(resultado);
        }
    }
    
    /**
     * Obtém informações sobre o serviço de conversão
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> informacoesServico() {
        
        Map<String, Object> info = new HashMap<>();
        info.put("servicoConversao", "ConversaoDadosService");
        info.put("configuracoes", conversaoService.getInformacoesConversao());
        info.put("anotacoesSuportadas", List.of("@JsonIgnoreProperties", "@JsonAlias", "@JsonProperty"));
        info.put("tiposSuportados", List.of("GutendxResponseCompleta", "LivroCompletoDto", "AutorCompletoDto"));
        info.put("versaoJackson", "2.16.1");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("GET /api/conversao/completa", "Conversão completa de dados da API");
        endpoints.put("GET /api/conversao/simplificada", "Conversão para tipos simplificados");
        endpoints.put("GET /api/conversao/metodos-especificos", "Demonstra métodos específicos dos DTOs");
        endpoints.put("GET /api/conversao/analises", "Análises e filtros da coleção");
        endpoints.put("POST /api/conversao/testar-json", "Testa conversão com JSON customizado");
        endpoints.put("GET /api/conversao/info", "Informações sobre o serviço");
        
        info.put("endpoints", endpoints);
        
        return ResponseEntity.ok(info);
    }
}
