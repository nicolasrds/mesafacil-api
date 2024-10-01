package com.mesafacil.util;

import com.mesafacil.dominio.reserva.restaurante.entity.HorarioFuncionamentoDto;
import com.mesafacil.dominio.reserva.restaurante.enumeration.DiaDaSemana;
import com.mesafacil.dominio.reserva.restaurante.enumeration.TipoDeCulinaria;
import com.mesafacil.dominio.reserva.restaurante.model.Restaurante;
import com.mesafacil.dominio.reserva.restaurante.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static com.mesafacil.dominio.reserva.restaurante.enumeration.DiaDaSemana.QUARTA_FEIRA;

@Component
public class Util {
    public static final DiaDaSemana DIA_DA_SEMANA = DiaDaSemana.DOMINGO;
    public static final LocalTime HORA_INICIO = LocalTime.of(10, 0);
    public static final LocalTime HORA_FIM = LocalTime.of(11, 0);


    public static HorarioFuncionamentoDto preencherHorarioFuncionamentoDtoInvalido() {
        return new HorarioFuncionamentoDto(1L, "teste", List.of(QUARTA_FEIRA),
                LocalTime.of(3, 30), LocalTime.of(20, 30));
    }

    public static HorarioFuncionamentoDto preencherHorarioFuncionamentoDtoValido(Long idRestaurante) {
        return new HorarioFuncionamentoDto(idRestaurante, "teste", List.of(QUARTA_FEIRA),
                LocalTime.of(18, 30), LocalTime.of(20, 30));
    }

    public static Restaurante gerarRestaurante() {
        return Restaurante.builder()
                .nome("Restaurante")
                .localizacao("Endereço")
                .tiposDeCulinaria(Arrays.asList(TipoDeCulinaria.values()))
                .build();
    }
}
