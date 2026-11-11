package com.github.mateusgromowski.supporthub.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import com.github.mateusgromowski.supporthub.exception.DadoInexistenteException;
import com.github.mateusgromowski.supporthub.model.Chamado;
import com.github.mateusgromowski.supporthub.model.HistoricoChamado;
import com.github.mateusgromowski.supporthub.repository.HistoricoChamadoRepository;

@Service
public class HistoricoChamadoService {
    private HistoricoChamadoRepository repository;
    private UsuarioService usuarioService;

    public HistoricoChamadoService(HistoricoChamadoRepository repository, UsuarioService usuarioService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
    }

    public List<HistoricoChamado> findAll() {
        return repository.findAll();
    }

    public HistoricoChamado findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new DadoInexistenteException("Chamado de ID " + id + " não encontrado no histórico."));
    }

    public void create(HistoricoChamado historicoChamado) {
        repository.save(historicoChamado);
    }

    public void registrarHistorico(Chamado chamado, String descricao, Long autorId) {
        HistoricoChamado historicoChamado = HistoricoChamado.builder()
                .chamado(chamado)
                .descricao(descricao)
                .autor(usuarioService.findById(autorId))
                .dataEvento(LocalDateTime.now())
                .build();
        this.create(historicoChamado);
    }
}
