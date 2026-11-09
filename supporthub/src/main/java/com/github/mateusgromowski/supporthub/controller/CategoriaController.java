package com.github.mateusgromowski.supporthub.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.mateusgromowski.supporthub.dto.CategoriaDTO;
import com.github.mateusgromowski.supporthub.model.Categoria;
import com.github.mateusgromowski.supporthub.service.CategoriaService;

import jakarta.validation.Valid;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {
    private CategoriaService service;
    private ObjectMapper mapper;

    public CategoriaController(CategoriaService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<CategoriaDTO> findAll() {
        List<Categoria> categorias = service.findAll();
        List<CategoriaDTO> dtos = new ArrayList<>();
        for (Categoria categoria : categorias) {
            CategoriaDTO dto = mapper.convertValue(categoria, CategoriaDTO.class);
            dtos.add(dto);
        }
        return dtos;
    }

    @GetMapping("/{id}")
    public CategoriaDTO findById(@PathVariable Long id) {
        Categoria categoria = service.findById(id);
        return mapper.convertValue(categoria, CategoriaDTO.class);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CategoriaDTO dto) {
        Categoria categoria = mapper.convertValue(dto, Categoria.class);
        CategoriaDTO categoriaDTO = mapper.convertValue(service.create(categoria), CategoriaDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaDTO);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid CategoriaDTO dto) {
        Categoria categoria = mapper.convertValue(dto, Categoria.class);
        CategoriaDTO categoriaDTO = mapper.convertValue(service.update(categoria), CategoriaDTO.class);
        return ResponseEntity.ok(categoriaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}
