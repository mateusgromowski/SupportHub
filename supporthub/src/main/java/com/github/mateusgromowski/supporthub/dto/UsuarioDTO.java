package com.github.mateusgromowski.supporthub.dto;

import com.github.mateusgromowski.supporthub.enums.Perfil;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(
        Long id,
        @NotBlank String nome,
        @Email @NotBlank String email,
        @NotBlank String senha,
        @NotBlank Perfil perfil
) {}
