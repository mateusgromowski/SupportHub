package com.github.mateusgromowski.supporthub.mapper;

import org.springframework.stereotype.Component;

import com.github.mateusgromowski.supporthub.dto.ChamadoDTO;
import com.github.mateusgromowski.supporthub.model.Chamado;

import tools.jackson.databind.ObjectMapper;

@Component
public class ChamadoMapper {

    private final ObjectMapper mapper;

    public ChamadoMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public ChamadoDTO toDTO(Chamado chamado) {
        ChamadoDTO dto = mapper.convertValue(chamado, ChamadoDTO.class);

        dto.setSolicitanteId(chamado.getSolicitante().getId());
        dto.setCategoriaId(chamado.getCategoria().getId());

        return dto;
    }
}
