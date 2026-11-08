package com.github.mateusgromowski.supporthub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.mateusgromowski.supporthub.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
