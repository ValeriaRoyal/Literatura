package com.exemplo.literatura.service;

import com.exemplo.literatura.dto.GutendxResponse;
import com.exemplo.literatura.dto.LivroDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GutendxService {
    
    private final WebClient webClient;
    private static final String GUTENDX_BASE_URL = "https://gutendx.com";
    
    public GutendxService() {
        this.webClient = WebClient.builder()
                .baseUrl(GUTENDX_BASE_URL)
                .build();
    }
    
    /**
     * Busca livros por termo de pesquisa
     * @param searchTerm termo de busca (título, autor, etc.)
     * @return lista de livros encontrados
     */
    public List<LivroDto> buscarLivros(String searchTerm) {
        try {
            GutendxResponse response = webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/books/")
                            .queryParam("search", searchTerm)
                            .build())
                    .retrieve()
                    .bodyToMono(GutendxResponse.class)
                    .block();
            
            return response != null ? response.getResults() : List.of();
        } catch (Exception e) {
            System.err.println("Erro ao buscar livros: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * Busca livros por autor
     * @param nomeAutor nome do autor
     * @return lista de livros do autor
     */
    public List<LivroDto> buscarLivrosPorAutor(String nomeAutor) {
        return buscarLivros(nomeAutor);
    }
    
    /**
     * Busca livros por título
     * @param titulo título do livro
     * @return lista de livros com o título
     */
    public List<LivroDto> buscarLivrosPorTitulo(String titulo) {
        return buscarLivros(titulo);
    }
    
    /**
     * Busca livros por idioma
     * @param idioma código do idioma (ex: "en", "pt", "es")
     * @return lista de livros no idioma especificado
     */
    public List<LivroDto> buscarLivrosPorIdioma(String idioma) {
        try {
            GutendxResponse response = webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/books/")
                            .queryParam("languages", idioma)
                            .build())
                    .retrieve()
                    .bodyToMono(GutendxResponse.class)
                    .block();
            
            return response != null ? response.getResults() : List.of();
        } catch (Exception e) {
            System.err.println("Erro ao buscar livros por idioma: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * Busca livros mais populares (ordenados por download_count)
     * @param limite número máximo de livros a retornar
     * @return lista dos livros mais populares
     */
    public List<LivroDto> buscarLivrosPopulares(int limite) {
        try {
            GutendxResponse response = webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/books/")
                            .queryParam("sort", "popular")
                            .build())
                    .retrieve()
                    .bodyToMono(GutendxResponse.class)
                    .block();
            
            if (response != null && response.getResults() != null) {
                return response.getResults().stream()
                        .limit(limite)
                        .toList();
            }
            return List.of();
        } catch (Exception e) {
            System.err.println("Erro ao buscar livros populares: " + e.getMessage());
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
            return webClient
                    .get()
                    .uri("/books/{id}/", id)
                    .retrieve()
                    .bodyToMono(LivroDto.class)
                    .block();
        } catch (Exception e) {
            System.err.println("Erro ao buscar livro por ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Lista todos os livros com paginação
     * @param pagina número da página (começando em 1)
     * @return resposta com livros e informações de paginação
     */
    public GutendxResponse listarLivros(int pagina) {
        try {
            return webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/books/")
                            .queryParam("page", pagina)
                            .build())
                    .retrieve()
                    .bodyToMono(GutendxResponse.class)
                    .block();
        } catch (Exception e) {
            System.err.println("Erro ao listar livros: " + e.getMessage());
            return new GutendxResponse();
        }
    }
}
