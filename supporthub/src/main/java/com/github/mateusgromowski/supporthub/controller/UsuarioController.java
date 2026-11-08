package com.github.mateusgromowski.supporthub.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.mateusgromowski.supporthub.dto.UsuarioDTO;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    @GetMapping
    public List<UsuarioDTO> findAll() {
        return null;
    }

    @GetMapping("/{id}")
    public UsuarioDTO findById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public void create(@RequestBody UsuarioDTO dto) {

    }

    @PutMapping("/{id}")
    public void update(@RequestBody UsuarioDTO dto) {

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

    }
}
