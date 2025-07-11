package com.exemplo.literatura.service;

import com.exemplo.literatura.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

/**
 * Serviço responsável pela interação com o usuário através de menu de linha de comando
 * Implementa CommandLineRunner para execução automática na inicialização
 */
@Service
public class MenuInterativoService {
    
    @Autowired
    private ConversaoDadosService conversaoService;
    
    @Autowired
    private GutendxHttpService gutendxService;
    
    private final Scanner scanner = new Scanner(System.in);
    private boolean executando = true;
    
    /**
     * Método principal que exibe o menu e gerencia a interação
     */
    public void exibirMenu() {
        exibirBoasVindas();
        
        while (executando) {
            try {
                exibirOpcoes();
                int opcao = lerOpcaoUsuario();
                processarOpcao(opcao);
            } catch (Exception e) {
                exibirErro("Erro inesperado: " + e.getMessage());
                pausar();
            }
        }
        
        exibirDespedida();
        scanner.close();
    }
    
    /**
     * Exibe mensagem de boas-vindas
     */
    private void exibirBoasVindas() {
        limparTela();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    📚 SISTEMA DE LITERATURA 📚                   ║");
        System.out.println("║                                                                  ║");
        System.out.println("║              Explore o mundo dos livros com dados               ║");
        System.out.println("║              do Project Gutenberg (70.000+ livros)              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        System.out.println();
        pausar();
    }
    
    /**
     * Exibe as opções do menu principal
     */
    private void exibirOpcoes() {
        limparTela();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                         📋 MENU PRINCIPAL                        ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════╣");
        System.out.println("║                                                                  ║");
        System.out.println("║  🔍 BUSCAR LIVROS:                                              ║");
        System.out.println("║     1 - Buscar livro por título                                 ║");
        System.out.println("║     2 - Buscar livros por autor                                 ║");
        System.out.println("║     3 - Buscar livros por idioma                                ║");
        System.out.println("║     4 - Buscar livros por assunto                               ║");
        System.out.println("║                                                                  ║");
        System.out.println("║  📊 ANÁLISES E ESTATÍSTICAS:                                    ║");
        System.out.println("║     5 - Livros mais populares                                   ║");
        System.out.println("║     6 - Autores por século                                      ║");
        System.out.println("║     7 - Estatísticas por idioma                                 ║");
        System.out.println("║     8 - Livros de domínio público                               ║");
        System.out.println("║                                                                  ║");
        System.out.println("║  🔧 FERRAMENTAS AVANÇADAS:                                      ║");
        System.out.println("║     9 - Análise detalhada de livro                              ║");
        System.out.println("║    10 - Comparar autores                                        ║");
        System.out.println("║    11 - Explorar formatos disponíveis                           ║");
        System.out.println("║    12 - Busca avançada (múltiplos critérios)                    ║");
        System.out.println("║                                                                  ║");
        System.out.println("║  ℹ️  INFORMAÇÕES:                                                ║");
        System.out.println("║    13 - Sobre o sistema                                         ║");
        System.out.println("║    14 - Ajuda e dicas                                           ║");
        System.out.println("║                                                                  ║");
        System.out.println("║     0 - Sair do sistema                                         ║");
        System.out.println("║                                                                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        System.out.print("\n👉 Digite sua opção: ");
    }
    
    /**
     * Lê e valida a opção escolhida pelo usuário
     */
    private int lerOpcaoUsuario() {
        try {
            String entrada = scanner.nextLine().trim();
            
            if (entrada.isEmpty()) {
                exibirErro("Por favor, digite uma opção válida.");
                pausar();
                return -1;
            }
            
            int opcao = Integer.parseInt(entrada);
            
            if (opcao < 0 || opcao > 14) {
                exibirErro("Opção inválida! Digite um número entre 0 e 14.");
                pausar();
                return -1;
            }
            
            return opcao;
            
        } catch (NumberFormatException e) {
            exibirErro("Por favor, digite apenas números.");
            pausar();
            return -1;
        }
    }
    
    /**
     * Processa a opção escolhida pelo usuário
     */
    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> buscarPorTitulo();
            case 2 -> buscarPorAutor();
            case 3 -> buscarPorIdioma();
            case 4 -> buscarPorAssunto();
            case 5 -> exibirMaisPopulares();
            case 6 -> analisarAutoresPorSeculo();
            case 7 -> estatisticasPorIdioma();
            case 8 -> livrosDominioPublico();
            case 9 -> analiseDetalhadaLivro();
            case 10 -> compararAutores();
            case 11 -> explorarFormatos();
            case 12 -> buscaAvancada();
            case 13 -> sobreSistema();
            case 14 -> exibirAjuda();
            case 0 -> {
                executando = false;
                return;
            }
            case -1 -> {
                // Opção inválida já tratada em lerOpcaoUsuario()
                return;
            }
            default -> {
                exibirErro("Opção não implementada ainda.");
                pausar();
            }
        }
    }
    
