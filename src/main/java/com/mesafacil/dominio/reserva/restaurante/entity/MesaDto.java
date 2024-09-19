package com.mesafacil.dominio.reserva.restaurante.entity;

import jakarta.validation.constraints.NotNull;

public record MesaDto(

        @NotNull
        Long idRestaurante,

//        @NotNull
//        Long idMesa,

        @NotNull
        int numeroMesa,

        @NotNull
        int capacidadeMesa
) {
}