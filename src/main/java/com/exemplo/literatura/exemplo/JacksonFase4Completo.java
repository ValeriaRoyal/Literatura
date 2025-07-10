package com.exemplo.literatura.exemplo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.*;

/**
 * FASE 4 - DEMONSTRAÇÃO COMPLETA DO JACKSON 2.16
 * 
 * Este exemplo demonstra todas as funcionalidades avançadas do Jackson
 * para análise e manipulação de dados JSON, conforme solicitado na Fase 4.
 */
public class JacksonFase4Completo {
    
    private final ObjectMapper objectMapper;
    
    public JacksonFase4Completo() {
        // Configuração avançada do ObjectMapper Jackson 2.16
        this.objectMapper = new ObjectMapper();
        
        // Configurações para robustez
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }
    
    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("📚 FASE 4: ANÁLISE COMPLETA DE JSON COM JACKSON 2.16");
        System.out.println("======================================================================\n");
        
        JacksonFase4Completo demo = new JacksonFase4Completo();
        
        // 1. Demonstrar ObjectMapper básico
        demo.demonstrarObjectMapper();
        
        // 2. Análise com JsonNode
        demo.demonstrarJsonNode();
        
        // 3. Mapeamento para diferentes tipos
        demo.demonstrarMapeamentoTipos();
        
        // 4. Manipulação de JSON
        demo.demonstrarManipulacaoJson();
        
        // 5. Casos especiais e tratamento de erros
        demo.demonstrarCasosEspeciais();
        
