package com.github.mateusgromowski.supporthub.dto;

import com.github.mateusgromowski.supporthub.enums.Perfil;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioResponseDTO(
        Long id,
        @NotBlank String nome,
        @Email @NotBlank String email,
        @NotNull Perfil perfil) {
}
