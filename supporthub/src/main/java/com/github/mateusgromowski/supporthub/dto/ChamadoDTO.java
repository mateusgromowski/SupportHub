package com.github.mateusgromowski.supporthub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.mateusgromowski.supporthub.enums.Prioridade;
import com.github.mateusgromowski.supporthub.enums.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChamadoDTO(
        Long id,
        @NotBlank String titulo,
        @NotNull String descricao,
        @NotNull Prioridade prioridade,
        @NotNull Status status,
        @JsonProperty("solicitante_id") @NotNull Long solicitanteId,
        @JsonProperty("categoria_id") @NotNull Long categoriaId) {

}
