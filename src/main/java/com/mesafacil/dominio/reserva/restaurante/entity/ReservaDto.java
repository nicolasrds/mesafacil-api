package com.mesafacil.dominio.reserva.restaurante.entity;

import com.mesafacil.dominio.reserva.restaurante.enumeration.StatusReserva;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.util.Date;

public record ReservaDto (

    @NotNull
    Long idReserva,

    @NotNull
    String nomeCliente,

    @NotNull
    Long idRestaurante,

    @NotNull
    Date data,

    @NotNull
    Time horario,

    @NotNull
    Long idMesa,

    @NotNull
    StatusReserva descricao
) {
}
