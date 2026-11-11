package com.github.mateusgromowski.supporthub.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.mateusgromowski.supporthub.dto.ChamadoDTO;
import com.github.mateusgromowski.supporthub.model.Chamado;
import com.github.mateusgromowski.supporthub.service.ChamadoService;

import jakarta.validation.Valid;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/v1/chamados")
public class ChamadoController {
    private ChamadoService service;
    private ObjectMapper mapper;

    public ChamadoController(ChamadoService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ChamadoDTO> findAll() {
        List<Chamado> chamados = service.findAll();
        List<ChamadoDTO> dtos = new ArrayList<>();
        for (Chamado chamado : chamados) {
            ChamadoDTO dto = mapper.convertValue(chamado, ChamadoDTO.class);
            dtos.add(dto);
        }
        return dtos;
    }

    @GetMapping("/{id}")
    public ChamadoDTO findById(@PathVariable Long id) {
        Chamado chamado = service.findById(id);
        ChamadoDTO dto = mapper.convertValue(chamado, ChamadoDTO.class);
        System.out.println(dto);
        return dto;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ChamadoDTO chamadoDTO) {
        Chamado chamadoSalvo = service.create(chamadoDTO);
        ChamadoDTO novoDTO = mapper.convertValue(chamadoSalvo, ChamadoDTO.class);
        novoDTO.setSolicitanteId(chamadoSalvo.getSolicitante().getId());
        novoDTO.setCategoriaId(chamadoSalvo.getCategoria().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(novoDTO);
    }
}
