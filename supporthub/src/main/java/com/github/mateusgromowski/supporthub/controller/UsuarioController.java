package com.github.mateusgromowski.supporthub.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.mateusgromowski.supporthub.dto.UsuarioDTO;
import com.github.mateusgromowski.supporthub.model.Usuario;
import com.github.mateusgromowski.supporthub.service.UsuarioService;

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
    public List<UsuarioDTO> findAll() {
        List<Usuario> usuarios = service.findAll();
        List<UsuarioDTO> dtos = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            UsuarioDTO dto = mapper.convertValue(usuario, UsuarioDTO.class);
            dtos.add(dto);
        }
        return dtos;
    }

    @GetMapping("/{id}")
    public UsuarioDTO findById(@PathVariable Long id) {
        Usuario usuario = service.findById(id);
        return mapper.convertValue(usuario, UsuarioDTO.class);
    }

    @PostMapping
    public void create(@RequestBody UsuarioDTO dto) {
        Usuario usuario = mapper.convertValue(dto, Usuario.class);
        service.create(usuario);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody UsuarioDTO dto) {
        Usuario usuario = mapper.convertValue(dto, Usuario.class);
        service.update(usuario);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
