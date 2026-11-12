package com.github.mateusgromowski.supporthub.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.mateusgromowski.supporthub.dto.UsuarioRequestDTO;
import com.github.mateusgromowski.supporthub.dto.UsuarioResponseDTO;
import com.github.mateusgromowski.supporthub.model.Usuario;
import com.github.mateusgromowski.supporthub.service.UsuarioService;

import jakarta.validation.Valid;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    private UsuarioService service;
    private ObjectMapper mapper;

    public UsuarioController(UsuarioService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<UsuarioResponseDTO> findAll() {
        List<Usuario> usuarios = service.findAll();
        List<UsuarioResponseDTO> dtos = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            UsuarioResponseDTO dto = mapper.convertValue(usuario, UsuarioResponseDTO.class);
            dtos.add(dto);
        }
        return dtos;
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO findById(@PathVariable Long id) {
        Usuario usuario = service.findById(id);
        return mapper.convertValue(usuario, UsuarioResponseDTO.class);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UsuarioRequestDTO dto) {
        Usuario usuario = mapper.convertValue(dto, Usuario.class);
        usuario.setDataCadastro(LocalDateTime.now());
        UsuarioResponseDTO response = mapper.convertValue(service.create(usuario), UsuarioResponseDTO.class);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid UsuarioRequestDTO dto) {
        Usuario usuario = mapper.convertValue(dto, Usuario.class);
        UsuarioResponseDTO response = mapper.convertValue(service.update(usuario), UsuarioResponseDTO.class);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