        // 6. Exemplo prático com dados da API Gutendx
        demo.exemploApiGutendx();
    }
    
    private void demonstrarObjectMapper() {
        System.out.println("🔧 1. OBJECTMAPPER - MAPEAMENTO JSON ↔ JAVA");
        System.out.println("===========================================");
        
        try {
            // JSON de exemplo simulando resposta da API Gutendx
            String jsonLivro = """
                {
                    "id": 1342,
                    "title": "Pride and Prejudice",
                    "authors": [
                        {
                            "name": "Austen, Jane",
                            "birth_year": 1775,
                            "death_year": 1817
                        }
                    ],
                    "languages": ["en"],
                    "download_count": 45678,
                    "formats": {
                        "text/html": "https://www.gutenberg.org/files/1342/1342-h/1342-h.htm",
                        "application/epub+zip": "https://www.gutenberg.org/ebooks/1342.epub.images",
                        "text/plain": "https://www.gutenberg.org/files/1342/1342-0.txt"
                    },
                    "subjects": ["England -- Social life and customs -- 19th century -- Fiction"]
                }
                """;
            
            System.out.println("📖 JSON Original:");
            System.out.println(jsonLivro.substring(0, Math.min(200, jsonLivro.length())) + "...");
            
            // Converter JSON para Map
            TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
            Map<String, Object> livroMap = objectMapper.readValue(jsonLivro, typeRef);
            
            System.out.println("\n✅ Mapeamento para Map:");
            System.out.println("   • ID: " + livroMap.get("id"));
            System.out.println("   • Título: " + livroMap.get("title"));
            System.out.println("   • Downloads: " + livroMap.get("download_count"));
            
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> autores = (List<Map<String, Object>>) livroMap.get("authors");
            if (!autores.isEmpty()) {
                Map<String, Object> autor = autores.get(0);
                System.out.println("   • Autor: " + autor.get("name"));
                System.out.println("   • Nascimento: " + autor.get("birth_year"));
            }
            
            @SuppressWarnings("unchecked")
            Map<String, String> formatos = (Map<String, String>) livroMap.get("formats");
            System.out.println("   • Formatos disponíveis: " + formatos.size());
            
        } catch (Exception e) {
            System.out.println("❌ Erro no ObjectMapper: " + e.getMessage());
        }
        System.out.println();
    }
    
    private void demonstrarJsonNode() {
        System.out.println("🔧 2. JsonNode - NAVEGAÇÃO E ANÁLISE");
        System.out.println("====================================");
        
        try {
            // JSON complexo simulando resposta da API
            String jsonResposta = """
                {
                    "count": 2847,
                    "next": "https://gutendx.com/books/?page=2&search=shakespeare",
                    "previous": null,
                    "results": [
                        {
                            "id": 1513,
                            "title": "Romeo and Juliet",
                            "authors": [
                                {
                                    "name": "Shakespeare, William",
                                    "birth_year": 1564,
                                    "death_year": 1616
                                }
                            ],
                            "languages": ["en"],
                            "download_count": 25432,
                            "formats": {
                                "text/html": "https://www.gutenberg.org/files/1513/1513-h/1513-h.htm",
                                "application/pdf": "https://www.gutenberg.org/files/1513/1513-pdf.pdf"
                            }
                        },
                        {
                            "id": 1524,
                            "title": "Hamlet",
                            "authors": [
                                {
                                    "name": "Shakespeare, William",
                                    "birth_year": 1564,
                                    "death_year": 1616
                                }
                            ],
                            "languages": ["en"],
                            "download_count": 31245,
                            "formats": {
                                "text/html": "https://www.gutenberg.org/files/1524/1524-h/1524-h.htm"
                            }
                        }
                    ]
                }
                """;
            
            JsonNode rootNode = objectMapper.readTree(jsonResposta);
            
            System.out.println("🧭 Navegação pela estrutura JSON:");
            
            // Acessar propriedades do nível raiz
            System.out.println("   • Total de livros: " + rootNode.path("count").asInt());
            System.out.println("   • Próxima página: " + rootNode.path("next").asText("N/A"));
            System.out.println("   • Página anterior: " + (rootNode.path("previous").isNull() ? "N/A" : rootNode.path("previous").asText()));
            
            // Navegar pelo array de resultados
            JsonNode resultsNode = rootNode.path("results");
            System.out.println("   • Livros nesta página: " + resultsNode.size());
            
            System.out.println("\n📚 Detalhes dos livros:");
            for (int i = 0; i < resultsNode.size(); i++) {
                JsonNode livro = resultsNode.get(i);
                
                System.out.println("   " + (i + 1) + ". " + livro.path("title").asText());
                System.out.println("      ID: " + livro.path("id").asInt());
                System.out.println("      Downloads: " + livro.path("download_count").asInt());
                
                // Navegar pelos autores
                JsonNode autoresNode = livro.path("authors");
                if (autoresNode.isArray() && autoresNode.size() > 0) {
                    JsonNode autor = autoresNode.get(0);
                    System.out.println("      Autor: " + autor.path("name").asText());
                    System.out.println("      Período: " + autor.path("birth_year").asInt() + 
                                     " - " + autor.path("death_year").asInt());
                }
                
                // Verificar formatos
                JsonNode formatosNode = livro.path("formats");
                System.out.println("      Formatos: " + formatosNode.size());
                
                // Verificar se tem PDF
                boolean temPdf = formatosNode.has("application/pdf");
                System.out.println("      PDF disponível: " + (temPdf ? "Sim" : "Não"));
                System.out.println();
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro no JsonNode: " + e.getMessage());
        }
    }
    
    private void demonstrarMapeamentoTipos() {
        System.out.println("🔧 3. MAPEAMENTO PARA DIFERENTES TIPOS");
        System.out.println("======================================");
        
        try {
            String jsonArray = """
                [
                    {"nome": "Dom Casmurro", "autor": "Machado de Assis", "ano": 1899},
                    {"nome": "O Cortiço", "autor": "Aluísio Azevedo", "ano": 1890},
                    {"nome": "Iracema", "autor": "José de Alencar", "ano": 1865}
                ]
                """;
            
            // Mapeamento para List<Map>
            TypeReference<List<Map<String, Object>>> listTypeRef = 
                new TypeReference<List<Map<String, Object>>>() {};
            List<Map<String, Object>> livros = objectMapper.readValue(jsonArray, listTypeRef);
            
            System.out.println("📚 Lista de livros brasileiros:");
            for (Map<String, Object> livro : livros) {
                System.out.println("   • " + livro.get("nome") + " - " + livro.get("autor") + " (" + livro.get("ano") + ")");
            }
            
            // Mapeamento para array de JsonNode
            JsonNode arrayNode = objectMapper.readTree(jsonArray);
            System.out.println("\n🔍 Análise com JsonNode:");
            System.out.println("   • Tipo: " + (arrayNode.isArray() ? "Array" : "Outro"));
            System.out.println("   • Elementos: " + arrayNode.size());
            
            // Encontrar o livro mais antigo
            int anoMaisAntigo = Integer.MAX_VALUE;
            String livroMaisAntigo = "";
            
            for (JsonNode livroNode : arrayNode) {
                int ano = livroNode.path("ano").asInt();
                if (ano < anoMaisAntigo) {
                    anoMaisAntigo = ano;
                    livroMaisAntigo = livroNode.path("nome").asText();
                }
            }
            
            System.out.println("   • Livro mais antigo: " + livroMaisAntigo + " (" + anoMaisAntigo + ")");
            
        } catch (Exception e) {
            System.out.println("❌ Erro no mapeamento de tipos: " + e.getMessage());
        }
        System.out.println();
    }
    
    private void demonstrarManipulacaoJson() {
        System.out.println("🔧 4. MANIPULAÇÃO E CRIAÇÃO DE JSON");
        System.out.println("===================================");
        
        try {
            // Criar JSON programaticamente
            ObjectNode livroNode = objectMapper.createObjectNode();
            livroNode.put("id", 9999);
            livroNode.put("title", "Livro Criado Programaticamente");
            livroNode.put("download_count", 0);
            
            // Criar array de autores
            ArrayNode autoresArray = objectMapper.createArrayNode();
            ObjectNode autorNode = objectMapper.createObjectNode();
            autorNode.put("name", "Autor Exemplo");
            autorNode.put("birth_year", 1900);
            autorNode.put("death_year", 1980);
            autoresArray.add(autorNode);
            
            livroNode.set("authors", autoresArray);
            
            // Criar objeto de formatos
            ObjectNode formatosNode = objectMapper.createObjectNode();
            formatosNode.put("text/plain", "https://exemplo.com/livro.txt");
            formatosNode.put("application/pdf", "https://exemplo.com/livro.pdf");
            livroNode.set("formats", formatosNode);
            
            System.out.println("📝 JSON criado programaticamente:");
            String jsonCriado = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(livroNode);
            System.out.println(jsonCriado);
            
            // Modificar JSON existente
            livroNode.put("download_count", 1500);
            livroNode.put("updated", true);
            
            System.out.println("\n🔄 JSON modificado:");
            System.out.println("   • Downloads atualizados: " + livroNode.path("download_count").asInt());
            System.out.println("   • Campo 'updated' adicionado: " + livroNode.path("updated").asBoolean());
            
        } catch (Exception e) {
            System.out.println("❌ Erro na manipulação JSON: " + e.getMessage());
        }
        System.out.println();
    }
    
    private void demonstrarCasosEspeciais() {
        System.out.println("🔧 5. CASOS ESPECIAIS E TRATAMENTO DE ERROS");
        System.out.println("===========================================");
        
        // JSON com propriedades faltantes
        String jsonIncompleto = """
            {
                "id": 123,
                "title": "Livro Incompleto",
                "authors": [],
                "download_count": null,
                "formats": {},
                "extra_field": "valor_desconhecido"
            }
            """;
        
        try {
            System.out.println("🧪 Testando JSON com propriedades faltantes/nulas:");
            JsonNode node = objectMapper.readTree(jsonIncompleto);
            
            System.out.println("   • ID: " + node.path("id").asInt());
            System.out.println("   • Título: " + node.path("title").asText());
            System.out.println("   • Autores (array vazio): " + node.path("authors").size());
            System.out.println("   • Downloads (null): " + (node.path("download_count").isNull() ? "null" : node.path("download_count").asInt()));
            System.out.println("   • Formatos (objeto vazio): " + node.path("formats").size());
            System.out.println("   • Campo extra: " + node.path("extra_field").asText());
            System.out.println("   • Campo inexistente: " + node.path("campo_inexistente").asText("valor_padrao"));
            
            System.out.println("\n✅ Tratamento robusto de propriedades opcionais!");
            
        } catch (Exception e) {
            System.out.println("❌ Erro no tratamento de casos especiais: " + e.getMessage());
        }
        
        // JSON malformado
        String jsonMalformado = "{ \"id\": 123, \"title\": \"Sem fechamento\"";
        
        try {
            System.out.println("\n🚨 Testando JSON malformado:");
            objectMapper.readTree(jsonMalformado);
        } catch (Exception e) {
            System.out.println("   ✅ Erro capturado corretamente: " + e.getClass().getSimpleName());
            System.out.println("   📝 Mensagem: " + e.getMessage().substring(0, Math.min(50, e.getMessage().length())) + "...");
        }
        System.out.println();
    }
    
    private void exemploApiGutendx() {
        System.out.println("🔧 6. EXEMPLO PRÁTICO - SIMULAÇÃO API GUTENDX");
        System.out.println("=============================================");
        
        // JSON simulando resposta real da API Gutendx
        String jsonGutendx = """
            {
                "count": 70000,
                "next": "https://gutendx.com/books/?page=2",
                "previous": null,
                "results": [
                    {
                        "id": 11,
                        "title": "Alice's Adventures in Wonderland",
                        "authors": [
                            {
                                "name": "Carroll, Lewis",
                                "birth_year": 1832,
                                "death_year": 1898
                            }
                        ],
                        "translators": [],
                        "subjects": [
                            "Alice (Fictitious character from Carroll) -- Juvenile fiction",
                            "Children's stories",
                            "Fantasy fiction"
                        ],
                        "bookshelves": [
                            "Children's Literature"
                        ],
                        "languages": ["en"],
                        "copyright": false,
                        "media_type": "Text",
                        "formats": {
                            "text/html": "https://www.gutenberg.org/ebooks/11.html.images",
                            "application/epub+zip": "https://www.gutenberg.org/ebooks/11.epub.images",
                            "application/x-mobipocket-ebook": "https://www.gutenberg.org/ebooks/11.kindle.images",
                            "application/rdf+xml": "https://www.gutenberg.org/ebooks/11.rdf",
                            "image/jpeg": "https://www.gutenberg.org/cache/epub/11/pg11.cover.medium.jpg",
                            "text/plain; charset=us-ascii": "https://www.gutenberg.org/files/11/11-0.txt",
                            "application/pdf": "https://www.gutenberg.org/files/11/11-pdf.pdf"
                        },
                        "download_count": 89432
                    }
                ]
            }
            """;
        
        try {
            System.out.println("📊 Análise completa da resposta da API Gutendx:");
            
            JsonNode root = objectMapper.readTree(jsonGutendx);
            
            // Informações gerais
            System.out.println("   • Total de livros na base: " + root.path("count").asInt());
            System.out.println("   • Tem próxima página: " + !root.path("next").isNull());
            System.out.println("   • Livros nesta página: " + root.path("results").size());
            
            // Análise do primeiro livro
            JsonNode livro = root.path("results").get(0);
            System.out.println("\n📖 Análise detalhada do livro:");
            System.out.println("   • ID: " + livro.path("id").asInt());
            System.out.println("   • Título: " + livro.path("title").asText());
            System.out.println("   • Downloads: " + livro.path("download_count").asInt());
            System.out.println("   • Idiomas: " + livro.path("languages").size());
            System.out.println("   • Copyright: " + livro.path("copyright").asBoolean());
            System.out.println("   • Tipo de mídia: " + livro.path("media_type").asText());
            
            // Análise dos autores
            JsonNode autores = livro.path("authors");
            System.out.println("   • Autores: " + autores.size());
            if (autores.size() > 0) {
                JsonNode autor = autores.get(0);
                System.out.println("     - Nome: " + autor.path("name").asText());
                System.out.println("     - Período: " + autor.path("birth_year").asInt() + 
                                 " - " + autor.path("death_year").asInt());
            }
            
            // Análise dos formatos
            JsonNode formatos = livro.path("formats");
            System.out.println("   • Formatos disponíveis: " + formatos.size());
            
            List<String> tiposFormato = new ArrayList<>();
            formatos.fieldNames().forEachRemaining(tiposFormato::add);
            
            System.out.println("     - HTML: " + (tiposFormato.stream().anyMatch(f -> f.contains("html")) ? "Sim" : "Não"));
            System.out.println("     - PDF: " + (tiposFormato.stream().anyMatch(f -> f.contains("pdf")) ? "Sim" : "Não"));
            System.out.println("     - EPUB: " + (tiposFormato.stream().anyMatch(f -> f.contains("epub")) ? "Sim" : "Não"));
            System.out.println("     - Kindle: " + (tiposFormato.stream().anyMatch(f -> f.contains("mobipocket")) ? "Sim" : "Não"));
            
            // Análise dos assuntos
            JsonNode assuntos = livro.path("subjects");
            System.out.println("   • Assuntos: " + assuntos.size());
            for (JsonNode assunto : assuntos) {
                System.out.println("     - " + assunto.asText());
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro na análise da API Gutendx: " + e.getMessage());
        }
        
        System.out.println("\n🎓 CONCLUSÃO DA FASE 4:");
        System.out.println("=======================");
        System.out.println("✅ Jackson 2.16 configurado e funcionando");
        System.out.println("✅ ObjectMapper com configurações robustas");
        System.out.println("✅ JsonNode para navegação flexível");
        System.out.println("✅ Mapeamento para diferentes tipos Java");
        System.out.println("✅ Criação e manipulação programática de JSON");
        System.out.println("✅ Tratamento de casos especiais e erros");
        System.out.println("✅ Análise completa de dados da API Gutendx");
        System.out.println("\n🚀 Pronto para integração com a API real!");
    }
}
