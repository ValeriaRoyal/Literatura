package com.exemplo.literatura.exemplo;

import com.exemplo.literatura.dto.GutendxResponse;
import com.exemplo.literatura.dto.LivroDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.Duration;
import java.util.List;

/**
 * Exemplo prático mostrando como HttpClient, HttpRequest e HttpResponse
 * são utilizados na integração com a API Gutendx no projeto de literatura.
 */
public class ExemploIntegracaoGutendx {
    
    private static final String BASE_URL = "https://gutendx.com/books/";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    
    public ExemploIntegracaoGutendx() {
        // 1. CONFIGURAÇÃO DO HttpClient
        this.httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .version(HttpClient.Version.HTTP_2)
            .build();
        
        this.objectMapper = new ObjectMapper();
    }
    
    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("📚 EXEMPLO: Integração com API Gutendx usando HttpClient");
        System.out.println("======================================================================\n");
        
        ExemploIntegracaoGutendx exemplo = new ExemploIntegracaoGutendx();
        
        // Demonstrar diferentes tipos de busca
        exemplo.demonstrarBuscaPorTitulo("pride");
        exemplo.demonstrarBuscaPorAutor("shakespeare");
        exemplo.demonstrarBuscaPorIdioma("pt");
        exemplo.demonstrarTratamentoErros();
    }
    
    private void demonstrarBuscaPorTitulo(String titulo) {
        System.out.println("🔍 1. BUSCA POR TÍTULO: '" + titulo + "'");
        System.out.println("=====================================");
        
        try {
            // 2. CONSTRUÇÃO DO HttpRequest
            String url = BASE_URL + "?search=" + titulo + "&page_size=3";
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .header("User-Agent", "Literatura-App/1.0")
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();
            
            System.out.println("📡 Enviando requisição para: " + url);
            System.out.println("🔧 Headers configurados:");
            System.out.println("   • Accept: application/json");
            System.out.println("   • User-Agent: Literatura-App/1.0");
            System.out.println("   • Timeout: 30 segundos");
            
            // 3. ENVIO DA REQUISIÇÃO E OBTENÇÃO DO HttpResponse
            HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
            
            // 4. ANÁLISE DO HttpResponse
            System.out.println("\n📊 Resposta recebida:");
            System.out.println("   • Status Code: " + response.statusCode());
            System.out.println("   • Content-Type: " + 
                response.headers().firstValue("content-type").orElse("N/A"));
            System.out.println("   • Content-Length: " + 
                response.headers().firstValue("content-length").orElse("N/A"));
            
            if (response.statusCode() == 200) {
                // 5. CONVERSÃO JSON PARA OBJETOS JAVA
                GutendxResponse gutendxResponse = objectMapper.readValue(
                    response.body(), GutendxResponse.class);
                
                System.out.println("   • Total de livros encontrados: " + gutendxResponse.getCount());
                System.out.println("   • Livros nesta página: " + gutendxResponse.getResults().size());
                
                System.out.println("\n📖 Primeiros livros encontrados:");
                for (LivroDto livro : gutendxResponse.getResults()) {
                    System.out.println("   • " + livro.getTitle() + 
                        " (ID: " + livro.getId() + ")");
                }
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro na busca por título: " + e.getMessage());
        }
        System.out.println();
    }
    
    private void demonstrarBuscaPorAutor(String autor) {
        System.out.println("👤 2. BUSCA POR AUTOR: '" + autor + "'");
        System.out.println("===================================");
        
        try {
            // Exemplo de URL com múltiplos parâmetros
            String url = BASE_URL + "?search=" + autor + "&page_size=2&sort=popular";
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();
            
            System.out.println("📡 URL com múltiplos parâmetros: " + url);
            
            HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
            
            System.out.println("📊 Status: " + response.statusCode());
            
            if (response.statusCode() == 200) {
                GutendxResponse gutendxResponse = objectMapper.readValue(
                    response.body(), GutendxResponse.class);
                
                System.out.println("📚 Livros de " + autor + ":");
                gutendxResponse.getResults().forEach(livro -> {
                    System.out.println("   • " + livro.getTitle());
                    if (!livro.getAuthors().isEmpty()) {
                        System.out.println("     Autor: " + 
                            livro.getAuthors().get(0).getName());
                    }
                });
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro na busca por autor: " + e.getMessage());
        }
        System.out.println();
    }
    
    private void demonstrarBuscaPorIdioma(String idioma) {
        System.out.println("🌍 3. BUSCA POR IDIOMA: '" + idioma + "'");
        System.out.println("====================================");
        
        try {
            String url = BASE_URL + "?languages=" + idioma + "&page_size=2";
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();
            
            System.out.println("📡 Buscando livros em idioma: " + idioma);
            
            HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
            
            System.out.println("📊 Status: " + response.statusCode());
            
            if (response.statusCode() == 200) {
                GutendxResponse gutendxResponse = objectMapper.readValue(
                    response.body(), GutendxResponse.class);
                
                System.out.println("📚 Livros encontrados:");
                gutendxResponse.getResults().forEach(livro -> {
                    System.out.println("   • " + livro.getTitle());
                    System.out.println("     Idiomas: " + livro.getLanguages());
                });
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro na busca por idioma: " + e.getMessage());
        }
        System.out.println();
    }
    
    private void demonstrarTratamentoErros() {
        System.out.println("⚠️  4. TRATAMENTO DE ERROS");
        System.out.println("==========================");
        
        try {
            // URL inválida para demonstrar tratamento de erro
            String url = BASE_URL + "invalid-endpoint";
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();
            
            System.out.println("📡 Testando endpoint inválido: " + url);
            
            HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
            
            System.out.println("📊 Status Code: " + response.statusCode());
            
            // Demonstrar diferentes códigos de status
            switch (response.statusCode()) {
                case 200:
                    System.out.println("✅ Sucesso - Dados recebidos");
                    break;
                case 404:
                    System.out.println("❌ Não encontrado - Endpoint ou recurso inexistente");
                    break;
                case 500:
                    System.out.println("❌ Erro interno do servidor");
                    break;
                default:
                    System.out.println("⚠️  Status inesperado: " + response.statusCode());
            }
            
            // Mostrar headers de erro se disponíveis
            response.headers().map().forEach((key, values) -> {
                if (key.toLowerCase().contains("error") || 
                    key.toLowerCase().contains("warning")) {
                    System.out.println("🔍 Header de erro - " + key + ": " + values);
                }
            });
            
        } catch (Exception e) {
            System.out.println("❌ Exceção capturada: " + e.getClass().getSimpleName());
            System.out.println("   Mensagem: " + e.getMessage());
        }
        
        System.out.println("\n🎯 RESUMO DO TRATAMENTO DE ERROS:");
        System.out.println("   • Sempre verificar response.statusCode()");
        System.out.println("   • Usar try-catch para IOException e InterruptedException");
        System.out.println("   • Verificar headers para informações adicionais");
        System.out.println("   • Implementar retry logic quando apropriado");
        System.out.println("   • Log detalhado para debugging");
    }
}
