package com.exemplo.literatura.controller;

import com.exemplo.literatura.dto.GutendxResponse;
import com.exemplo.literatura.dto.LivroDto;
import com.exemplo.literatura.service.GutendxHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gutendx")
public class GutendxController {
    
    @Autowired
    private GutendxHttpService gutendxHttpService;
    
    /**
     * Busca livros por termo de pesquisa
     * GET /api/gutendx/buscar?termo=shakespeare
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<LivroDto>> buscarLivros(@RequestParam String termo) {
        List<LivroDto> livros = gutendxHttpService.buscarLivros(termo);
        return ResponseEntity.ok(livros);
    }
    
    /**
     * Busca livros por autor
     * GET /api/gutendx/autor?nome=Shakespeare
     */
    @GetMapping("/autor")
    public ResponseEntity<List<LivroDto>> buscarPorAutor(@RequestParam String nome) {
        List<LivroDto> livros = gutendxHttpService.buscarLivrosPorAutor(nome);
        return ResponseEntity.ok(livros);
    }
    
    /**
     * Busca livros por título
     * GET /api/gutendx/titulo?nome=Hamlet
     */
    @GetMapping("/titulo")
    public ResponseEntity<List<LivroDto>> buscarPorTitulo(@RequestParam String nome) {
        List<LivroDto> livros = gutendxHttpService.buscarLivrosPorTitulo(nome);
        return ResponseEntity.ok(livros);
    }
    
    /**
     * Busca livros por idioma
     * GET /api/gutendx/idioma?codigo=en
     */
    @GetMapping("/idioma")
    public ResponseEntity<List<LivroDto>> buscarPorIdioma(@RequestParam String codigo) {
        List<LivroDto> livros = gutendxHttpService.buscarLivrosPorIdioma(codigo);
        return ResponseEntity.ok(livros);
    }
    
    /**
     * Busca livro por ID
     * GET /api/gutendx/livro/1342
     */
    @GetMapping("/livro/{id}")
    public ResponseEntity<LivroDto> buscarPorId(@PathVariable Long id) {
        LivroDto livro = gutendxHttpService.buscarLivroPorId(id);
        if (livro != null) {
            return ResponseEntity.ok(livro);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Lista todos os livros com paginação
     * GET /api/gutendx/listar?pagina=1
     */
    @GetMapping("/listar")
    public ResponseEntity<GutendxResponse> listarLivros(@RequestParam(defaultValue = "1") int pagina) {
        GutendxResponse response = gutendxHttpService.listarLivros(pagina);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Testa conectividade com a API
     * GET /api/gutendx/teste
     */
    @GetMapping("/teste")
    public ResponseEntity<String> testarConectividade() {
        boolean conectado = gutendxHttpService.testarConectividade();
        if (conectado) {
            return ResponseEntity.ok("✅ API Gutendx está acessível!");
        } else {
            return ResponseEntity.status(503).body("❌ API Gutendx não está acessível.");
        }
    }
}
