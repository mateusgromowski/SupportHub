package com.github.mateusgromowski.supporthub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.mateusgromowski.supporthub.enums.Perfil;
import com.github.mateusgromowski.supporthub.enums.Status;
import com.github.mateusgromowski.supporthub.exception.ChamadoFechadoException;
import com.github.mateusgromowski.supporthub.exception.ChamadoResolvidoException;
import com.github.mateusgromowski.supporthub.exception.DadoInexistenteException;
import com.github.mateusgromowski.supporthub.model.Chamado;
import com.github.mateusgromowski.supporthub.model.Usuario;
import com.github.mateusgromowski.supporthub.repository.ChamadoRepository;

@Service
public class ChamadoService {
    private ChamadoRepository repository;
    private UsuarioService usuarioService;
    private HistoricoChamadoService historicoChamadoService;

    public ChamadoService(ChamadoRepository repository,
            UsuarioService usuarioService,
            HistoricoChamadoService historicoChamadoService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
        this.historicoChamadoService = historicoChamadoService;
    }

    public List<Chamado> findAll() {
        return repository.findAll();
    }

    public Chamado findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DadoInexistenteException("Chamado de ID " + id + " não encontrado."));
    }

    public Chamado create(Chamado chamado, Usuario autor) {
        Chamado chamadoSalvo = repository.save(chamado);
        historicoChamadoService.registrarHistorico(chamadoSalvo, "Chamado aberto.", autor);
        return chamadoSalvo;
    }

    public Chamado update(Chamado chamado, Usuario autor) {
        Chamado entity = findById(chamado.getId());
        verificaStatusFechado(entity);

        entity.setTitulo(chamado.getTitulo());
        entity.setDescricao(chamado.getDescricao());
        entity.setPrioridade(chamado.getPrioridade());
        entity.setCategoria(chamado.getCategoria());
        Chamado entitySalvo = repository.save(entity);
        historicoChamadoService.registrarHistorico(entity, "Chamado atualizado.", autor);
        return entitySalvo;

    }

    public void atribuir(Long chamadoId, Long tecnicoId, Usuario autor) {
        Chamado chamado = findById(chamadoId);
        verificaStatusFechado(chamado);
        verificaStatusResolvido(chamado);
        Usuario tecnico = usuarioService.findById(tecnicoId);
        if (tecnico.getPerfil() != Perfil.TECNICO) {
            throw new DadoInexistenteException(
                    "O ID " + tecnicoId + " não é um tecnico apto para a ação.");
        }
        chamado.setTecnicoResponsavel(tecnico);
        repository.save(chamado);
        historicoChamadoService.registrarHistorico(chamado, "Técnico atribuído", autor);
    }

    public void verificaStatusFechado(Chamado chamado) {
        if (chamado.getStatus() == Status.FECHADO) {
            throw new ChamadoFechadoException("O chamado está fechado e não aceita mais alterações.");
        }

    }

    public void verificaStatusResolvido(Chamado chamado) {
        if (chamado.getStatus() == Status.RESOLVIDO) {
            throw new ChamadoResolvidoException("O chamado está resolvido e não aceita mais atribuições.");
        }
    }

}
