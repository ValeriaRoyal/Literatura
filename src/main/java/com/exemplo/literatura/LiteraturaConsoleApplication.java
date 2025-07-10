package com.exemplo.literatura;

import com.exemplo.literatura.dto.AutorDto;
import com.exemplo.literatura.dto.LivroDto;
import com.exemplo.literatura.service.GutendxHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class LiteraturaConsoleApplication implements CommandLineRunner {
    
    @Autowired
    private GutendxHttpService gutendxHttpService;
    
    private Scanner scanner = new Scanner(System.in);
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("🚀 SISTEMA DE LITERATURA - GUTENDX API");
        System.out.println("=".repeat(60));
        System.out.println("📚 Conectado à API do Project Gutenberg");
        System.out.println("🌟 Mais de 70.000 livros disponíveis!");
        System.out.println("🔧 Usando HttpClient, HttpRequest e HttpResponse do Java");
        System.out.println("=".repeat(60));
        
        // Testando conectividade antes de iniciar
        if (!gutendxHttpService.testarConectividade()) {
            System.err.println("❌ Não foi possível conectar à API. Verifique sua conexão com a internet.");
            return;
        }
        
        boolean continuar = true;
        
        while (continuar) {
            exibirMenu();
            int opcao = lerOpcao();
            
            switch (opcao) {
                case 1 -> buscarLivrosPorTitulo();
                case 2 -> buscarLivrosPorAutor();
                case 3 -> buscarLivrosPorIdioma();
                case 4 -> buscarLivroPorId();
                case 5 -> listarTodosLivros();
                case 6 -> demonstrarHttpClasses();
                case 0 -> {
                    System.out.println("👋 Encerrando o sistema...");
                    System.out.println("Obrigado por usar o Sistema de Literatura!");
                    continuar = false;
                }
                default -> System.out.println("❌ Opção inválida! Tente novamente.");
            }
            
            if (continuar) {
                System.out.println("\n⏸️  Pressione Enter para continuar...");
                scanner.nextLine();
            }
        }
    }
    
    private void exibirMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📋 MENU PRINCIPAL - SISTEMA DE LITERATURA");
        System.out.println("=".repeat(60));
        System.out.println("1 - 🔍 Buscar livros por título");
        System.out.println("2 - 👤 Buscar livros por autor");
        System.out.println("3 - 🌍 Buscar livros por idioma");
        System.out.println("4 - 🆔 Buscar livro por ID");
        System.out.println("5 - 📖 Listar todos os livros (paginado)");
        System.out.println("6 - 🔧 Demonstração das classes HTTP");
        System.out.println("0 - 🚪 Sair");
        System.out.println("=".repeat(60));
        System.out.print("➤ Escolha uma opção: ");
    }
    
    private int lerOpcao() {
        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            return opcao;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private void buscarLivrosPorTitulo() {
        System.out.print("\n📖 Digite o título do livro: ");
        String titulo = scanner.nextLine();
        
        if (titulo.trim().isEmpty()) {
            System.out.println("❌ Título não pode estar vazio!");
            return;
        }
        
        System.out.println("\n🔍 Buscando livros com título: " + titulo);
        System.out.println("⏳ Aguarde...");
        
        List<LivroDto> livros = gutendxHttpService.buscarLivrosPorTitulo(titulo);
        exibirResultados(livros, "título '" + titulo + "'");
    }
    
    private void buscarLivrosPorAutor() {
        System.out.print("\n👤 Digite o nome do autor: ");
        String autor = scanner.nextLine();
        
        if (autor.trim().isEmpty()) {
            System.out.println("❌ Nome do autor não pode estar vazio!");
            return;
        }
        
        System.out.println("\n🔍 Buscando livros do autor: " + autor);
        System.out.println("⏳ Aguarde...");
        
        List<LivroDto> livros = gutendxHttpService.buscarLivrosPorAutor(autor);
        exibirResultados(livros, "autor '" + autor + "'");
    }
    
    private void buscarLivrosPorIdioma() {
        System.out.println("\n🌍 Idiomas disponíveis:");
        System.out.println("  🇺🇸 en - Inglês");
        System.out.println("  🇧🇷 pt - Português");
        System.out.println("  🇪🇸 es - Espanhol");
        System.out.println("  🇫🇷 fr - Francês");
        System.out.println("  🇩🇪 de - Alemão");
        System.out.println("  🇮🇹 it - Italiano");
        System.out.print("\n➤ Digite o código do idioma: ");
        String idioma = scanner.nextLine();
        
        if (idioma.trim().isEmpty()) {
            System.out.println("❌ Código do idioma não pode estar vazio!");
            return;
        }
        
        System.out.println("\n🔍 Buscando livros no idioma: " + idioma);
        System.out.println("⏳ Aguarde...");
        
        List<LivroDto> livros = gutendxHttpService.buscarLivrosPorIdioma(idioma);
        exibirResultados(livros, "idioma '" + idioma + "'");
    }
    
    private void buscarLivroPorId() {
        System.out.print("\n🆔 Digite o ID do livro: ");
        String input = scanner.nextLine();
        
        if (input.trim().isEmpty()) {
            System.out.println("❌ ID não pode estar vazio!");
            return;
        }
        
        try {
            Long id = Long.parseLong(input);
            System.out.println("\n🔍 Buscando livro com ID: " + id);
            System.out.println("⏳ Aguarde...");
            
            LivroDto livro = gutendxHttpService.buscarLivroPorId(id);
            if (livro != null) {
                System.out.println("\n" + "=".repeat(60));
                System.out.println("📚 LIVRO ENCONTRADO");
                System.out.println("=".repeat(60));
                exibirDetalhesLivro(livro);
            } else {
                System.out.println("\n❌ Livro com ID " + id + " não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido! Digite apenas números.");
        }
    }
    
    private void listarTodosLivros() {
        System.out.print("\n📄 Digite o número da página (padrão: 1): ");
        String input = scanner.nextLine();
        
        int pagina = 1;
        if (!input.trim().isEmpty()) {
            try {
                pagina = Integer.parseInt(input);
                if (pagina <= 0) pagina = 1;
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Número inválido, usando página 1");
            }
        }
        
        System.out.println("\n📖 Carregando página " + pagina + "...");
        System.out.println("⏳ Aguarde...");
        
        var response = gutendxHttpService.listarLivros(pagina);
        if (response != null && response.getResults() != null) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("📄 PÁGINA " + pagina + " - Total de livros: " + response.getCount());
            System.out.println("=".repeat(60));
            
            exibirResultados(response.getResults(), "página " + pagina);
            
            if (response.getNext() != null) {
                System.out.println("\n➡️ Há mais páginas disponíveis.");
            }
        } else {
            System.out.println("\n❌ Nenhum resultado encontrado.");
        }
    }
    
    private void demonstrarHttpClasses() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🔧 DEMONSTRAÇÃO DAS CLASSES HTTP DO JAVA");
        System.out.println("=".repeat(60));
        
        System.out.println("📋 Este sistema utiliza as seguintes classes nativas do Java:");
        System.out.println();
        System.out.println("🔹 HttpClient:");
        System.out.println("   • Classe principal para fazer requisições HTTP");
        System.out.println("   • Configurada com timeout e redirecionamento automático");
        System.out.println("   • Suporte a HTTP/1.1 e HTTP/2");
        System.out.println();
        System.out.println("🔹 HttpRequest:");
        System.out.println("   • Representa uma requisição HTTP");
        System.out.println("   • Permite configurar URL, método, cabeçalhos e timeout");
        System.out.println("   • Suporte a GET, POST, PUT, DELETE, etc.");
        System.out.println();
        System.out.println("🔹 HttpResponse:");
        System.out.println("   • Representa a resposta de uma requisição HTTP");
        System.out.println("   • Fornece acesso ao status code, cabeçalhos e corpo da resposta");
        System.out.println("   • Suporte a diferentes tipos de corpo (String, bytes, etc.)");
        System.out.println();
        
        System.out.println("🧪 Fazendo uma requisição de demonstração...");
        
        // Demonstração prática
        if (gutendxHttpService.testarConectividade()) {
            System.out.println("✅ Demonstração concluída com sucesso!");
            System.out.println("📊 Verifique os logs acima para ver os detalhes da requisição HTTP.");
        } else {
            System.out.println("❌ Falha na demonstração.");
        }
    }
    
    private void exibirResultados(List<LivroDto> livros, String criterio) {
        if (livros == null || livros.isEmpty()) {
            System.out.println("\n❌ Nenhum livro encontrado para: " + criterio);
            return;
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📚 RESULTADOS PARA: " + criterio.toUpperCase());
        System.out.println("📊 Total encontrado: " + livros.size());
        System.out.println("=".repeat(60));
        
        for (int i = 0; i < livros.size(); i++) {
            LivroDto livro = livros.get(i);
            System.out.println("\n📖 " + (i + 1) + ". " + livro.getTitle());
            
            if (livro.getAuthors() != null && !livro.getAuthors().isEmpty()) {
                System.out.print("   👤 Autor(es): ");
                for (int j = 0; j < livro.getAuthors().size(); j++) {
                    AutorDto autor = livro.getAuthors().get(j);
                    System.out.print(autor.getName());
                    if (j < livro.getAuthors().size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }
            
            if (livro.getLanguages() != null && !livro.getLanguages().isEmpty()) {
                System.out.println("   🌍 Idioma(s): " + String.join(", ", livro.getLanguages()));
            }
            
            if (livro.getDownloadCount() != null) {
                System.out.println("   📥 Downloads: " + livro.getDownloadCount());
            }
            
            System.out.println("   🆔 ID: " + livro.getId());
        }
    }
    
    private void exibirDetalhesLivro(LivroDto livro) {
        System.out.println("📖 Título: " + livro.getTitle());
        System.out.println("🆔 ID: " + livro.getId());
        
        if (livro.getAuthors() != null && !livro.getAuthors().isEmpty()) {
            System.out.println("\n👤 Autor(es):");
            for (AutorDto autor : livro.getAuthors()) {
                System.out.println("  • " + autor.getName());
                if (autor.getBirthYear() != null || autor.getDeathYear() != null) {
                    System.out.print("    📅 (");
                    if (autor.getBirthYear() != null) {
                        System.out.print(autor.getBirthYear());
                    }
                    System.out.print(" - ");
                    if (autor.getDeathYear() != null) {
                        System.out.print(autor.getDeathYear());
                    }
                    System.out.println(")");
                }
            }
        }
        
        if (livro.getLanguages() != null && !livro.getLanguages().isEmpty()) {
            System.out.println("\n🌍 Idioma(s): " + String.join(", ", livro.getLanguages()));
        }
        
        if (livro.getSubjects() != null && !livro.getSubjects().isEmpty()) {
            System.out.println("\n📚 Assuntos:");
            for (String assunto : livro.getSubjects()) {
                System.out.println("  • " + assunto);
            }
        }
        
        if (livro.getDownloadCount() != null) {
            System.out.println("\n📥 Número de downloads: " + livro.getDownloadCount());
        }
        
        System.out.println("\n©️ Copyright: " + (livro.getCopyright() != null && livro.getCopyright() ? "Sim" : "Não"));
        
        if (livro.getFormats() != null && !livro.getFormats().isEmpty()) {
            System.out.println("\n📄 Formatos disponíveis:");
            livro.getFormats().forEach((formato, url) -> {
                System.out.println("  • " + formato);
            });
        }
    }
}
