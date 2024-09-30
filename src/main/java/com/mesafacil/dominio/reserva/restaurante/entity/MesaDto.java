package com.mesafacil.dominio.reserva.restaurante.entity;

import com.mesafacil.dominio.reserva.restaurante.enumeration.DisponibilidadeMesa;
import jakarta.validation.constraints.NotNull;

public record MesaDto(

        @NotNull
        Long idMesa,

        @NotNull
        Long idRestaurante,

        @NotNull
        int numeroMesa,

        @NotNull
        DisponibilidadeMesa descricao
) {
//        public boolean isDisponivel(){
//                return descricao == DisponibilidadeMesa.DISPONIVEL;
//        }
}