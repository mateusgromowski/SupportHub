package com.github.mateusgromowski.supporthub.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
        return repository.findAll();
    }

    public Usuario findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new UsuarioInexistenteException(String.format("Usuário de ID %d não encontrado.", id)));
    }

    public ResponseEntity<?> create(Usuario usuario) {
        validaEmail(usuario.getEmail());
        repository.save(usuario);
        return ResponseEntity.ok(usuario);
    }

    public ResponseEntity<?> update(Usuario usuario) {
        String email = usuario.getEmail();
        validaEmail(email, usuario.getId());
        Usuario entity = findById(usuario.getId());
        entity.setNome(usuario.getNome());
        entity.setEmail(email);
        entity.setSenha(usuario.getSenha());
        entity.setPerfil(usuario.getPerfil());
        repository.save(entity);
        return ResponseEntity.ok(entity);
    }

    public ResponseEntity<?> delete(Long id) {
        repository.delete(findById(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
}
