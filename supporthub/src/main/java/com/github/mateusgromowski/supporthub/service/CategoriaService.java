package com.github.mateusgromowski.supporthub.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.github.mateusgromowski.supporthub.exception.CategoriaDuplicadaException;
import com.github.mateusgromowski.supporthub.exception.CategoriaInexistenteException;
import com.github.mateusgromowski.supporthub.model.Categoria;
import com.github.mateusgromowski.supporthub.repository.CategoriaRepository;

@Service
public class CategoriaService {
    private CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<Categoria> findAll() {
        return repository.findAll();
    }

    public Categoria findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new CategoriaInexistenteException("A categoria de ID " + id + " não existe no banco de dados."));
    }

    public ResponseEntity<?> create(Categoria categoria) {
        verificaCategoriaExistente(categoria.getNome());
        return ResponseEntity.ok(repository.save(categoria));
    }

    public ResponseEntity<?> update(Categoria categoria) {
        verificaCategoriaExistente(categoria.getNome(), categoria.getId());
        Categoria entity = findById(categoria.getId());
        entity.setNome(categoria.getNome());
        entity.setDescricao(categoria.getDescricao());
        repository.save(entity);
        return ResponseEntity.ok(categoria);
    }

    public ResponseEntity<?> delete(Long id) {
        repository.delete(findById(id));
        return ResponseEntity.noContent().build();
    }

    public void verificaCategoriaExistente(String nome) {
        if (repository.existsByNome(nome)) {
            throw new CategoriaDuplicadaException("Já existe uma categoria com esse nome no banco de dados.");
        }
    }

    public void verificaCategoriaExistente(String nome, Long id) {
        if (repository.existsByNomeAndIdNot(nome, id)) {
            throw new CategoriaDuplicadaException("Já existe uma categoria com esse nome no banco de dados.");
        }
    }

}
