package com.github.mateusgromowski.supporthub.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaDTO(
        Long id,
        @NotBlank String nome,
        @NotBlank String descricao) {

}
