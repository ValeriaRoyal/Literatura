package com.exemplo.literatura.service;

import com.exemplo.literatura.model.Autor;
import com.exemplo.literatura.model.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

/**
 * Serviço de menu simplificado para demonstrar as funcionalidades do catálogo
 * Inclui as 5 funcionalidades obrigatórias
 */
@Service
public class MenuCatalogoService {
    
    @Autowired
    private CatalogoService catalogoService;
    
    private final Scanner scanner = new Scanner(System.in);
    
    /**
     * Exibe menu principal e processa opções
     */
    public void exibirMenuPrincipal() {
        boolean executando = true;
        
        exibirBoasVindas();
        
        while (executando) {
            try {
                exibirOpcoes();
                int opcao = lerOpcao();
                
                switch (opcao) {
                    case 1 -> buscarEAdicionarLivro();
                    case 2 -> listarTodosLivros();
                    case 3 -> listarLivrosPorIdioma();
                    case 4 -> listarTodosAutores();
                    case 5 -> listarAutoresVivosNoAno();
                    case 6 -> exibirEstatisticas();
                    case 0 -> {
                        executando = false;
                        exibirDespedida();
                    }
                    default -> System.out.println("❌ Opção inválida!");
                }
                
                if (executando && opcao != -1) {
                    pausar();
                }
                
            } catch (Exception e) {
                System.out.println("❌ Erro: " + e.getMessage());
                pausar();
            }
        }
    }
    
    private void exibirBoasVindas() {
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    📚 CATÁLOGO DE LITERATURA                     ║");
        System.out.println("║                                                                  ║");
        System.out.println("║              Funcionalidades Obrigatórias Implementadas         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝\n");
    }
    
    private void exibirOpcoes() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                         📋 MENU PRINCIPAL                        ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════╣");
        System.out.println("║                                                                  ║");
        System.out.println("║  📚 FUNCIONALIDADES OBRIGATÓRIAS DE LIVROS:                     ║");
        System.out.println("║     1 - Buscar livro por título e adicionar ao catálogo         ║");
        System.out.println("║     2 - Listar todos os livros                                  ║");
        System.out.println("║     3 - Listar livros por idioma                                ║");
        System.out.println("║                                                                  ║");
        System.out.println("║  👥 FUNCIONALIDADES OBRIGATÓRIAS DE AUTORES:                    ║");
        System.out.println("║     4 - Lista de autores                                        ║");
        System.out.println("║     5 - Listar autores vivos em determinado ano                 ║");
        System.out.println("║                                                                  ║");
        System.out.println("║  📊 EXTRAS:                                                     ║");
        System.out.println("║     6 - Estatísticas do catálogo                                ║");
        System.out.println("║                                                                  ║");
        System.out.println("║     0 - Sair                                                    ║");
        System.out.println("║                                                                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        System.out.print("\n👉 Digite sua opção: ");
    }
    
    private int lerOpcao() {
        try {
            String entrada = scanner.nextLine().trim();
            return entrada.isEmpty() ? -1 : Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            System.out.println("❌ Digite apenas números!");
            return -1;
        }
    }
    
    // FUNCIONALIDADE OBRIGATÓRIA 1
    private void buscarEAdicionarLivro() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║           📚 BUSCAR E ADICIONAR LIVRO POR TÍTULO                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        System.out.print("\n📖 Digite o título do livro: ");
        String titulo = scanner.nextLine().trim();
        
        if (titulo.isEmpty()) {
            System.out.println("❌ Título não pode estar vazio!");
            return;
        }
        
        try {
            Livro livro = catalogoService.buscarEAdicionarLivroPorTitulo(titulo);
            
            if (livro != null) {
                System.out.println("\n✅ Livro adicionado com sucesso!");
                exibirDetalhesLivro(livro);
            } else {
                System.out.println("❌ Nenhum livro encontrado com esse título.");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro ao buscar livro: " + e.getMessage());
        }
    }
    
    // FUNCIONALIDADE OBRIGATÓRIA 2
    private void listarTodosLivros() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                📚 TODOS OS LIVROS DO CATÁLOGO                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        List<Livro> livros = catalogoService.listarTodosOsLivros();
        
