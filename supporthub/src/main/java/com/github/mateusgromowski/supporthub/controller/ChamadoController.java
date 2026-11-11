package com.github.mateusgromowski.supporthub.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.mateusgromowski.supporthub.dto.ChamadoDTO;
import com.github.mateusgromowski.supporthub.mapper.ChamadoMapper;
import com.github.mateusgromowski.supporthub.model.Chamado;
import com.github.mateusgromowski.supporthub.service.ChamadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/chamados")
public class ChamadoController {
    private ChamadoService service;
    private ChamadoMapper mapper;

    public ChamadoController(ChamadoService service, ChamadoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ChamadoDTO> findAll() {
        List<Chamado> chamados = service.findAll();
        List<ChamadoDTO> dtos = new ArrayList<>();
        for (Chamado chamado : chamados) {
            ChamadoDTO dto = mapper.toDTO(chamado);
            dtos.add(dto);
        }
        return dtos;
    }

    @GetMapping("/{id}")
    public ChamadoDTO findById(@PathVariable Long id) {
        Chamado chamado = service.findById(id);
        ChamadoDTO dto = mapper.toDTO(chamado);
        System.out.println(dto);
        return dto;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ChamadoDTO chamadoDTO) {
        Chamado chamadoSalvo = service.create(chamadoDTO);
        ChamadoDTO novoDTO = mapper.toDTO(chamadoSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoDTO);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid ChamadoDTO chamadoDTO) {
        Chamado chamado = service.update(chamadoDTO);
        ChamadoDTO dto = mapper.toDTO(chamado);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{idChamado}/atribuir/{tecnicoId}")
    public ResponseEntity<?> atribuir(@PathVariable Long idChamado, @PathVariable Long tecnicoId,
            @RequestParam(value = "autor_id") Long autorId) {
        Chamado chamado = service.atribuir(idChamado, tecnicoId, autorId);
        return ResponseEntity.ok(mapper.toDTO(chamado));
    }
}
