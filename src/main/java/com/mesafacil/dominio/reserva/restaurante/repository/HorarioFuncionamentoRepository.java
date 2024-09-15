package com.mesafacil.dominio.reserva.restaurante.repository;

import com.mesafacil.dominio.reserva.restaurante.enumeration.DiaDaSemana;
import com.mesafacil.dominio.reserva.restaurante.model.HorarioFuncionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;

public interface HorarioFuncionamentoRepository extends JpaRepository<HorarioFuncionamento, Long> {

    @Query("SELECT COUNT(hf.id) > 0 FROM HorarioFuncionamento hf " +
            "JOIN hf.listaDiaDaSemana dia " +
            "WHERE dia = :diaSemana " +
            "AND :horaAbertura BETWEEN hf.horaAbertura AND hf.horaFechamento")
    boolean existeHorarioConflitante(@Param("horaAbertura") LocalTime horaAbertura, @Param("diaSemana") DiaDaSemana diaDaSemana);
}

