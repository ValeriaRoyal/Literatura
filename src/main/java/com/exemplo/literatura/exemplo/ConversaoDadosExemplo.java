package com.exemplo.literatura.exemplo;

import com.exemplo.literatura.dto.*;
import com.exemplo.literatura.service.ConversaoDadosService;

import java.util.List;

/**
 * Exemplo prático demonstrando a conversão de dados JSON para classes Java
 * usando as anotações @JsonIgnoreProperties e @JsonAlias
 */
public class ConversaoDadosExemplo {
    
    private final ConversaoDadosService conversaoService;
    
    public ConversaoDadosExemplo() {
        this.conversaoService = new ConversaoDadosService();
    }
    
    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("🔄 CONVERSÃO DE DADOS JSON PARA CLASSES JAVA");
        System.out.println("======================================================================\n");
        
        ConversaoDadosExemplo exemplo = new ConversaoDadosExemplo();
        
        // 1. Demonstrar conversão de resposta completa
        exemplo.demonstrarConversaoResposta();
        
        // 2. Demonstrar conversão de livro individual
        exemplo.demonstrarConversaoLivro();
        
        // 3. Demonstrar conversão de autor individual
        exemplo.demonstrarConversaoAutor();
        
        // 4. Demonstrar métodos específicos dos DTOs
        exemplo.demonstrarMetodosEspecificos();
        
        // 5. Demonstrar conversões entre tipos
        exemplo.demonstrarConversoesTipos();
        
