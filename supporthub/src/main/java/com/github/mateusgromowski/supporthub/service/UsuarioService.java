package com.github.mateusgromowski.supporthub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.mateusgromowski.supporthub.exception.BancoDeDadosVazioException;
import com.github.mateusgromowski.supporthub.exception.EmailDuplicadoException;
import com.github.mateusgromowski.supporthub.exception.UsuarioInexistenteException;
import com.github.mateusgromowski.supporthub.model.Usuario;
import com.github.mateusgromowski.supporthub.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> findAll() {
        verificaBancoVazio();
        return repository.findAll();
    }

    public Usuario findById(Long id) {
        verificaBancoVazio();
        return repository.findById(id).orElseThrow(
                () -> new UsuarioInexistenteException(String.format("Usuário de ID %d não encontrado.", id)));
    }

    public void create(Usuario usuario) {
        validaEmail(usuario.getEmail());
        repository.save(usuario);
    }

    public void update(Usuario usuario) {
        String email = usuario.getEmail();
        validaEmail(email, usuario.getId());
        Usuario entity = findById(usuario.getId());
        entity.setNome(usuario.getNome());
        entity.setEmail(email);
        entity.setSenha(usuario.getSenha());
        entity.setPerfil(usuario.getPerfil());
        repository.save(entity);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    public void validaEmail(String email, Long id) {
        if (repository.existsByEmailAndIdNot(email, id)) {
            throw new EmailDuplicadoException("Email inserido já existe no banco de dados.");
        }
    }

    public void validaEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new EmailDuplicadoException("Email inserido já existe no banco de dados.");
        }
    }

    public void verificaBancoVazio() {
        if (repository.count() == 0) {
            throw new BancoDeDadosVazioException("O banco de dados não contém informação.");
        }
    }
}
