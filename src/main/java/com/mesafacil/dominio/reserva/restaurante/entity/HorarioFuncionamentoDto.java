package com.mesafacil.dominio.reserva.restaurante.entity;

import com.mesafacil.dominio.reserva.restaurante.enumeration.DiaDaSemana;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.List;

public record HorarioFuncionamentoDto(

        @NotNull
        Long idRestaurante,

        @NotBlank
        String descricao,

        @NotEmpty
        List<DiaDaSemana> listaDiaDaSemana,

        @NotNull
        LocalTime horaAbertura,

        @NotNull
        LocalTime horaFechamento
) {
        public boolean isHorarioAberturaValido(){
                return horaAbertura.getHour() < 6;
        }
}
