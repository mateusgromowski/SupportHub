package com.github.mateusgromowski.supporthub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.mateusgromowski.supporthub.enums.Prioridade;
import com.github.mateusgromowski.supporthub.enums.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChamadoDTO {

        private Long id;

        @NotBlank
        private String titulo;

        @NotBlank
        private String descricao;

        @NotNull
        private Prioridade prioridade;

        @NotNull
        private Status status;

        @JsonProperty("solicitante_id")
        @NotNull
        private Long solicitanteId;

        @JsonProperty("categoria_id")
        @NotNull
        private Long categoriaId;

}
