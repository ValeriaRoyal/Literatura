package com.exemplo.literatura.exemplo;

import com.exemplo.literatura.dto.GutendxResponse;
import com.exemplo.literatura.dto.LivroDto;
import com.exemplo.literatura.dto.AutorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

/**
 * FASE 4 - ANÁLISE DE RESPOSTA JSON COM JACKSON
 * 
 * Este exemplo demonstra o uso avançado da biblioteca Jackson 2.16
 * para análise e manipulação de dados JSON da API Gutendx.
 * 
 * Funcionalidades demonstradas:
 * - ObjectMapper com configurações avançadas
 * - Mapeamento JSON para objetos Java
 * - Análise de estruturas JSON complexas
 * - Tratamento de propriedades opcionais
 * - Navegação em árvore JSON com JsonNode
 */
public class JacksonAnaliseJsonExemplo {
    
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private static final String GUTENDX_BASE_URL = "https://gutendx.com/books/";
    
    public JacksonAnaliseJsonExemplo() {
        // Configuração do HttpClient
        this.httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();
        
        // Configuração avançada do ObjectMapper Jackson 2.16
        this.objectMapper = new ObjectMapper();
        
        // Configurações para lidar com propriedades desconhecidas
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        
        // Configuração de naming strategy (se necessário)
        // objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }
    
    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("📚 FASE 4: ANÁLISE DE RESPOSTA JSON COM JACKSON 2.16");
        System.out.println("======================================================================\n");
        
        JacksonAnaliseJsonExemplo exemplo = new JacksonAnaliseJsonExemplo();
        
        // 1. Demonstrar mapeamento básico JSON -> Objeto
        exemplo.demonstrarMapeamentoBasico();
        
        // 2. Análise detalhada da estrutura JSON
        exemplo.analisarEstruturaJson();
        
        // 3. Navegação com JsonNode
        exemplo.demonstrarJsonNode();
        
        // 4. Tratamento de casos especiais
        exemplo.tratarCasosEspeciais();
        
        // 5. Extração de dados específicos
        exemplo.extrairDadosEspecificos();
    }
    