        // 6. Demonstrar tratamento de casos especiais
        exemplo.demonstrarCasosEspeciais();
    }
    
    private void demonstrarConversaoResposta() {
        System.out.println("🔧 1. CONVERSÃO DE RESPOSTA COMPLETA DA API");
        System.out.println("==========================================");
        
        // JSON simulando resposta real da API Gutendx
        String jsonResposta = """
            {
                "count": 1234,
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
                        "translators": [],
                        "subjects": ["Drama", "Tragedy"],
                        "bookshelves": ["Plays"],
                        "languages": ["en"],
                        "copyright": false,
                        "media_type": "Text",
                        "formats": {
                            "text/html": "https://www.gutenberg.org/files/1513/1513-h/1513-h.htm",
                            "application/pdf": "https://www.gutenberg.org/files/1513/1513-pdf.pdf",
                            "text/plain": "https://www.gutenberg.org/files/1513/1513-0.txt"
                        },
                        "download_count": 25432
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
                        "copyright": false,
                        "media_type": "Text",
                        "formats": {
                            "text/html": "https://www.gutenberg.org/files/1524/1524-h/1524-h.htm"
                        },
                        "download_count": 31245
                    }
                ]
            }
            """;
        
        try {
            System.out.println("📡 Convertendo JSON da API para GutendxResponseCompleta...");
            
            GutendxResponseCompleta resposta = conversaoService.converterResposta(jsonResposta);
            
            System.out.println("✅ Conversão bem-sucedida!");
            System.out.println("📊 Informações da resposta:");
            System.out.println("   • Total de livros: " + resposta.getTotalLivros());
            System.out.println("   • Livros nesta página: " + resposta.getNumeroLivrosPagina());
            System.out.println("   • Tem próxima página: " + resposta.temProximaPagina());
            System.out.println("   • Idiomas únicos: " + resposta.getTodosIdiomas());
            System.out.println("   • Autores únicos: " + resposta.getTodosAutores().size());
            
            System.out.println("\n📈 Estatísticas: " + resposta.getEstatisticas());
            System.out.println("📝 Resumo: " + resposta.getResumo());
            
        } catch (Exception e) {
            System.out.println("❌ Erro na conversão: " + e.getMessage());
        }
        System.out.println();
    }
    
    private void demonstrarConversaoLivro() {
        System.out.println("🔧 2. CONVERSÃO DE LIVRO INDIVIDUAL");
        System.out.println("===================================");
        
        // JSON de um livro com propriedades alternativas (testando @JsonAlias)
        String jsonLivro = """
            {
                "book_id": 11,
                "book_title": "Alice's Adventures in Wonderland",
                "book_authors": [
                    {
                        "author_name": "Carroll, Lewis",
                        "born": 1832,
                        "died": 1898
                    }
                ],
                "book_subjects": [
                    "Children's stories",
                    "Fantasy fiction",
                    "Alice (Fictitious character from Carroll) -- Juvenile fiction"
                ],
                "shelves": ["Children's Literature"],
                "book_languages": ["en"],
                "has_copyright": false,
                "content_type": "Text",
                "download_links": {
                    "text/html": "https://www.gutenberg.org/ebooks/11.html.images",
                    "application/epub+zip": "https://www.gutenberg.org/ebooks/11.epub.images",
                    "application/pdf": "https://www.gutenberg.org/files/11/11-pdf.pdf",
                    "text/plain; charset=us-ascii": "https://www.gutenberg.org/files/11/11-0.txt"
                },
                "downloads": 89432
            }
            """;
        
        try {
            System.out.println("📖 Convertendo JSON com propriedades alternativas (@JsonAlias)...");
            
            LivroCompletoDto livro = conversaoService.converterLivro(jsonLivro);
            
            System.out.println("✅ Conversão bem-sucedida com @JsonAlias!");
            System.out.println("📚 Informações do livro:");
            System.out.println("   • ID: " + livro.getId());
            System.out.println("   • Título: " + livro.getTitulo());
            System.out.println("   • Primeiro autor: " + livro.getPrimeiroAutor().getNomeFormatado());
            System.out.println("   • Idioma principal: " + livro.getIdiomaPrincipal());
            System.out.println("   • Downloads: " + livro.getNumeroDownloads());
            System.out.println("   • Domínio público: " + livro.isDominioPublico());
            
            System.out.println("\n🔍 Verificações específicas:");
            System.out.println("   • Tem formato PDF: " + livro.temFormato("pdf"));
            System.out.println("   • Tem formato EPUB: " + livro.temFormato("epub"));
            System.out.println("   • URL do PDF: " + livro.getUrlFormato("pdf"));
            System.out.println("   • Está em inglês: " + livro.estaEmIdioma("en"));
            
            System.out.println("\n📊 Resumo: " + livro.getResumo());
            System.out.println("📈 Estatísticas: " + livro.getEstatisticas());
            
        } catch (Exception e) {
            System.out.println("❌ Erro na conversão: " + e.getMessage());
        }
        System.out.println();
    }
    
    private void demonstrarConversaoAutor() {
        System.out.println("🔧 3. CONVERSÃO DE AUTOR INDIVIDUAL");
        System.out.println("===================================");
        
        // JSON de um autor com propriedades alternativas
        String jsonAutor = """
            {
                "full_name": "Machado de Assis",
                "year_born": 1839,
                "year_died": 1908
            }
            """;
        
        try {
            System.out.println("👤 Convertendo JSON de autor com @JsonAlias...");
            
            AutorCompletoDto autor = conversaoService.converterAutor(jsonAutor);
            
            System.out.println("✅ Conversão bem-sucedida!");
            System.out.println("📝 Informações do autor:");
            System.out.println("   • Nome original: " + autor.getNome());
            System.out.println("   • Nome formatado: " + autor.getNomeFormatado());
            System.out.println("   • Primeiro nome: " + autor.getPrimeiroNome());
            System.out.println("   • Sobrenome: " + autor.getSobrenome());
            System.out.println("   • Período de vida: " + autor.getPeriodoVida());
            System.out.println("   • Idade vivida: " + autor.getIdadeVivida() + " anos");
            System.out.println("   • Século de nascimento: " + autor.getSeculoNascimento());
            System.out.println("   • Está vivo: " + autor.estaVivo());
            System.out.println("   • É autor clássico: " + autor.eAutorClassico());
            
            System.out.println("\n🔍 Verificações específicas:");
            System.out.println("   • Viveu no século XIX: " + autor.viveuNoPeriodo(1801, 1900));
            System.out.println("   • Viveu no século XX: " + autor.viveuNoPeriodo(1901, 2000));
            
            System.out.println("\n📖 Descrição completa: " + autor.getDescricaoCompleta());
            
        } catch (Exception e) {
            System.out.println("❌ Erro na conversão: " + e.getMessage());
        }
        System.out.println();
    }
    
    private void demonstrarMetodosEspecificos() {
        System.out.println("🔧 4. MÉTODOS ESPECÍFICOS DOS DTOs");
        System.out.println("==================================");
        
        // Criar objetos para demonstração
        AutorCompletoDto shakespeare = new AutorCompletoDto("Shakespeare, William", 1564, 1616);
        AutorCompletoDto machado = new AutorCompletoDto("Machado de Assis", 1839, 1908);
        
        LivroCompletoDto livro = new LivroCompletoDto(1L, "Dom Casmurro");
        livro.setAutores(List.of(machado));
        livro.setIdiomas(List.of("pt"));
        livro.setNumeroDownloads(15000);
        livro.setTemCopyright(false);
        
        System.out.println("📚 Demonstrando métodos específicos:");
        
        System.out.println("\n👤 Métodos do AutorCompletoDto:");
        System.out.println("   • Shakespeare formatado: " + shakespeare.getNomeFormatado());
        System.out.println("   • Shakespeare viveu: " + shakespeare.getIdadeVivida() + " anos");
        System.out.println("   • Shakespeare é clássico: " + shakespeare.eAutorClassico());
        System.out.println("   • Machado período: " + machado.getPeriodoVida());
        System.out.println("   • Machado século: " + machado.getSeculoNascimento());
        
        System.out.println("\n📖 Métodos do LivroCompletoDto:");
        System.out.println("   • Primeiro autor: " + livro.getPrimeiroAutor().getNomeFormatado());
        System.out.println("   • Nomes dos autores: " + livro.getNomesAutores());
        System.out.println("   • Idioma principal: " + livro.getIdiomaPrincipal());
        System.out.println("   • É domínio público: " + livro.isDominioPublico());
        System.out.println("   • Resumo: " + livro.getResumo());
        
        System.out.println("\n🔄 Conversões para simplificados:");
        LivroSimplificadoDto livroSimples = livro.paraSimplificado();
        AutorSimplificadoDto autorSimples = shakespeare.paraSimplificado();
        
        System.out.println("   • Livro simplificado: " + livroSimples.getResumoUmaLinha());
        System.out.println("   • Autor simplificado: " + autorSimples.getResumoUmaLinha());
        System.out.println();
    }
    
    private void demonstrarConversoesTipos() {
        System.out.println("🔧 5. CONVERSÕES ENTRE TIPOS");
        System.out.println("============================");
        
        // Criar uma resposta com múltiplos livros
        String jsonMultiplosLivros = """
            {
                "count": 3,
                "results": [
                    {
                        "id": 1,
                        "title": "Livro 1",
                        "authors": [{"name": "Autor 1", "birth_year": 1800, "death_year": 1850}],
                        "languages": ["pt"],
                        "download_count": 1000
                    },
                    {
                        "id": 2,
                        "title": "Livro 2",
                        "authors": [{"name": "Autor 2", "birth_year": 1900, "death_year": 1980}],
                        "languages": ["en"],
                        "download_count": 2000
                    },
                    {
                        "id": 3,
                        "title": "Livro 3",
                        "authors": [{"name": "Autor 3", "birth_year": 1950}],
                        "languages": ["fr"],
                        "download_count": 3000
                    }
                ]
            }
            """;
        
        try {
            System.out.println("🔄 Convertendo e manipulando múltiplos livros...");
            
            GutendxResponseCompleta resposta = conversaoService.converterResposta(jsonMultiplosLivros);
            
            System.out.println("✅ Conversão realizada!");
            System.out.println("📊 Análises da coleção:");
            
            // Filtros e análises
            List<LivroCompletoDto> livrosPortugues = resposta.getLivrosPorIdioma("pt");
            List<LivroCompletoDto> livrosDominioPublico = resposta.getLivrosDominioPublico();
            List<LivroCompletoDto> maisPopulares = resposta.getLivrosMaisPopulares(2);
            
            System.out.println("   • Livros em português: " + livrosPortugues.size());
            System.out.println("   • Livros domínio público: " + livrosDominioPublico.size());
            System.out.println("   • Top 2 mais populares: " + maisPopulares.size());
            
            // Conversões para simplificados
            List<LivroSimplificadoDto> livrosSimples = conversaoService.converterParaSimplificados(resposta.getLivros());
            List<AutorCompletoDto> todosAutores = resposta.getTodosAutores();
            List<AutorSimplificadoDto> autoresSimples = conversaoService.converterAutoresParaSimplificados(todosAutores);
            
            System.out.println("   • Livros simplificados: " + livrosSimples.size());
            System.out.println("   • Autores únicos: " + todosAutores.size());
            System.out.println("   • Autores simplificados: " + autoresSimples.size());
            
            System.out.println("\n📝 Livros mais populares:");
            maisPopulares.forEach(livro -> 
                System.out.println("   • " + livro.getTitulo() + " - " + livro.getNumeroDownloads() + " downloads"));
            
        } catch (Exception e) {
            System.out.println("❌ Erro nas conversões: " + e.getMessage());
        }
        System.out.println();
    }
    
    private void demonstrarCasosEspeciais() {
        System.out.println("🔧 6. TRATAMENTO DE CASOS ESPECIAIS");
        System.out.println("===================================");
        
        // JSON com propriedades faltantes e valores nulos
        String jsonIncompleto = """
            {
                "id": 999,
                "title": "Livro Incompleto",
                "authors": [],
                "languages": null,
                "download_count": null,
                "copyright": null,
                "formats": {},
                "unknown_property": "valor_ignorado"
            }
            """;
        
        try {
            System.out.println("🧪 Testando JSON com propriedades faltantes (@JsonIgnoreProperties)...");
            
            LivroCompletoDto livro = conversaoService.converterLivro(jsonIncompleto);
            
            System.out.println("✅ Conversão bem-sucedida mesmo com dados incompletos!");
            System.out.println("📋 Tratamento de valores nulos/vazios:");
            System.out.println("   • ID: " + livro.getId());
            System.out.println("   • Título: " + livro.getTitulo());
            System.out.println("   • Autores: " + (livro.getAutores() != null ? livro.getAutores().size() : "null"));
            System.out.println("   • Idiomas: " + livro.getIdiomas());
            System.out.println("   • Downloads: " + livro.getNumeroDownloads());
            System.out.println("   • Copyright: " + livro.getTemCopyright());
            System.out.println("   • Formatos: " + (livro.getFormatos() != null ? livro.getFormatos().size() : "null"));
            
            System.out.println("\n🔍 Métodos com tratamento de nulos:");
            System.out.println("   • Nomes autores: " + livro.getNomesAutores());
            System.out.println("   • Idioma principal: " + livro.getIdiomaPrincipal());
            System.out.println("   • É domínio público: " + livro.isDominioPublico());
            System.out.println("   • Resumo: " + livro.getResumo());
            
        } catch (Exception e) {
            System.out.println("❌ Erro no tratamento de casos especiais: " + e.getMessage());
        }
        
        // Teste de JSON inválido
        String jsonInvalido = "{ \"id\": 123, \"title\": \"Sem fechamento\"";
        
        System.out.println("\n🚨 Testando JSON inválido:");
        boolean valido = conversaoService.isJsonValido(jsonInvalido);
        System.out.println("   • JSON válido: " + valido);
        
        if (!valido) {
            System.out.println("   ✅ Validação funcionando corretamente!");
        }
        
        System.out.println("\n🎓 RESUMO DA CONVERSÃO DE DADOS:");
        System.out.println("===============================");
        System.out.println("✅ @JsonIgnoreProperties - ignora propriedades desconhecidas");
        System.out.println("✅ @JsonAlias - mapeia propriedades com nomes alternativos");
        System.out.println("✅ Getters/Setters - acesso controlado aos dados");
        System.out.println("✅ toString() - representação textual dos objetos");
        System.out.println("✅ Métodos específicos - manipulação inteligente dos dados");
        System.out.println("✅ Conversões entre tipos - flexibilidade na apresentação");
        System.out.println("✅ Tratamento robusto - funciona mesmo com dados incompletos");
        System.out.println("\n🚀 Dados JSON convertidos com sucesso para classes Java!");
    }
}
