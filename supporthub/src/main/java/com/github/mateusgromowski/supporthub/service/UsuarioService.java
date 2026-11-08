package com.github.mateusgromowski.supporthub.service;

import java.util.List;

import com.github.mateusgromowski.supporthub.model.Usuario;
import com.github.mateusgromowski.supporthub.repository.UsuarioRepository;

public class UsuarioService {
    private UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> findAll() {
        return repository.findAll();
    }

    public Usuario findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public void create(Usuario usuario) {
        repository.save(usuario);
    }

    public void update(Usuario usuario) {
        repository.save(usuario);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }
}
