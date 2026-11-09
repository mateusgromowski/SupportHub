package com.github.mateusgromowski.supporthub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.mateusgromowski.supporthub.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNome(String nome);

    boolean existsByNomeAndIdNot(String nome, Long id);
}
