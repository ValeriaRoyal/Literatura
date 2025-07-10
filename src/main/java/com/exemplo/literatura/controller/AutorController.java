package com.exemplo.literatura.controller;

import com.exemplo.literatura.model.Autor;
import com.exemplo.literatura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/autores")
@CrossOrigin(origins = "*")
public class AutorController {
    
    @Autowired
    private AutorRepository autorRepository;
    
    @GetMapping
    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Autor> buscarPorId(@PathVariable Long id) {
        Optional<Autor> autor = autorRepository.findById(id);
        return autor.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/buscar")
    public List<Autor> buscarPorNome(@RequestParam String nome) {
        return autorRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    @GetMapping("/nacionalidade/{nacionalidade}")
    public List<Autor> buscarPorNacionalidade(@PathVariable String nacionalidade) {
        return autorRepository.findByNacionalidade(nacionalidade);
    }
    
    @GetMapping("/vivos")
    public List<Autor> listarAutoresVivos() {
        return autorRepository.findAutoresVivos();
    }
    
    @PostMapping
    public Autor criar(@RequestBody Autor autor) {
        return autorRepository.save(autor);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Autor> atualizar(@PathVariable Long id, @RequestBody Autor autorAtualizado) {
        return autorRepository.findById(id)
                .map(autor -> {
                    autor.setNome(autorAtualizado.getNome());
                    autor.setDataNascimento(autorAtualizado.getDataNascimento());
                    autor.setDataMorte(autorAtualizado.getDataMorte());
                    autor.setNacionalidade(autorAtualizado.getNacionalidade());
                    autor.setBiografia(autorAtualizado.getBiografia());
                    return ResponseEntity.ok(autorRepository.save(autor));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return autorRepository.findById(id)
                .map(autor -> {
                    autorRepository.delete(autor);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