    private void demonstrarMapeamentoBasico() {
        System.out.println("🔧 1. MAPEAMENTO BÁSICO JSON → OBJETO JAVA");
        System.out.println("==========================================");
        
        try {
            // Fazer requisição para API
            String url = GUTENDX_BASE_URL + "?search=machado&page_size=2";
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();
            
            System.out.println("📡 Fazendo requisição para: " + url);
            
            HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                System.out.println("✅ Resposta recebida com sucesso!");
                System.out.println("📊 Status Code: " + response.statusCode());
                System.out.println("📏 Tamanho da resposta: " + response.body().length() + " caracteres");
                
                // Mapeamento direto JSON → GutendxResponse
                GutendxResponse gutendxResponse = objectMapper.readValue(
                    response.body(), GutendxResponse.class);
                
                System.out.println("\n📖 Dados mapeados:");
                System.out.println("   • Total de livros: " + gutendxResponse.getCount());
                System.out.println("   • Próxima página: " + gutendxResponse.getNext());
                System.out.println("   • Página anterior: " + gutendxResponse.getPrevious());
                System.out.println("   • Livros nesta página: " + gutendxResponse.getResults().size());
                
                // Exibir detalhes dos livros
                for (LivroDto livro : gutendxResponse.getResults()) {
                    System.out.println("\n   📚 Livro: " + livro.getTitle());
                    System.out.println("      ID: " + livro.getId());
                    System.out.println("      Downloads: " + livro.getDownloadCount());
                    System.out.println("      Idiomas: " + livro.getLanguages());
                    
                    if (!livro.getAuthors().isEmpty()) {
                        AutorDto autor = livro.getAuthors().get(0);
                        System.out.println("      Autor: " + autor.getName());
                        System.out.println("      Nascimento: " + autor.getBirthYear());
                        System.out.println("      Morte: " + autor.getDeathYear());
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro no mapeamento básico: " + e.getMessage());
        }
        System.out.println();
    }
    
    private void analisarEstruturaJson() {
        System.out.println("🔧 2. ANÁLISE DETALHADA DA ESTRUTURA JSON");
        System.out.println("=========================================");
        
        try {
            String url = GUTENDX_BASE_URL + "?search=shakespeare&page_size=1";
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                String jsonString = response.body();
                
                System.out.println("📋 Estrutura JSON da resposta:");
                System.out.println("------------------------------");
                
                // Análise da estrutura usando JsonNode
                JsonNode rootNode = objectMapper.readTree(jsonString);
                
                System.out.println("🔍 Propriedades do nível raiz:");
                rootNode.fieldNames().forEachRemaining(fieldName -> {
                    JsonNode fieldValue = rootNode.get(fieldName);
                    System.out.println("   • " + fieldName + ": " + 
                        getJsonNodeType(fieldValue) + 
                        (fieldValue.isValueNode() ? " = " + fieldValue.asText() : ""));
                });
                
                // Analisar array de resultados
                JsonNode resultsNode = rootNode.get("results");
                if (resultsNode != null && resultsNode.isArray() && resultsNode.size() > 0) {
                    System.out.println("\n🔍 Estrutura de um livro (primeiro resultado):");
                    JsonNode primeiroLivro = resultsNode.get(0);
                    
                    primeiroLivro.fieldNames().forEachRemaining(fieldName -> {
                        JsonNode fieldValue = primeiroLivro.get(fieldName);
                        System.out.println("   • " + fieldName + ": " + 
                            getJsonNodeType(fieldValue));
                        
                        // Mostrar detalhes de arrays e objetos
                        if (fieldValue.isArray() && fieldValue.size() > 0) {
                            System.out.println("     └─ Primeiro elemento: " + 
                                getJsonNodeType(fieldValue.get(0)));
                        }
                    });
                }
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro na análise da estrutura: " + e.getMessage());
        }
        System.out.println();
    }
    
    private void demonstrarJsonNode() {
        System.out.println("🔧 3. NAVEGAÇÃO COM JsonNode");
        System.out.println("============================");
        
        try {
            String url = GUTENDX_BASE_URL + "?search=dom+casmurro&page_size=1";
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                JsonNode rootNode = objectMapper.readTree(response.body());
                
                System.out.println("🧭 Navegando pela árvore JSON:");
                
                // Acessar propriedades aninhadas
                JsonNode countNode = rootNode.path("count");
                System.out.println("   • Total de livros: " + countNode.asInt());
                
                JsonNode resultsNode = rootNode.path("results");
                if (resultsNode.isArray() && resultsNode.size() > 0) {
                    JsonNode primeiroLivro = resultsNode.get(0);
                    
                    // Acessar propriedades do livro
                    System.out.println("   • Título: " + primeiroLivro.path("title").asText());
                    System.out.println("   • ID: " + primeiroLivro.path("id").asInt());
                    System.out.println("   • Downloads: " + primeiroLivro.path("download_count").asInt());
                    
                    // Acessar array de autores
                    JsonNode autoresNode = primeiroLivro.path("authors");
                    if (autoresNode.isArray() && autoresNode.size() > 0) {
                        JsonNode primeiroAutor = autoresNode.get(0);
                        System.out.println("   • Autor: " + primeiroAutor.path("name").asText());
                        System.out.println("   • Nascimento: " + primeiroAutor.path("birth_year").asText());
                        System.out.println("   • Morte: " + primeiroAutor.path("death_year").asText());
                    }
                    
                    // Acessar array de idiomas
                    JsonNode idiomasNode = primeiroLivro.path("languages");
                    if (idiomasNode.isArray()) {
                        System.out.print("   • Idiomas: ");
                        for (JsonNode idioma : idiomasNode) {
                            System.out.print(idioma.asText() + " ");
                        }
                        System.out.println();
                    }
                    
                    // Acessar formatos disponíveis
                    JsonNode formatosNode = primeiroLivro.path("formats");
                    if (formatosNode.isObject()) {
                        System.out.println("   • Formatos disponíveis:");
                        formatosNode.fieldNames().forEachRemaining(formato -> {
                            System.out.println("     - " + formato + ": " + 
                                formatosNode.get(formato).asText());
                        });
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro na navegação JsonNode: " + e.getMessage());
        }
        System.out.println();
    }
    
    private void tratarCasosEspeciais() {
        System.out.println("🔧 4. TRATAMENTO DE CASOS ESPECIAIS");
        System.out.println("===================================");
        
        System.out.println("🛡️ Configurações do ObjectMapper para robustez:");
        System.out.println("   • FAIL_ON_UNKNOWN_PROPERTIES: false");
        System.out.println("   • ACCEPT_EMPTY_STRING_AS_NULL_OBJECT: true");
        System.out.println("   • Tratamento automático de valores null");
        System.out.println("   • Conversão flexível de tipos");
        
        try {
            // Exemplo de JSON com propriedades faltantes
            String jsonComPropriedadesFaltantes = """
                {
                    "count": 100,
                    "results": [
                        {
                            "id": 1,
                            "title": "Livro Exemplo",
                            "authors": [],
                            "languages": ["pt"],
                            "download_count": null,
                            "formats": {}
                        }
                    ]
                }
                """;
            
            System.out.println("\n🧪 Testando JSON com propriedades faltantes/nulas:");
            GutendxResponse response = objectMapper.readValue(
                jsonComPropriedadesFaltantes, GutendxResponse.class);
            
            System.out.println("✅ Mapeamento bem-sucedido!");
            System.out.println("   • Count: " + response.getCount());
            System.out.println("   • Livros: " + response.getResults().size());
            
            if (!response.getResults().isEmpty()) {
                LivroDto livro = response.getResults().get(0);
                System.out.println("   • Título: " + livro.getTitle());
                System.out.println("   • Downloads: " + livro.getDownloadCount());
                System.out.println("   • Autores: " + livro.getAuthors().size());
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro no tratamento de casos especiais: " + e.getMessage());
        }
        System.out.println();
    }
    
    private void extrairDadosEspecificos() {
        System.out.println("🔧 5. EXTRAÇÃO DE DADOS ESPECÍFICOS");
        System.out.println("===================================");
        
        try {
            String url = GUTENDX_BASE_URL + "?languages=pt&page_size=3";
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                JsonNode rootNode = objectMapper.readTree(response.body());
                
                System.out.println("🎯 Extraindo dados específicos de livros em português:");
                
                JsonNode resultsNode = rootNode.path("results");
                for (JsonNode livroNode : resultsNode) {
                    String titulo = livroNode.path("title").asText("Título não disponível");
                    int downloads = livroNode.path("download_count").asInt(0);
                    
                    System.out.println("\n📚 " + titulo);
                    System.out.println("   Downloads: " + downloads);
                    
                    // Extrair nomes dos autores
                    JsonNode autoresNode = livroNode.path("authors");
                    if (autoresNode.isArray() && autoresNode.size() > 0) {
                        System.out.print("   Autores: ");
                        for (JsonNode autorNode : autoresNode) {
                            String nomeAutor = autorNode.path("name").asText();
                            System.out.print(nomeAutor + "; ");
                        }
                        System.out.println();
                    }
                    
                    // Extrair formatos disponíveis
                    JsonNode formatosNode = livroNode.path("formats");
                    if (formatosNode.isObject()) {
                        System.out.println("   Formatos: " + formatosNode.size() + " disponíveis");
                        
                        // Verificar se tem PDF
                        boolean temPdf = false;
                        Iterator<String> formatoIterator = formatosNode.fieldNames();
                        while (formatoIterator.hasNext()) {
                            String formato = formatoIterator.next();
                            if (formato.toLowerCase().contains("pdf")) {
                                temPdf = true;
                                break;
                            }
                        }
                        System.out.println("   PDF disponível: " + (temPdf ? "Sim" : "Não"));
                        System.out.println("   PDF disponível: " + (temPdf ? "Sim" : "Não"));
                    }
                }
                
                // Estatísticas gerais
                System.out.println("\n📊 ESTATÍSTICAS:");
                int totalLivros = rootNode.path("count").asInt();
                System.out.println("   • Total de livros em português: " + totalLivros);
                System.out.println("   • Livros nesta página: " + resultsNode.size());
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro na extração de dados específicos: " + e.getMessage());
        }
        
        System.out.println("\n🎓 RESUMO DA ANÁLISE JSON COM JACKSON:");
        System.out.println("=====================================");
        System.out.println("✅ ObjectMapper configurado com Jackson 2.16");
        System.out.println("✅ Mapeamento automático JSON → Objetos Java");
        System.out.println("✅ Navegação flexível com JsonNode");
        System.out.println("✅ Tratamento robusto de propriedades opcionais");
        System.out.println("✅ Extração eficiente de dados específicos");
        System.out.println("✅ Configurações avançadas para casos especiais");
    }
    
    private String getJsonNodeType(JsonNode node) {
        if (node.isArray()) {
            return "Array[" + node.size() + "]";
        } else if (node.isObject()) {
            return "Object{" + node.size() + " props}";
        } else if (node.isTextual()) {
            return "String";
        } else if (node.isNumber()) {
            return "Number";
        } else if (node.isBoolean()) {
            return "Boolean";
        } else if (node.isNull()) {
            return "null";
        } else {
            return "Unknown";
        }
    }
}
