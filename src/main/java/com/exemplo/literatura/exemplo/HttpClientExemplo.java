package com.exemplo.literatura.exemplo;

import com.exemplo.literatura.dto.GutendxResponse;
import com.exemplo.literatura.dto.LivroDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Exemplo prático demonstrando o uso das classes HttpClient, HttpRequest e HttpResponse
 * para consumir a API Gutendx do Project Gutenberg
 */
public class HttpClientExemplo {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(70));
        System.out.println("🚀 EXEMPLO PRÁTICO: HttpClient, HttpRequest e HttpResponse");
        System.out.println("=".repeat(70));
        
        // Demonstração completa das classes HTTP
        demonstrarHttpClasses();
    }
    
    public static void demonstrarHttpClasses() {
        try {
            // 1. CRIANDO O HttpClient
            System.out.println("\n🔧 1. CRIANDO O HttpClient");
            System.out.println("-".repeat(40));
            
            HttpClient httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))  // Timeout de conexão
                    .followRedirects(HttpClient.Redirect.NORMAL)  // Seguir redirecionamentos
                    .build();
            
            System.out.println("✅ HttpClient criado com:");
            System.out.println("   • Timeout de conexão: 10 segundos");
            System.out.println("   • Redirecionamentos: Automático");
            System.out.println("   • Versão HTTP: 1.1 e 2.0 suportadas");
            
            // 2. CONSTRUINDO O HttpRequest
            System.out.println("\n🔧 2. CONSTRUINDO O HttpRequest");
            System.out.println("-".repeat(40));
            
            String url = "https://gutendx.com/books/?search=shakespeare";
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))  // URL da requisição
                    .timeout(Duration.ofSeconds(30))  // Timeout da requisição
                    .header("Accept", "application/json")  // Cabeçalho Accept
                    .header("User-Agent", "Literatura-App/1.0")  // User-Agent personalizado
                    .GET()  // Método HTTP GET
                    .build();
            
            System.out.println("✅ HttpRequest configurado com:");
            System.out.println("   • URL: " + url);
            System.out.println("   • Método: GET");
            System.out.println("   • Timeout: 30 segundos");
            System.out.println("   • Accept: application/json");
            System.out.println("   • User-Agent: Literatura-App/1.0");
            
            // 3. ENVIANDO A REQUISIÇÃO E OBTENDO HttpResponse
            System.out.println("\n🔧 3. ENVIANDO REQUISIÇÃO E OBTENDO HttpResponse");
            System.out.println("-".repeat(50));
            
            System.out.println("📡 Enviando requisição para a API Gutendx...");
            
            HttpResponse<String> response = httpClient.send(request, 
                    HttpResponse.BodyHandlers.ofString());
            
            System.out.println("✅ Resposta recebida!");
            
            // 4. ANALISANDO A RESPOSTA
            System.out.println("\n🔧 4. ANALISANDO A HttpResponse");
            System.out.println("-".repeat(40));
            
            System.out.println("📊 Status Code: " + response.statusCode());
            System.out.println("🌐 URI: " + response.uri());
            System.out.println("📏 Tamanho do corpo: " + response.body().length() + " caracteres");
            
            // Exibindo alguns cabeçalhos importantes
            System.out.println("\n📋 Cabeçalhos da Resposta:");
            response.headers().map().forEach((key, values) -> {
                if (key.toLowerCase().contains("content") || 
                    key.toLowerCase().contains("server") ||
                    key.toLowerCase().contains("date")) {
                    System.out.println("   • " + key + ": " + String.join(", ", values));
                }
            });
            
            // 5. PROCESSANDO O CORPO DA RESPOSTA
            System.out.println("\n🔧 5. PROCESSANDO O CORPO DA RESPOSTA");
            System.out.println("-".repeat(45));
            
            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                GutendxResponse gutendxResponse = objectMapper.readValue(
                        response.body(), GutendxResponse.class);
                
                System.out.println("✅ JSON convertido para objeto Java:");
                System.out.println("   • Total de livros no catálogo: " + gutendxResponse.getCount());
                System.out.println("   • Livros na página atual: " + 
                        (gutendxResponse.getResults() != null ? gutendxResponse.getResults().size() : 0));
                
                if (gutendxResponse.getResults() != null && !gutendxResponse.getResults().isEmpty()) {
                    System.out.println("\n📚 Primeiros livros encontrados:");
                    for (int i = 0; i < Math.min(3, gutendxResponse.getResults().size()); i++) {
                        LivroDto livro = gutendxResponse.getResults().get(i);
                        System.out.println("   " + (i + 1) + ". " + livro.getTitle());
                        if (livro.getAuthors() != null && !livro.getAuthors().isEmpty()) {
                            System.out.println("      Autor: " + livro.getAuthors().get(0).getName());
                        }
                        System.out.println("      Downloads: " + livro.getDownloadCount());
                    }
                }
            } else {
                System.out.println("❌ Erro na requisição. Status: " + response.statusCode());
            }
            
            // 6. RESUMO DAS CLASSES UTILIZADAS
            System.out.println("\n🔧 6. RESUMO DAS CLASSES HTTP");
            System.out.println("-".repeat(40));
            
            System.out.println("🔹 HttpClient:");
            System.out.println("   • Gerencia conexões HTTP/HTTPS");
            System.out.println("   • Configurável (timeouts, redirecionamentos, etc.)");
            System.out.println("   • Reutilizável para múltiplas requisições");
            
            System.out.println("\n🔹 HttpRequest:");
            System.out.println("   • Representa uma requisição HTTP");
            System.out.println("   • Imutável após construção");
            System.out.println("   • Suporte a todos os métodos HTTP (GET, POST, PUT, DELETE, etc.)");
            
            System.out.println("\n🔹 HttpResponse:");
            System.out.println("   • Contém a resposta completa do servidor");
            System.out.println("   • Acesso a status code, cabeçalhos e corpo");
            System.out.println("   • Suporte a diferentes tipos de corpo (String, bytes, Stream, etc.)");
            
            System.out.println("\n" + "=".repeat(70));
            System.out.println("✅ DEMONSTRAÇÃO CONCLUÍDA COM SUCESSO!");
            System.out.println("=".repeat(70));
            
        } catch (IOException e) {
            System.err.println("❌ Erro de I/O: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("❌ Requisição interrompida: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("❌ Erro inesperado: " + e.getMessage());
        }
    }
}
