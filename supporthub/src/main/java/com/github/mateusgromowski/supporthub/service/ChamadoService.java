package com.github.mateusgromowski.supporthub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.mateusgromowski.supporthub.exception.DadoInexistenteException;
import com.github.mateusgromowski.supporthub.model.Chamado;
import com.github.mateusgromowski.supporthub.repository.ChamadoRepository;

@Service
public class ChamadoService {
    private ChamadoRepository repository;

    public ChamadoService(ChamadoRepository repository) {
        this.repository = repository;
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
}
