package com.mesafacil.dominio.reserva.restaurante.entity;

import com.mesafacil.dominio.reserva.restaurante.enumeration.Status;
import jakarta.validation.constraints.NotNull;

public record MesaDto(

        @NotNull
        Long idMesa,

        @NotNull
        Long idRestaurante,

        @NotNull
        int numeroMesa,

        @NotNull
        Status status
) {
        public boolean isDisponivel(){
                return status == Status.DISPONIVEL;
        }
}