    // Métodos utilitários
    private void limparTela() {
        // Simula limpeza da tela
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    
    private void pausar() {
        System.out.print("\n⏸️  Pressione ENTER para continuar...");
        scanner.nextLine();
    }
    
    private void exibirErro(String mensagem) {
        System.out.println("\n❌ ERRO: " + mensagem);
    }
    
    private void exibirSucesso(String mensagem) {
        System.out.println("\n✅ " + mensagem);
    }
    
    private void exibirInfo(String mensagem) {
        System.out.println("\nℹ️  " + mensagem);
    }
    
    private void exibirDespedida() {
        limparTela();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    👋 OBRIGADO POR USAR O SISTEMA!               ║");
        System.out.println("║                                                                  ║");
        System.out.println("║              Continue explorando o mundo da literatura!         ║");
        System.out.println("║                     📚 Até a próxima! 📚                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
    }
    
    /**
     * Busca livros por título
     */
    private void buscarPorTitulo() {
        limparTela();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    🔍 BUSCAR POR TÍTULO                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        System.out.print("\n📖 Digite o título ou parte do título: ");
        String titulo = scanner.nextLine().trim();
        
        if (titulo.isEmpty()) {
            exibirErro("Título não pode estar vazio!");
            pausar();
            return;
        }
        
        try {
            exibirInfo("Buscando livros com título: \"" + titulo + "\"...");
            
            String jsonResposta = gutendxService.obterJsonResposta(titulo);
            GutendxResponseCompleta resposta = conversaoService.converterResposta(jsonResposta);
            
            if (resposta.estaVazia()) {
                exibirInfo("Nenhum livro encontrado com esse título.");
            } else {
                exibirResultadosBusca(resposta, "título \"" + titulo + "\"");
            }
            
        } catch (Exception e) {
            exibirErro("Erro ao buscar livros: " + e.getMessage());
        }
        
        pausar();
    }
    
    /**
     * Busca livros por autor
     */
    private void buscarPorAutor() {
        limparTela();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    👤 BUSCAR POR AUTOR                           ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        System.out.print("\n✍️  Digite o nome do autor: ");
        String autor = scanner.nextLine().trim();
        
        if (autor.isEmpty()) {
            exibirErro("Nome do autor não pode estar vazio!");
            pausar();
            return;
        }
        
        try {
            exibirInfo("Buscando livros do autor: \"" + autor + "\"...");
            
            String jsonResposta = gutendxService.obterJsonResposta(autor);
            GutendxResponseCompleta resposta = conversaoService.converterResposta(jsonResposta);
            
            if (resposta.estaVazia()) {
                exibirInfo("Nenhum livro encontrado para esse autor.");
                exibirInfo("💡 Dica: Tente variações do nome (ex: 'Shakespeare' ou 'William Shakespeare')");
            } else {
                exibirResultadosBusca(resposta, "autor \"" + autor + "\"");
                exibirAutoresEncontrados(resposta.getTodosAutores());
            }
            
        } catch (Exception e) {
            exibirErro("Erro ao buscar livros: " + e.getMessage());
        }
        
        pausar();
    }
    
    /**
     * Busca livros por idioma
     */
    private void buscarPorIdioma() {
        limparTela();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                   🌍 BUSCAR POR IDIOMA                           ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        System.out.println("\n📋 Idiomas mais comuns:");
        System.out.println("   • pt - Português");
        System.out.println("   • en - Inglês");
        System.out.println("   • fr - Francês");
        System.out.println("   • es - Espanhol");
        System.out.println("   • de - Alemão");
        System.out.println("   • it - Italiano");
        
        System.out.print("\n🗣️  Digite o código do idioma: ");
        String idioma = scanner.nextLine().trim().toLowerCase();
        
        if (idioma.isEmpty()) {
            exibirErro("Código do idioma não pode estar vazio!");
            pausar();
            return;
        }
        
        try {
            exibirInfo("Buscando livros em " + obterNomeIdioma(idioma) + "...");
            
            // Busca usando parâmetro de idioma da API
            String url = "?languages=" + idioma + "&page_size=10";
            String jsonResposta = gutendxService.obterJsonResposta(url);
            GutendxResponseCompleta resposta = conversaoService.converterResposta(jsonResposta);
            
            if (resposta.estaVazia()) {
                exibirInfo("Nenhum livro encontrado nesse idioma.");
            } else {
                exibirResultadosBusca(resposta, "idioma " + obterNomeIdioma(idioma));
            }
            
        } catch (Exception e) {
            exibirErro("Erro ao buscar livros: " + e.getMessage());
        }
        
        pausar();
    }
    
    /**
     * Busca livros por assunto
     */
    private void buscarPorAssunto() {
        limparTela();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                   📚 BUSCAR POR ASSUNTO                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        System.out.println("\n📋 Exemplos de assuntos:");
        System.out.println("   • Fiction (Ficção)");
        System.out.println("   • Poetry (Poesia)");
        System.out.println("   • Drama (Drama)");
        System.out.println("   • History (História)");
        System.out.println("   • Philosophy (Filosofia)");
        System.out.println("   • Science (Ciência)");
        
        System.out.print("\n🏷️  Digite o assunto (em inglês): ");
        String assunto = scanner.nextLine().trim();
        
        if (assunto.isEmpty()) {
            exibirErro("Assunto não pode estar vazio!");
            pausar();
            return;
        }
        
        try {
            exibirInfo("Buscando livros sobre: \"" + assunto + "\"...");
            
            String jsonResposta = gutendxService.obterJsonResposta(assunto);
            GutendxResponseCompleta resposta = conversaoService.converterResposta(jsonResposta);
            
            if (resposta.estaVazia()) {
                exibirInfo("Nenhum livro encontrado sobre esse assunto.");
                exibirInfo("💡 Dica: Tente termos em inglês ou palavras-chave mais gerais");
            } else {
                exibirResultadosBusca(resposta, "assunto \"" + assunto + "\"");
            }
            
        } catch (Exception e) {
            exibirErro("Erro ao buscar livros: " + e.getMessage());
        }
        
        pausar();
    }
    /**
     * Exibe os livros mais populares
     */
    private void exibirMaisPopulares() {
        limparTela();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                   🏆 LIVROS MAIS POPULARES                       ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        System.out.print("\n📊 Quantos livros deseja ver? (1-20): ");
        String entrada = scanner.nextLine().trim();
        
        int limite;
        try {
            limite = Integer.parseInt(entrada);
            if (limite < 1 || limite > 20) {
                exibirErro("Digite um número entre 1 e 20.");
                pausar();
                return;
            }
        } catch (NumberFormatException e) {
            exibirErro("Digite um número válido.");
            pausar();
            return;
        }
        
        try {
            exibirInfo("Buscando os " + limite + " livros mais populares...");
            
            // Busca geral ordenada por popularidade
            String jsonResposta = gutendxService.obterJsonResposta("?sort=popular&page_size=" + limite);
            GutendxResponseCompleta resposta = conversaoService.converterResposta(jsonResposta);
            
            if (resposta.estaVazia()) {
                exibirInfo("Não foi possível obter os livros mais populares.");
            } else {
                List<LivroCompletoDto> maisPopulares = resposta.getLivrosMaisPopulares(limite);
                exibirRankingPopularidade(maisPopulares);
            }
            
        } catch (Exception e) {
            exibirErro("Erro ao buscar livros populares: " + e.getMessage());
        }
        
        pausar();
    }
    
    /**
     * Analisa autores por século
     */
    private void analisarAutoresPorSeculo() {
        limparTela();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                  📅 AUTORES POR SÉCULO                           ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        System.out.print("\n🏛️  Digite o século (ex: 19 para século XIX): ");
        String entrada = scanner.nextLine().trim();
        
        int seculo;
        try {
            seculo = Integer.parseInt(entrada);
            if (seculo < 1 || seculo > 21) {
                exibirErro("Digite um século válido (1-21).");
                pausar();
                return;
            }
        } catch (NumberFormatException e) {
            exibirErro("Digite um número válido.");
            pausar();
            return;
        }
        
        try {
            int anoInicio = (seculo - 1) * 100 + 1;
            int anoFim = seculo * 100;
            
            exibirInfo("Buscando autores do século " + seculo + " (" + anoInicio + "-" + anoFim + ")...");
            
            // Busca por autores clássicos
            String jsonResposta = gutendxService.obterJsonResposta("classic literature");
            GutendxResponseCompleta resposta = conversaoService.converterResposta(jsonResposta);
            
            if (!resposta.estaVazia()) {
                List<AutorCompletoDto> autoresSeculo = resposta.getTodosAutores().stream()
                    .filter(autor -> autor.viveuNoPeriodo(anoInicio, anoFim))
                    .distinct()
                    .toList();
                
                exibirAutoresSeculo(autoresSeculo, seculo);
            } else {
                exibirInfo("Nenhum autor encontrado para esse século.");
            }
            
        } catch (Exception e) {
            exibirErro("Erro ao analisar autores: " + e.getMessage());
        }
        
        pausar();
    }
    
    /**
     * Exibe estatísticas por idioma
     */
    private void estatisticasPorIdioma() {
        limparTela();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                📊 ESTATÍSTICAS POR IDIOMA                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        try {
            exibirInfo("Coletando estatísticas de diferentes idiomas...");
            
            String[] idiomas = {"en", "pt", "fr", "es", "de", "it"};
            String[] nomes = {"Inglês", "Português", "Francês", "Espanhol", "Alemão", "Italiano"};
            
            System.out.println("\n📈 ESTATÍSTICAS POR IDIOMA:");
            System.out.println("═".repeat(60));
            
            for (int i = 0; i < idiomas.length; i++) {
                try {
                    String jsonResposta = gutendxService.obterJsonResposta("?languages=" + idiomas[i] + "&page_size=1");
                    GutendxResponseCompleta resposta = conversaoService.converterResposta(jsonResposta);
                    
                    System.out.printf("🌍 %-12s: %,8d livros%n", 
                        nomes[i], 
                        resposta.getTotalLivros() != null ? resposta.getTotalLivros() : 0);
                    
                    Thread.sleep(500); // Evita sobrecarga da API
                    
                } catch (Exception e) {
                    System.out.printf("🌍 %-12s: %8s (erro)%n", nomes[i], "N/A");
                }
            }
            
            exibirSucesso("Estatísticas coletadas com sucesso!");
            
        } catch (Exception e) {
            exibirErro("Erro ao coletar estatísticas: " + e.getMessage());
        }
        
        pausar();
    }
    
    /**
     * Exibe livros de domínio público
     */
    private void livrosDominioPublico() {
        limparTela();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                  🆓 LIVROS DE DOMÍNIO PÚBLICO                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        System.out.print("\n📚 Digite um termo para buscar (ou ENTER para busca geral): ");
        String termo = scanner.nextLine().trim();
        
        if (termo.isEmpty()) {
            termo = "public domain";
        }
        
        try {
            exibirInfo("Buscando livros de domínio público...");
            
            String jsonResposta = gutendxService.obterJsonResposta(termo);
            GutendxResponseCompleta resposta = conversaoService.converterResposta(jsonResposta);
            
            if (resposta.estaVazia()) {
                exibirInfo("Nenhum livro encontrado.");
            } else {
                List<LivroCompletoDto> dominioPublico = resposta.getLivrosDominioPublico();
                exibirLivrosDominioPublico(dominioPublico);
            }
            
        } catch (Exception e) {
            exibirErro("Erro ao buscar livros: " + e.getMessage());
        }
        
        pausar();
    }
    /**
     * Análise detalhada de um livro específico
     */
    private void analiseDetalhadaLivro() {
        limparTela();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                  🔍 ANÁLISE DETALHADA DE LIVRO                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        System.out.print("\n📖 Digite o título do livro para análise: ");
        String titulo = scanner.nextLine().trim();
        
        if (titulo.isEmpty()) {
            exibirErro("Título não pode estar vazio!");
            pausar();
            return;
        }
        
        try {
            exibirInfo("Analisando livro: \"" + titulo + "\"...");
            
            String jsonResposta = gutendxService.obterJsonResposta(titulo);
            GutendxResponseCompleta resposta = conversaoService.converterResposta(jsonResposta);
            
            if (resposta.estaVazia()) {
                exibirInfo("Livro não encontrado.");
            } else {
                LivroCompletoDto livro = resposta.getLivros().get(0);
                exibirAnaliseDetalhada(livro);
            }
            
        } catch (Exception e) {
            exibirErro("Erro na análise: " + e.getMessage());
        }
        
        pausar();
    }
    
    /**
     * Compara dois autores
     */
    private void compararAutores() {
        limparTela();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    ⚖️  COMPARAR AUTORES                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        System.out.print("\n👤 Digite o nome do primeiro autor: ");
        String autor1 = scanner.nextLine().trim();
        
        if (autor1.isEmpty()) {
            exibirErro("Nome do primeiro autor não pode estar vazio!");
            pausar();
            return;
        }
        
        System.out.print("👤 Digite o nome do segundo autor: ");
        String autor2 = scanner.nextLine().trim();
        
        if (autor2.isEmpty()) {
            exibirErro("Nome do segundo autor não pode estar vazio!");
            pausar();
            return;
        }
        
        try {
            exibirInfo("Comparando autores: \"" + autor1 + "\" vs \"" + autor2 + "\"...");
            
            // Buscar dados do primeiro autor
            String json1 = gutendxService.obterJsonResposta(autor1);
            GutendxResponseCompleta resposta1 = conversaoService.converterResposta(json1);
            
            // Buscar dados do segundo autor
            String json2 = gutendxService.obterJsonResposta(autor2);
            GutendxResponseCompleta resposta2 = conversaoService.converterResposta(json2);
            
            if (resposta1.estaVazia() || resposta2.estaVazia()) {
                exibirInfo("Um ou ambos os autores não foram encontrados.");
            } else {
                AutorCompletoDto autorObj1 = resposta1.getTodosAutores().get(0);
                AutorCompletoDto autorObj2 = resposta2.getTodosAutores().get(0);
                
                exibirComparacaoAutores(autorObj1, autorObj2, resposta1, resposta2);
            }
            
        } catch (Exception e) {
            exibirErro("Erro na comparação: " + e.getMessage());
        }
        
        pausar();
    }
    
    /**
     * Explora formatos disponíveis
     */
    private void explorarFormatos() {
        limparTela();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                 📁 EXPLORAR FORMATOS DISPONÍVEIS                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        System.out.print("\n📚 Digite um termo para buscar livros: ");
        String termo = scanner.nextLine().trim();
        
        if (termo.isEmpty()) {
            termo = "literature";
        }
        
        try {
            exibirInfo("Explorando formatos disponíveis para: \"" + termo + "\"...");
            
            String jsonResposta = gutendxService.obterJsonResposta(termo);
            GutendxResponseCompleta resposta = conversaoService.converterResposta(jsonResposta);
            
            if (resposta.estaVazia()) {
                exibirInfo("Nenhum livro encontrado.");
            } else {
                exibirAnaliseFormatos(resposta.getLivros());
            }
            
        } catch (Exception e) {
            exibirErro("Erro ao explorar formatos: " + e.getMessage());
        }
        
        pausar();
    }
    
    /**
     * Busca avançada com múltiplos critérios
     */
    private void buscaAvancada() {
        limparTela();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                   🔬 BUSCA AVANÇADA                              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        System.out.println("\n📋 Configure os critérios de busca:");
        
        System.out.print("📖 Título (opcional): ");
        String titulo = scanner.nextLine().trim();
        
        System.out.print("👤 Autor (opcional): ");
        String autor = scanner.nextLine().trim();
        
        System.out.print("🌍 Idioma (opcional, ex: pt, en): ");
        String idioma = scanner.nextLine().trim();
        
        System.out.print("📊 Mínimo de downloads (opcional): ");
        String minDownloads = scanner.nextLine().trim();
        
        if (titulo.isEmpty() && autor.isEmpty() && idioma.isEmpty()) {
            exibirErro("Pelo menos um critério deve ser preenchido!");
            pausar();
            return;
        }
        
        try {
            exibirInfo("Executando busca avançada...");
            
            // Construir termo de busca combinado
            StringBuilder termoBusca = new StringBuilder();
            if (!titulo.isEmpty()) termoBusca.append(titulo).append(" ");
            if (!autor.isEmpty()) termoBusca.append(autor).append(" ");
            
            String jsonResposta = gutendxService.obterJsonResposta(termoBusca.toString().trim());
            GutendxResponseCompleta resposta = conversaoService.converterResposta(jsonResposta);
            
            if (resposta.estaVazia()) {
                exibirInfo("Nenhum resultado encontrado com esses critérios.");
            } else {
                List<LivroCompletoDto> resultados = filtrarResultadosAvancados(
                    resposta.getLivros(), idioma, minDownloads);
                exibirResultadosBuscaAvancada(resultados, titulo, autor, idioma, minDownloads);
            }
            
        } catch (Exception e) {
            exibirErro("Erro na busca avançada: " + e.getMessage());
        }
        
        pausar();
    }
    /**
     * Exibe informações sobre o sistema
     */
    private void sobreSistema() {
        limparTela();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                     ℹ️  SOBRE O SISTEMA                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        System.out.println("\n📚 SISTEMA DE LITERATURA");
        System.out.println("═".repeat(50));
        System.out.println("🔹 Versão: 1.0.0");
        System.out.println("🔹 Desenvolvido com Spring Boot + Java 17");
        System.out.println("🔹 Integração com API Gutendx (Project Gutenberg)");
        System.out.println("🔹 Mais de 70.000 livros disponíveis");
        System.out.println("🔹 Conversão JSON com Jackson 2.16");
        System.out.println("🔹 Interface de linha de comando interativa");
        
        System.out.println("\n🌐 FONTE DOS DADOS:");
        System.out.println("═".repeat(50));
        System.out.println("🔹 Project Gutenberg (www.gutenberg.org)");
        System.out.println("🔹 API Gutendx (gutendx.com)");
        System.out.println("🔹 Livros de domínio público");
        System.out.println("🔹 Múltiplos idiomas e formatos");
        
        System.out.println("\n🛠️  TECNOLOGIAS UTILIZADAS:");
        System.out.println("═".repeat(50));
        System.out.println("🔹 Java 17 com HttpClient nativo");
        System.out.println("🔹 Spring Boot 3.x");
        System.out.println("🔹 Jackson 2.16 para JSON");
        System.out.println("🔹 Maven para gerenciamento de dependências");
        System.out.println("🔹 Padrões de design: DTO, Service, Controller");
        
        pausar();
    }
    
    /**
     * Exibe ajuda e dicas de uso
     */
    private void exibirAjuda() {
        limparTela();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                      🆘 AJUDA E DICAS                            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        System.out.println("\n💡 DICAS DE BUSCA:");
        System.out.println("═".repeat(50));
        System.out.println("🔹 Use palavras-chave em inglês para melhores resultados");
        System.out.println("🔹 Para autores, tente variações do nome (ex: 'Shakespeare', 'William Shakespeare')");
        System.out.println("🔹 Títulos podem ser buscados parcialmente");
        System.out.println("🔹 Use códigos de idioma: pt, en, fr, es, de, it");
        
        System.out.println("\n📋 CÓDIGOS DE IDIOMA:");
        System.out.println("═".repeat(50));
        System.out.println("🔹 pt - Português    🔹 en - Inglês");
        System.out.println("🔹 fr - Francês      🔹 es - Espanhol");
        System.out.println("🔹 de - Alemão       🔹 it - Italiano");
        
        System.out.println("\n🎯 EXEMPLOS DE BUSCA:");
        System.out.println("═".repeat(50));
        System.out.println("🔹 Títulos: 'Pride and Prejudice', 'Dom Casmurro'");
        System.out.println("🔹 Autores: 'Jane Austen', 'Machado de Assis'");
        System.out.println("🔹 Assuntos: 'Fiction', 'Poetry', 'Philosophy'");
        
        System.out.println("\n⚠️  LIMITAÇÕES:");
        System.out.println("═".repeat(50));
        System.out.println("🔹 Dados limitados pela API do Project Gutenberg");
        System.out.println("🔹 Alguns livros podem não ter todos os metadados");
        System.out.println("🔹 Busca depende de conexão com a internet");
        
        pausar();
    }
    
    // Métodos auxiliares para exibição de resultados
    
    /**
     * Exibe resultados de busca formatados
     */
    private void exibirResultadosBusca(GutendxResponseCompleta resposta, String criterio) {
        System.out.println("\n📊 RESULTADOS DA BUSCA POR " + criterio.toUpperCase());
        System.out.println("═".repeat(70));
        System.out.println("📈 Total encontrado: " + resposta.getTotalLivros());
        System.out.println("📋 Exibindo: " + resposta.getNumeroLivrosPagina() + " livro(s)");
        
        if (resposta.temProximaPagina()) {
            System.out.println("➡️  Há mais resultados disponíveis");
        }
        
        System.out.println("\n📚 LIVROS ENCONTRADOS:");
        System.out.println("─".repeat(70));
        
        int contador = 1;
        for (LivroCompletoDto livro : resposta.getLivros()) {
            System.out.printf("%2d. 📖 %s%n", contador++, livro.getTitulo());
            System.out.printf("    👤 %s%n", livro.getNomesAutores());
            System.out.printf("    🌍 %s | 📥 %,d downloads%n", 
                livro.getIdiomaPrincipal().toUpperCase(), 
                livro.getNumeroDownloads() != null ? livro.getNumeroDownloads() : 0);
            
            if (livro.temFormato("pdf")) {
                System.out.println("    📄 PDF disponível");
            }
            if (livro.isDominioPublico()) {
                System.out.println("    🆓 Domínio público");
            }
            System.out.println();
        }
    }
    
    /**
     * Exibe autores encontrados
     */
    private void exibirAutoresEncontrados(List<AutorCompletoDto> autores) {
        if (autores.isEmpty()) return;
        
        System.out.println("\n👥 AUTORES ENCONTRADOS:");
        System.out.println("─".repeat(50));
        
        autores.stream()
            .distinct()
            .limit(10)
            .forEach(autor -> {
                System.out.printf("👤 %s (%s)%n", 
                    autor.getNomeFormatado(), 
                    autor.getPeriodoVida());
                if (autor.eAutorClassico()) {
                    System.out.println("   🏛️  Autor clássico");
                }
                System.out.println();
            });
    }
    
    /**
     * Exibe ranking de popularidade
     */
    private void exibirRankingPopularidade(List<LivroCompletoDto> livros) {
        System.out.println("\n🏆 RANKING DE POPULARIDADE:");
        System.out.println("═".repeat(70));
        
        int posicao = 1;
        for (LivroCompletoDto livro : livros) {
            String medalha = switch (posicao) {
                case 1 -> "🥇";
                case 2 -> "🥈";
                case 3 -> "🥉";
                default -> String.format("%2d.", posicao);
            };
            
            System.out.printf("%s %-50s %,8d downloads%n",
                medalha,
                truncarTexto(livro.getTitulo(), 45),
                livro.getNumeroDownloads() != null ? livro.getNumeroDownloads() : 0);
            System.out.printf("    👤 %s%n", truncarTexto(livro.getNomesAutores(), 60));
            System.out.println();
            posicao++;
        }
    }
    
    /**
     * Obtém nome do idioma por código
     */
    private String obterNomeIdioma(String codigo) {
        return switch (codigo.toLowerCase()) {
            case "pt" -> "Português";
            case "en" -> "Inglês";
            case "fr" -> "Francês";
            case "es" -> "Espanhol";
            case "de" -> "Alemão";
            case "it" -> "Italiano";
            default -> codigo.toUpperCase();
        };
    }
    
    
    /**
     * Exibe autores de um século específico
     */
    private void exibirAutoresSeculo(List<AutorCompletoDto> autores, int seculo) {
        System.out.println("\n👥 AUTORES DO SÉCULO " + seculo + ":");
        System.out.println("═".repeat(60));
        
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado para esse século.");
            return;
        }
        
        autores.forEach(autor -> {
            System.out.printf("👤 %s%n", autor.getNomeFormatado());
            System.out.printf("   📅 %s%n", autor.getPeriodoVida());
            System.out.printf("   🎂 Viveu %d anos%n", autor.getIdadeVivida());
            System.out.println();
        });
    }
    
    /**
     * Exibe livros de domínio público
     */
    private void exibirLivrosDominioPublico(List<LivroCompletoDto> livros) {
        System.out.println("\n🆓 LIVROS DE DOMÍNIO PÚBLICO:");
        System.out.println("═".repeat(60));
        
        livros.forEach(livro -> {
            System.out.printf("📚 %s%n", livro.getTitulo());
            System.out.printf("   👤 %s%n", livro.getNomesAutores());
            System.out.printf("   📥 %,d downloads%n", 
                livro.getNumeroDownloads() != null ? livro.getNumeroDownloads() : 0);
            System.out.println();
        });
    }
    
    /**
     * Exibe análise detalhada de um livro
     */
    private void exibirAnaliseDetalhada(LivroCompletoDto livro) {
        System.out.println("\n📖 ANÁLISE DETALHADA DO LIVRO");
        System.out.println("═".repeat(70));
        
        System.out.println("📋 INFORMAÇÕES BÁSICAS:");
        System.out.println("─".repeat(30));
        System.out.printf("🆔 ID: %d%n", livro.getId());
        System.out.printf("📖 Título: %s%n", livro.getTitulo());
        System.out.printf("👤 Autor(es): %s%n", livro.getNomesAutores());
        System.out.printf("🌍 Idioma: %s%n", livro.getIdiomaPrincipal());
        System.out.printf("📥 Downloads: %,d%n", 
            livro.getNumeroDownloads() != null ? livro.getNumeroDownloads() : 0);
        System.out.printf("🆓 Domínio público: %s%n", livro.isDominioPublico() ? "Sim" : "Não");
        
        if (livro.getPrimeiroAutor() != null) {
            AutorCompletoDto autor = livro.getPrimeiroAutor();
            System.out.println("\n👤 INFORMAÇÕES DO AUTOR:");
            System.out.println("─".repeat(30));
            System.out.printf("📝 Nome completo: %s%n", autor.getNomeFormatado());
            System.out.printf("📅 Período: %s%n", autor.getPeriodoVida());
            System.out.printf("🎂 Idade: %d anos%n", autor.getIdadeVivida());
            System.out.printf("🏛️  Século: %s%n", autor.getSeculoNascimento());
            System.out.printf("⭐ Clássico: %s%n", autor.eAutorClassico() ? "Sim" : "Não");
        }
        
        if (livro.getFormatos() != null && !livro.getFormatos().isEmpty()) {
            System.out.println("\n📁 FORMATOS DISPONÍVEIS:");
            System.out.println("─".repeat(30));
            livro.getFormatos().forEach((formato, url) -> {
                String icone;
                String formatoLower = formato.toLowerCase();
                if (formatoLower.contains("pdf")) {
                    icone = "📄";
                } else if (formatoLower.contains("epub")) {
                    icone = "📱";
                } else if (formatoLower.contains("html")) {
                    icone = "🌐";
                } else if (formatoLower.contains("txt")) {
                    icone = "📝";
                } else {
                    icone = "📎";
                }
                System.out.printf("%s %s%n", icone, formato);
            });
        }
        
        if (livro.getAssuntos() != null && !livro.getAssuntos().isEmpty()) {
            System.out.println("\n🏷️  ASSUNTOS:");
            System.out.println("─".repeat(30));
            livro.getAssuntos().stream()
                .limit(5)
                .forEach(assunto -> System.out.println("• " + assunto));
        }
    }
    
    /**
     * Exibe comparação entre autores
     */
    private void exibirComparacaoAutores(AutorCompletoDto autor1, AutorCompletoDto autor2, 
                                        GutendxResponseCompleta resposta1, GutendxResponseCompleta resposta2) {
        System.out.println("\n⚖️  COMPARAÇÃO DE AUTORES");
        System.out.println("═".repeat(70));
        
        System.out.printf("%-35s | %-35s%n", autor1.getNomeFormatado(), autor2.getNomeFormatado());
        System.out.println("─".repeat(70));
        
        System.out.printf("📅 %-33s | %-35s%n", 
            autor1.getPeriodoVida(), autor2.getPeriodoVida());
        
        System.out.printf("🎂 %-33s | %-35s%n", 
            autor1.getIdadeVivida() + " anos", autor2.getIdadeVivida() + " anos");
        
        System.out.printf("🏛️  %-33s | %-35s%n", 
            autor1.getSeculoNascimento(), autor2.getSeculoNascimento());
        
        System.out.printf("⭐ %-33s | %-35s%n", 
            autor1.eAutorClassico() ? "Autor clássico" : "Autor moderno",
            autor2.eAutorClassico() ? "Autor clássico" : "Autor moderno");
        
        System.out.printf("📚 %-33s | %-35s%n", 
            resposta1.getNumeroLivrosPagina() + " livro(s) encontrado(s)",
            resposta2.getNumeroLivrosPagina() + " livro(s) encontrado(s)");
    }
    
    /**
     * Exibe análise de formatos
     */
    private void exibirAnaliseFormatos(List<LivroCompletoDto> livros) {
        System.out.println("\n📁 ANÁLISE DE FORMATOS DISPONÍVEIS");
        System.out.println("═".repeat(60));
        
        long comPdf = livros.stream().filter(l -> l.temFormato("pdf")).count();
        long comEpub = livros.stream().filter(l -> l.temFormato("epub")).count();
        long comHtml = livros.stream().filter(l -> l.temFormato("html")).count();
        long comTxt = livros.stream().filter(l -> l.temFormato("txt")).count();
        
        System.out.printf("📄 PDF:  %3d livros (%.1f%%)%n", comPdf, (comPdf * 100.0) / livros.size());
        System.out.printf("📱 EPUB: %3d livros (%.1f%%)%n", comEpub, (comEpub * 100.0) / livros.size());
        System.out.printf("🌐 HTML: %3d livros (%.1f%%)%n", comHtml, (comHtml * 100.0) / livros.size());
        System.out.printf("📝 TXT:  %3d livros (%.1f%%)%n", comTxt, (comTxt * 100.0) / livros.size());
        
        System.out.println("\n📊 DETALHES POR LIVRO:");
        System.out.println("─".repeat(60));
        
        livros.stream().limit(10).forEach(livro -> {
            System.out.printf("📚 %s%n", truncarTexto(livro.getTitulo(), 50));
            System.out.printf("   Formatos: %d | ", 
                livro.getFormatos() != null ? livro.getFormatos().size() : 0);
            
            if (livro.temFormato("pdf")) System.out.print("📄 ");
            if (livro.temFormato("epub")) System.out.print("📱 ");
            if (livro.temFormato("html")) System.out.print("🌐 ");
            if (livro.temFormato("txt")) System.out.print("📝 ");
            
            System.out.println();
        });
    }
    
    /**
     * Filtra resultados da busca avançada
     */
    private List<LivroCompletoDto> filtrarResultadosAvancados(List<LivroCompletoDto> livros, 
                                                             String idioma, String minDownloads) {
        return livros.stream()
            .filter(livro -> {
                // Filtro por idioma
                if (!idioma.isEmpty() && !livro.estaEmIdioma(idioma)) {
                    return false;
                }
                
                // Filtro por downloads mínimos
                if (!minDownloads.isEmpty()) {
                    try {
                        int min = Integer.parseInt(minDownloads);
                        Integer downloads = livro.getNumeroDownloads();
                        if (downloads == null || downloads < min) {
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        // Ignora filtro se não for número válido
                    }
                }
                
                return true;
            })
            .toList();
    }
    
    /**
     * Exibe resultados da busca avançada
     */
    private void exibirResultadosBuscaAvancada(List<LivroCompletoDto> resultados, 
                                              String titulo, String autor, String idioma, String minDownloads) {
        System.out.println("\n🔬 RESULTADOS DA BUSCA AVANÇADA");
        System.out.println("═".repeat(60));
        
        System.out.println("📋 CRITÉRIOS APLICADOS:");
        if (!titulo.isEmpty()) System.out.println("   📖 Título: " + titulo);
        if (!autor.isEmpty()) System.out.println("   👤 Autor: " + autor);
        if (!idioma.isEmpty()) System.out.println("   🌍 Idioma: " + obterNomeIdioma(idioma));
        if (!minDownloads.isEmpty()) System.out.println("   📥 Min. downloads: " + minDownloads);
        
        System.out.println("\n📊 RESULTADOS: " + resultados.size() + " livro(s) encontrado(s)");
        
        if (resultados.isEmpty()) {
            System.out.println("💡 Tente relaxar alguns critérios para obter mais resultados.");
            return;
        }
        
        System.out.println("─".repeat(60));
        
        resultados.stream().limit(10).forEach(livro -> {
            System.out.printf("📚 %s%n", livro.getTitulo());
            System.out.printf("   👤 %s%n", livro.getNomesAutores());
            System.out.printf("   🌍 %s | 📥 %,d downloads%n", 
                livro.getIdiomaPrincipal().toUpperCase(),
                livro.getNumeroDownloads() != null ? livro.getNumeroDownloads() : 0);
            System.out.println();
        });
        
        if (resultados.size() > 10) {
            System.out.println("... e mais " + (resultados.size() - 10) + " resultado(s)");
        }
    }
    
    /**
     * Trunca texto para exibição
     */
    private String truncarTexto(String texto, int limite) {
        if (texto == null) return "N/A";
        return texto.length() > limite ? texto.substring(0, limite - 3) + "..." : texto;
    }
}
