package com.mesafacil.dominio.reserva.restaurante.usecase;

import com.mesafacil.dominio.reserva.restaurante.entity.HorarioFuncionamentoDto;
import com.mesafacil.dominio.reserva.restaurante.repository.HorarioFuncionamentoRepository;
import com.mesafacil.infra.exception.RegraDeNegocioException;
import com.mesafacil.infra.util.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChecarHorarioConflitante implements UseCaseRestauranteHorario {

    private final HorarioFuncionamentoRepository horarioFuncionamentoRepository;
    private final MessageService messageService;

    @Override
    public void validar(HorarioFuncionamentoDto horarioFuncionamento) {
        boolean existeHorarioConflitante = horarioFuncionamento.listaDiaDaSemana().stream()
                .anyMatch(diaDaSemana -> horarioFuncionamentoRepository.existeHorarioConflitante(
                        horarioFuncionamento.horaAbertura(), diaDaSemana));
        if (existeHorarioConflitante) {
            throw new RegraDeNegocioException(messageService.getMessage("error.horario.conflitante"));
        }
    }

}