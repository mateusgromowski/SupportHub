package com.github.mateusgromowski.supporthub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.mateusgromowski.supporthub.enums.Perfil;
import com.github.mateusgromowski.supporthub.exception.DadoInexistenteException;
import com.github.mateusgromowski.supporthub.model.Chamado;
import com.github.mateusgromowski.supporthub.model.Usuario;
import com.github.mateusgromowski.supporthub.repository.ChamadoRepository;
import com.github.mateusgromowski.supporthub.repository.UsuarioRepository;

@Service
public class ChamadoService {
    private ChamadoRepository repository;
    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    public ChamadoService(ChamadoRepository repository, UsuarioRepository usuarioRepository,
            UsuarioService usuarioService) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    public List<Chamado> findAll() {
        return repository.findAll();
    }

    public Chamado findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DadoInexistenteException("Chamado de ID " + id + " não encontrado."));
    }

    public Chamado create(Chamado chamado) {
        return repository.save(chamado);
    }

    public Chamado update(Chamado chamado) {
        Chamado entity = findById(chamado.getId());

        entity.setTitulo(chamado.getTitulo());
        entity.setDescricao(chamado.getDescricao());
        entity.setStatus(chamado.getStatus());
        entity.setPrioridade(chamado.getPrioridade());
        entity.setCategoria(chamado.getCategoria());

        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    public void atribuir(Long chamadoId, Long tecnicoId) {
        Chamado chamado = findById(chamadoId);
        Usuario tecnico = usuarioService.findById(tecnicoId);
        if (!usuarioRepository.existsByIdAndPerfil(tecnicoId, Perfil.TECNICO)) {
            throw new DadoInexistenteException(
                    "O ID " + tecnicoId + " não é um tecnico apto para a ação.");
        }
        chamado.setTecnicoResponsavel(tecnico);
        repository.save(chamado);
    }
}
