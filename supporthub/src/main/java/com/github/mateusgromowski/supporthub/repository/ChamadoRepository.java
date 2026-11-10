package com.github.mateusgromowski.supporthub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.mateusgromowski.supporthub.model.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

}
