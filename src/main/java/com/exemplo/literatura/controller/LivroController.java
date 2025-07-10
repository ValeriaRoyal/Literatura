package com.exemplo.literatura.controller;

import com.exemplo.literatura.model.Livro;
import com.exemplo.literatura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/livros")
@CrossOrigin(origins = "*")
public class LivroController {
    
    @Autowired
    private LivroRepository livroRepository;
    
    @GetMapping
    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        Optional<Livro> livro = livroRepository.findById(id);
        return livro.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/buscar")
    public List<Livro> buscarPorTitulo(@RequestParam String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }
    
    @GetMapping("/genero/{genero}")
    public List<Livro> buscarPorGenero(@PathVariable String genero) {
        return livroRepository.findByGenero(genero);
    }
    
    @GetMapping("/autor/{autorId}")
    public List<Livro> buscarPorAutor(@PathVariable Long autorId) {
        return livroRepository.findByAutorId(autorId);
    }
    
    @GetMapping("/autor/nome/{nomeAutor}")
    public List<Livro> buscarPorNomeAutor(@PathVariable String nomeAutor) {
        return livroRepository.findByAutorNome(nomeAutor);
    }
    
    @GetMapping("/editora/{editora}")
    public List<Livro> buscarPorEditora(@PathVariable String editora) {
        return livroRepository.findByEditora(editora);
    }
    
    @GetMapping("/periodo")
    public List<Livro> buscarPorPeriodo(@RequestParam String dataInicio, 
                                       @RequestParam String dataFim) {
        LocalDate inicio = LocalDate.parse(dataInicio);
        LocalDate fim = LocalDate.parse(dataFim);
        return livroRepository.findByDataPublicacaoBetween(inicio, fim);
    }
    
    @PostMapping
    public Livro criar(@RequestBody Livro livro) {
        return livroRepository.save(livro);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @RequestBody Livro livroAtualizado) {
        return livroRepository.findById(id)
                .map(livro -> {
                    livro.setTitulo(livroAtualizado.getTitulo());
                    livro.setDataPublicacao(livroAtualizado.getDataPublicacao());
                    livro.setGenero(livroAtualizado.getGenero());
                    livro.setSinopse(livroAtualizado.getSinopse());
                    livro.setIsbn(livroAtualizado.getIsbn());
                    livro.setNumeroPaginas(livroAtualizado.getNumeroPaginas());
                    livro.setEditora(livroAtualizado.getEditora());
                    livro.setIdioma(livroAtualizado.getIdioma());
                    livro.setAutor(livroAtualizado.getAutor());
                    return ResponseEntity.ok(livroRepository.save(livro));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return livroRepository.findById(id)
                .map(livro -> {
                    livroRepository.delete(livro);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
