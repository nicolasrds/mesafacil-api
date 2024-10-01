package com.mesafacil.dominio.reserva.avaliacao.entity;

import com.mesafacil.dominio.reserva.restaurante.entity.RestauranteDto;

public record AvaliacaoDto(
        Long id,
        RestauranteDto restaurante,
        Integer nota,
        String comentario
) {
}
