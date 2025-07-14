package com.exemplo.literatura.service;

import com.exemplo.literatura.dto.GutendxResponseCompleta;
import com.exemplo.literatura.dto.LivroCompletoDto;
import com.exemplo.literatura.dto.AutorCompletoDto;
import com.exemplo.literatura.model.Livro;
import com.exemplo.literatura.model.Autor;
import com.exemplo.literatura.repository.LivroRepository;
import com.exemplo.literatura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Serviço principal para gerenciar o catálogo de livros e autores
 * Implementa as funcionalidades obrigatórias:
 * 1. Busca de livro por título
 * 2. Listagem de todos os livros
 * 3. Listagem por idioma
 */
@Service
@Transactional
public class CatalogoService {
    
    @Autowired
    private LivroRepository livroRepository;
    
    @Autowired
    private AutorRepository autorRepository;
    
    @Autowired
    private GutendxHttpService gutendxHttpService;
    
    @Autowired
    private ConversaoDadosService conversaoService;
    
    /**
     * FUNCIONALIDADE OBRIGATÓRIA 1: Busca de livro por título
     * Busca na API Gutendx e salva o primeiro resultado no banco
     */
    public Livro buscarEAdicionarLivroPorTitulo(String titulo) {
        try {
            System.out.println("🔍 Buscando livro por título: " + titulo);
            
            // Buscar na API Gutendx
            String jsonResposta = gutendxHttpService.obterJsonResposta(titulo);
            GutendxResponseCompleta resposta = conversaoService.converterResposta(jsonResposta);
            
            if (resposta.estaVazia()) {
                System.out.println("❌ Nenhum livro encontrado com o título: " + titulo);
                return null;
            }
            
            // Pegar o primeiro resultado
            LivroCompletoDto livroDto = resposta.getLivros().get(0);
            
            // Verificar se já existe no banco
            if (livroDto.getId() != null) {
                Optional<Livro> livroExistente = livroRepository.findByGutenbergId(livroDto.getId());
                if (livroExistente.isPresent()) {
                    System.out.println("📚 Livro já existe no catálogo: " + livroExistente.get().getTitulo());
                    return livroExistente.get();
                }
            }
            
            // Converter DTO para entidade e salvar
            Livro livro = converterDtoParaEntidade(livroDto);
            Livro livroSalvo = livroRepository.save(livro);
            
            System.out.println("✅ Livro adicionado ao catálogo: " + livroSalvo.getTitulo());
            return livroSalvo;
            
        } catch (Exception e) {
            System.err.println("❌ Erro ao buscar e adicionar livro: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar livro por título: " + titulo, e);
        }
    }
    
    /**
     * FUNCIONALIDADE OBRIGATÓRIA 2: Listagem de todos os livros
     */
    public List<Livro> listarTodosOsLivros() {
        System.out.println("📚 Listando todos os livros do catálogo...");
        List<Livro> livros = livroRepository.findAllByOrderByTitulo();
        System.out.println("📊 Total de livros no catálogo: " + livros.size());
        return livros;
    }
    
    /**
     * FUNCIONALIDADE OBRIGATÓRIA 3: Listagem por idioma (consulta derivada)
     */
    public List<Livro> listarLivrosPorIdioma(String idioma) {
        System.out.println("🌍 Listando livros em " + obterNomeIdioma(idioma) + "...");
        List<Livro> livros = livroRepository.findByIdiomaOrderByTitulo(idioma.toLowerCase());
        System.out.println("📊 Livros encontrados em " + obterNomeIdioma(idioma) + ": " + livros.size());
        return livros;
    }
    
    // Métodos auxiliares
    
    /**
     * Converte DTO da API para entidade JPA
     */
    private Livro converterDtoParaEntidade(LivroCompletoDto livroDto) {
        // Processar autor
        Autor autor = null;
        if (livroDto.getAutores() != null && !livroDto.getAutores().isEmpty()) {
            AutorCompletoDto autorDto = livroDto.getAutores().get(0);
            autor = buscarOuCriarAutor(autorDto);
        }
        
        // Processar idioma (apenas o primeiro)
        String idioma = "unknown";
        if (livroDto.getIdiomas() != null && !livroDto.getIdiomas().isEmpty()) {
            idioma = livroDto.getIdiomas().get(0).toLowerCase();
        }
        
        // Criar entidade Livro
        Livro livro = new Livro(
            livroDto.getTitulo(),
            autor,
            idioma,
            livroDto.getNumeroDownloads(),
            livroDto.getId()
        );
        
        return livro;
    }
    
