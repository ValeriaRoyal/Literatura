package com.exemplo.literatura.service;

import com.exemplo.literatura.dto.GutendxResponse;
import com.exemplo.literatura.dto.LivroDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

@Service
public class GutendxHttpService {
    
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private static final String GUTENDX_BASE_URL = "https://gutendx.com";
    
    public GutendxHttpService() {
        // Configurando o HttpClient com timeout e redirecionamento
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        
        this.objectMapper = new ObjectMapper();
    }
    
    /**
     * Busca livros por termo de pesquisa usando HttpClient nativo do Java
     * @param searchTerm termo de busca (título, autor, etc.)
     * @return lista de livros encontrados
     */
    public List<LivroDto> buscarLivros(String searchTerm) {
        try {
            // Codificando o termo de busca para URL
            String encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
            String url = GUTENDX_BASE_URL + "/books/?search=" + encodedSearchTerm;
            
            // Construindo a requisição HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(30))
                    .header("Accept", "application/json")
                    .header("User-Agent", "Literatura-App/1.0")
                    .GET()
                    .build();
            
            // Enviando a requisição e obtendo a resposta
            HttpResponse<String> response = httpClient.send(request, 
                    HttpResponse.BodyHandlers.ofString());
            
            // Verificando o código de status da resposta
            if (response.statusCode() == 200) {
                // Convertendo JSON para objeto Java
                GutendxResponse gutendxResponse = objectMapper.readValue(
                        response.body(), GutendxResponse.class);
                
                System.out.println("✅ Busca realizada com sucesso!");
                System.out.println("📊 Status Code: " + response.statusCode());
                System.out.println("📚 Total de livros encontrados: " + 
                        (gutendxResponse.getResults() != null ? gutendxResponse.getResults().size() : 0));
                
                return gutendxResponse.getResults() != null ? gutendxResponse.getResults() : List.of();
            } else {
                System.err.println("❌ Erro na requisição. Status Code: " + response.statusCode());
                System.err.println("📄 Resposta: " + response.body());
                return List.of();
            }
            
        } catch (IOException e) {
            System.err.println("❌ Erro de I/O ao fazer requisição: " + e.getMessage());
            return List.of();
        } catch (InterruptedException e) {
            System.err.println("❌ Requisição interrompida: " + e.getMessage());
            Thread.currentThread().interrupt();
            return List.of();
        } catch (Exception e) {
            System.err.println("❌ Erro inesperado: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * Busca livros por autor
     * @param nomeAutor nome do autor
     * @return lista de livros do autor
     */
    public List<LivroDto> buscarLivrosPorAutor(String nomeAutor) {
        System.out.println("🔍 Buscando livros do autor: " + nomeAutor);
        return buscarLivros(nomeAutor);
    }
    
    /**
     * Busca livros por título
     * @param titulo título do livro
     * @return lista de livros com o título
     */
    public List<LivroDto> buscarLivrosPorTitulo(String titulo) {
        System.out.println("🔍 Buscando livros com título: " + titulo);
        return buscarLivros(titulo);
    }
    
    /**
     * Busca livros por idioma
     * @param idioma código do idioma (ex: "en", "pt", "es")
     * @return lista de livros no idioma especificado
     */
    public List<LivroDto> buscarLivrosPorIdioma(String idioma) {
        try {
            String url = GUTENDX_BASE_URL + "/books/?languages=" + idioma;
            
            System.out.println("🔍 Buscando livros no idioma: " + idioma);
            System.out.println("🌐 URL da requisição: " + url);
            
            // Construindo a requisição HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(30))
                    .header("Accept", "application/json")
                    .header("User-Agent", "Literatura-App/1.0")
                    .GET()
                    .build();
            
            // Enviando a requisição
            HttpResponse<String> response = httpClient.send(request, 
                    HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                GutendxResponse gutendxResponse = objectMapper.readValue(
                        response.body(), GutendxResponse.class);
                
                System.out.println("✅ Busca por idioma realizada com sucesso!");
                System.out.println("📊 Status Code: " + response.statusCode());
                
                return gutendxResponse.getResults() != null ? gutendxResponse.getResults() : List.of();
            } else {
                System.err.println("❌ Erro na requisição por idioma. Status Code: " + response.statusCode());
                return List.of();
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erro ao buscar livros por idioma: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * Busca um livro específico por ID
     * @param id ID do livro no Project Gutenberg
     * @return dados do livro ou null se não encontrado
     */
    public LivroDto buscarLivroPorId(Long id) {
        try {
            String url = GUTENDX_BASE_URL + "/books/" + id + "/";
            
            System.out.println("🔍 Buscando livro com ID: " + id);
            System.out.println("🌐 URL da requisição: " + url);
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(30))
                    .header("Accept", "application/json")
                    .header("User-Agent", "Literatura-App/1.0")
                    .GET()
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, 
                    HttpResponse.BodyHandlers.ofString());
            
            System.out.println("📊 Status Code: " + response.statusCode());
            
            if (response.statusCode() == 200) {
                LivroDto livro = objectMapper.readValue(response.body(), LivroDto.class);
                System.out.println("✅ Livro encontrado: " + livro.getTitle());
                return livro;
            } else if (response.statusCode() == 404) {
                System.out.println("❌ Livro com ID " + id + " não encontrado.");
                return null;
            } else {
                System.err.println("❌ Erro na requisição. Status Code: " + response.statusCode());
                return null;
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erro ao buscar livro por ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Lista livros com paginação
     * @param pagina número da página (começando em 1)
     * @return resposta com livros e informações de paginação
     */
    public GutendxResponse listarLivros(int pagina) {
        try {
            String url = GUTENDX_BASE_URL + "/books/?page=" + pagina;
            
            System.out.println("📖 Listando livros - Página: " + pagina);
            System.out.println("🌐 URL da requisição: " + url);
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(30))
                    .header("Accept", "application/json")
                    .header("User-Agent", "Literatura-App/1.0")
                    .GET()
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, 
                    HttpResponse.BodyHandlers.ofString());
            
            System.out.println("📊 Status Code: " + response.statusCode());
            System.out.println("📏 Tamanho da resposta: " + response.body().length() + " caracteres");
            
            if (response.statusCode() == 200) {
                GutendxResponse gutendxResponse = objectMapper.readValue(
                        response.body(), GutendxResponse.class);
                
                System.out.println("✅ Lista de livros obtida com sucesso!");
                System.out.println("📊 Total de livros no catálogo: " + gutendxResponse.getCount());
                
                return gutendxResponse;
            } else {
                System.err.println("❌ Erro ao listar livros. Status Code: " + response.statusCode());
                return new GutendxResponse();
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erro ao listar livros: " + e.getMessage());
            return new GutendxResponse();
        }
    }
    
    /**
     * Método para testar a conectividade com a API
     * @return true se a API estiver acessível
     */
    public boolean testarConectividade() {
        try {
            String url = GUTENDX_BASE_URL + "/books/?page=1";
            
            System.out.println("🔧 Testando conectividade com a API Gutendx...");
            System.out.println("🌐 URL de teste: " + url);
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .header("Accept", "application/json")
                    .header("User-Agent", "Literatura-App/1.0")
                    .GET()
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, 
                    HttpResponse.BodyHandlers.ofString());
            
            System.out.println("📊 Status Code: " + response.statusCode());
            
            if (response.statusCode() == 200) {
                System.out.println("✅ Conectividade OK! API Gutendx está acessível.");
                return true;
            } else {
                System.out.println("⚠️ API acessível mas retornou status: " + response.statusCode());
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erro de conectividade: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtém informações detalhadas sobre a resposta HTTP
     * @param response resposta HTTP
     */
    private void exibirDetalhesResposta(HttpResponse<String> response) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("DETALHES DA RESPOSTA HTTP");
        System.out.println("=".repeat(50));
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("URI: " + response.uri());
        
        System.out.println("\nCabeçalhos da Resposta:");
        response.headers().map().forEach((key, values) -> {
            System.out.println("  " + key + ": " + String.join(", ", values));
        });
        
        System.out.println("\nTamanho do corpo: " + response.body().length() + " caracteres");
        System.out.println("=".repeat(50));
    }
    
    /**
     * Obtém a resposta JSON bruta da API para análise
     * @param searchTerm termo de busca
     * @return JSON string da resposta
     */
    public String obterJsonResposta(String searchTerm) throws IOException, InterruptedException {
        String encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
        String url = GUTENDX_BASE_URL + "/books/?search=" + encodedSearchTerm;
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .header("Accept", "application/json")
                .header("User-Agent", "Literatura-App/1.0")
                .GET()
                .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new IOException("Erro na API: " + response.statusCode());
        }
    }
}
