package com.github.mateusgromowski.supporthub.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.mateusgromowski.supporthub.dto.ChamadoDTO;
import com.github.mateusgromowski.supporthub.enums.Perfil;
import com.github.mateusgromowski.supporthub.enums.Status;
import com.github.mateusgromowski.supporthub.exception.ChamadoFechadoException;
import com.github.mateusgromowski.supporthub.exception.ChamadoResolvidoException;
import com.github.mateusgromowski.supporthub.exception.DadoInexistenteException;
import com.github.mateusgromowski.supporthub.model.Chamado;
import com.github.mateusgromowski.supporthub.model.Usuario;
import com.github.mateusgromowski.supporthub.repository.ChamadoRepository;

import tools.jackson.databind.ObjectMapper;

@Service
public class ChamadoService {
    private ChamadoRepository repository;
    private UsuarioService usuarioService;
    private HistoricoChamadoService historicoChamadoService;
    private CategoriaService categoriaService;
    private ObjectMapper mapper;

    public ChamadoService(ChamadoRepository repository, UsuarioService usuarioService,
            HistoricoChamadoService historicoChamadoService, CategoriaService categoriaService, ObjectMapper mapper) {
        this.repository = repository;
        this.usuarioService = usuarioService;
        this.historicoChamadoService = historicoChamadoService;
        this.categoriaService = categoriaService;
        this.mapper = mapper;
    }

    public List<Chamado> findAll() {
        return repository.findAll();
    }

    public Chamado findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DadoInexistenteException("Chamado de ID " + id + " não encontrado."));
    }

    public Chamado create(ChamadoDTO chamadoDTO) {
        Chamado chamado = mapper.convertValue(chamadoDTO, Chamado.class);
        chamado.setDataAbertura(LocalDateTime.now());
        chamado.setSolicitante(usuarioService.findById(chamadoDTO.getSolicitanteId()));
        chamado.setCategoria(categoriaService.findById(chamadoDTO.getCategoriaId()));
        Chamado chamadoSalvo = repository.save(chamado);
        historicoChamadoService.registrarHistorico(chamadoSalvo, "Chamado aberto.",
                chamadoSalvo.getSolicitante().getId());
        return chamadoSalvo;
    }

    public Chamado update(Chamado chamado, Long autorId) {
        Chamado entity = findById(chamado.getId());
        verificaStatusFechado(entity);

        entity.setTitulo(chamado.getTitulo());
        entity.setDescricao(chamado.getDescricao());
        entity.setPrioridade(chamado.getPrioridade());
        entity.setCategoria(chamado.getCategoria());
        Chamado entitySalvo = repository.save(entity);
        historicoChamadoService.registrarHistorico(entity, "Chamado atualizado.", autorId);
        return entitySalvo;

    }

    public void atribuir(Long chamadoId, Long tecnicoId, Long autorId) {
        Chamado chamado = findById(chamadoId);
        verificaStatusFechado(chamado);
        verificaStatusResolvido(chamado);
        Usuario tecnico = usuarioService.findById(tecnicoId);
        verificaAptidao(tecnico);
        chamado.setTecnicoResponsavel(tecnico);
        repository.save(chamado);
        historicoChamadoService.registrarHistorico(chamado, "Técnico atribuído", autorId);
    }

    private void verificaAptidao(Usuario tecnico) {
        if (tecnico.getPerfil() != Perfil.TECNICO) {
            throw new DadoInexistenteException(
                    "O ID " + tecnico.getId() + " não é um tecnico apto para a ação.");
        }
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