    /**
     * Busca autor existente ou cria novo
     */
    private Autor buscarOuCriarAutor(AutorCompletoDto autorDto) {
        String nomeAutor = autorDto.getNome();
        
        // Buscar autor existente
        Optional<Autor> autorExistente = autorRepository.findByNome(nomeAutor);
        if (autorExistente.isPresent()) {
            return autorExistente.get();
        }
        
        // Criar novo autor
        Autor novoAutor = new Autor(
            nomeAutor,
            autorDto.getAnoNascimento(),
            autorDto.getAnoMorte()
        );
        
        return autorRepository.save(novoAutor);
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
    
    // Métodos adicionais úteis
    
    /**
     * Busca livros por título no catálogo local
     */
    public List<Livro> buscarLivrosPorTituloLocal(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }
    
    /**
     * Busca livros por autor no catálogo local
     */
    public List<Livro> buscarLivrosPorAutorLocal(String nomeAutor) {
        return livroRepository.findByAutorNomeContainingIgnoreCase(nomeAutor);
    }
    
    /**
     * Obtém estatísticas do catálogo
     */
    public void exibirEstatisticasCatalogo() {
        long totalLivros = livroRepository.count();
        long totalAutores = autorRepository.count();
        
        System.out.println("\n📊 ESTATÍSTICAS DO CATÁLOGO:");
        System.out.println("═".repeat(40));
        System.out.println("📚 Total de livros: " + totalLivros);
        System.out.println("👤 Total de autores: " + totalAutores);
        
        // Estatísticas por idioma
        List<Object[]> estatisticasIdioma = livroRepository.contarLivrosPorIdioma();
        if (!estatisticasIdioma.isEmpty()) {
            System.out.println("\n🌍 Livros por idioma:");
            for (Object[] stat : estatisticasIdioma) {
                String idioma = (String) stat[0];
                Long quantidade = (Long) stat[1];
                System.out.printf("   %s: %d livro(s)%n", obterNomeIdioma(idioma), quantidade);
            }
        }
    }
    
    /**
     * Verifica se o catálogo está vazio
     */
    public boolean catalogoEstaVazio() {
        return livroRepository.count() == 0;
    }
    
    /**
     * FUNCIONALIDADE OBRIGATÓRIA 4: Lista de autores
     * Lista todos os autores dos livros buscados, ordenados por nome
     */
    public List<Autor> listarTodosOsAutores() {
        System.out.println("👥 Listando todos os autores do catálogo...");
        List<Autor> autores = autorRepository.findAllByOrderByNome();
        System.out.println("📊 Total de autores no catálogo: " + autores.size());
        return autores;
    }
    
    /**
     * FUNCIONALIDADE OBRIGATÓRIA 5: Listar autores vivos em determinado ano
     * Lista autores que estavam vivos em um ano específico
     */
    public List<Autor> listarAutoresVivosNoAno(Integer ano) {
        System.out.println("🕰️  Listando autores vivos no ano " + ano + "...");
        List<Autor> autoresVivos = autorRepository.findAutoresVivosNoAno(ano);
        System.out.println("📊 Autores vivos em " + ano + ": " + autoresVivos.size());
        return autoresVivos;
    }
    
    // Métodos auxiliares para autores
    
    /**
     * Verifica se um autor estava vivo em determinado ano
     */
    public boolean autorEstaviaVivoNoAno(Autor autor, Integer ano) {
        // Deve ter nascido antes ou no ano especificado
        if (autor.getAnoNascimento() == null || autor.getAnoNascimento() > ano) {
            return false;
        }
        
        // Se não tem ano de morte, ainda está vivo
        if (autor.getAnoMorte() == null) {
            return true;
        }
        
        // Se tem ano de morte, deve ter morrido depois do ano especificado
        return autor.getAnoMorte() >= ano;
    }
    
    /**
     * Obtém estatísticas dos autores
     */
    public void exibirEstatisticasAutores() {
        long totalAutores = autorRepository.count();
        List<Autor> autoresVivos = autorRepository.findByAnoMorteIsNull();
        
        System.out.println("\n👥 ESTATÍSTICAS DOS AUTORES:");
        System.out.println("═".repeat(40));
        System.out.println("📊 Total de autores: " + totalAutores);
        System.out.println("💚 Autores ainda vivos: " + autoresVivos.size());
        System.out.println("⚰️  Autores falecidos: " + (totalAutores - autoresVivos.size()));
        
        if (totalAutores > 0) {
            // Estatísticas por século
            System.out.println("\n📅 Autores por século:");
            exibirAutoresPorSeculo();
        }
    }
    
    /**
     * Exibe autores agrupados por século de nascimento
     */
    private void exibirAutoresPorSeculo() {
        // Séculos mais comuns na literatura
        int[][] seculos = {
            {1501, 1600, 16}, // Século XVI
            {1601, 1700, 17}, // Século XVII
            {1701, 1800, 18}, // Século XVIII
            {1801, 1900, 19}, // Século XIX
            {1901, 2000, 20}, // Século XX
            {2001, 2100, 21}  // Século XXI
        };
        
        for (int[] seculo : seculos) {
            List<Autor> autoresSeculo = autorRepository.findAutoresPorSeculo(seculo[0], seculo[1]);
            if (!autoresSeculo.isEmpty()) {
                System.out.printf("   Século %d: %d autor(es)%n", seculo[2], autoresSeculo.size());
            }
        }
    }
    
    /**
     * Busca autores por nome (busca parcial)
     */
    public List<Autor> buscarAutoresPorNome(String nome) {
        return autorRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    /**
     * Obtém autores mais prolíficos (com mais livros)
     */
    public List<Autor> obterAutoresMaisProlificos(int limite) {
        List<Autor> autores = autorRepository.findAutoresComMaisLivros();
        return autores.stream().limit(limite).toList();
    }
}