        if (livros.isEmpty()) {
            System.out.println("\n📭 O catálogo está vazio.");
            System.out.println("💡 Use a opção 1 para adicionar livros!");
        } else {
            System.out.println("\n📊 Total: " + livros.size() + " livro(s)");
            System.out.println("═".repeat(70));
            
            int contador = 1;
            for (Livro livro : livros) {
                System.out.printf("%2d. %s%n", contador++, livro.getResumo());
            }
        }
    }
    
    // FUNCIONALIDADE OBRIGATÓRIA 3
    private void listarLivrosPorIdioma() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║              🌍 LISTAR LIVROS POR IDIOMA                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        System.out.println("\n📋 Idiomas disponíveis: pt, en, fr, es, de, it");
        System.out.print("🗣️  Digite o código do idioma: ");
        String idioma = scanner.nextLine().trim().toLowerCase();
        
        if (idioma.isEmpty()) {
            System.out.println("❌ Código do idioma não pode estar vazio!");
            return;
        }
        
        List<Livro> livros = catalogoService.listarLivrosPorIdioma(idioma);
        
        if (livros.isEmpty()) {
            System.out.println("\n📭 Nenhum livro encontrado em " + obterNomeIdioma(idioma));
        } else {
            System.out.println("\n📚 Livros em " + obterNomeIdioma(idioma) + ":");
            System.out.println("═".repeat(60));
            
            int contador = 1;
            for (Livro livro : livros) {
                System.out.printf("%2d. %s%n", contador++, livro.getResumo());
            }
        }
    }
    
    // FUNCIONALIDADE OBRIGATÓRIA 4
    private void listarTodosAutores() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                   👥 LISTA DE AUTORES                            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        List<Autor> autores = catalogoService.listarTodosOsAutores();
        
        if (autores.isEmpty()) {
            System.out.println("\n📭 Nenhum autor encontrado no catálogo.");
            System.out.println("💡 Adicione livros primeiro para ver os autores!");
        } else {
            System.out.println("\n📊 Total: " + autores.size() + " autor(es)");
            System.out.println("═".repeat(70));
            
            int contador = 1;
            for (Autor autor : autores) {
                System.out.printf("%2d. %s%n", contador++, formatarAutor(autor));
            }
        }
    }
    
    // FUNCIONALIDADE OBRIGATÓRIA 5
    private void listarAutoresVivosNoAno() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║            🕰️  AUTORES VIVOS EM DETERMINADO ANO                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        System.out.print("\n📅 Digite o ano (ex: 1800, 1900, 2000): ");
        String entradaAno = scanner.nextLine().trim();
        
        if (entradaAno.isEmpty()) {
            System.out.println("❌ Ano não pode estar vazio!");
            return;
        }
        
        try {
            Integer ano = Integer.parseInt(entradaAno);
            
            if (ano < 1 || ano > 2024) {
                System.out.println("❌ Digite um ano válido (1-2024)!");
                return;
            }
            
            List<Autor> autoresVivos = catalogoService.listarAutoresVivosNoAno(ano);
            
            if (autoresVivos.isEmpty()) {
                System.out.println("\n📭 Nenhum autor estava vivo no ano " + ano);
            } else {
                System.out.println("\n👥 Autores vivos em " + ano + ":");
                System.out.println("═".repeat(60));
                
                int contador = 1;
                for (Autor autor : autoresVivos) {
                    System.out.printf("%2d. %s%n", contador++, formatarAutorComStatus(autor, ano));
                }
            }
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Digite um ano válido!");
        }
    }
    
    private void exibirEstatisticas() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                📊 ESTATÍSTICAS DO CATÁLOGO                       ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        catalogoService.exibirEstatisticasCatalogo();
        catalogoService.exibirEstatisticasAutores();
    }
    
    // Métodos auxiliares
    
    private void exibirDetalhesLivro(Livro livro) {
        System.out.println("─".repeat(50));
        System.out.printf("📖 Título: %s%n", livro.getTitulo());
        System.out.printf("👤 Autor: %s%n", 
            livro.getAutor() != null ? livro.getAutor().getNomeFormatado() : "Desconhecido");
        System.out.printf("🌍 Idioma: %s%n", livro.getNomeIdioma());
        System.out.printf("📥 Downloads: %,d%n", 
            livro.getNumeroDownloads() != null ? livro.getNumeroDownloads() : 0);
        
        if (livro.getAutor() != null) {
            Autor autor = livro.getAutor();
            System.out.printf("📅 Período do autor: %s%n", autor.getPeriodoVida());
        }
    }
    
    private String formatarAutor(Autor autor) {
        return String.format("👤 %s (%s)", 
            autor.getNomeFormatado(), 
            autor.getPeriodoVida());
    }
    
    private String formatarAutorComStatus(Autor autor, Integer ano) {
        String status = catalogoService.autorEstaviaVivoNoAno(autor, ano) ? "✅ VIVO" : "❌ MORTO";
        return String.format("👤 %s (%s) - %s em %d", 
            autor.getNomeFormatado(), 
            autor.getPeriodoVida(),
            status,
            ano);
    }
    
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
    
    private void pausar() {
        System.out.print("\n⏸️  Pressione ENTER para continuar...");
        scanner.nextLine();
    }
    
    private void exibirDespedida() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    👋 OBRIGADO POR USAR O SISTEMA!               ║");
        System.out.println("║                                                                  ║");
        System.out.println("║              Continue explorando o mundo da literatura!         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
    }
}
