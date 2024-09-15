package com.mesafacil.dominio.reserva.restaurante.useCase;

import com.mesafacil.dominio.reserva.restaurante.entity.HorarioFuncionamentoDto;
import com.mesafacil.dominio.reserva.restaurante.repository.HorarioFuncionamentoRepository;
import com.mesafacil.infra.exception.RegraDeNegocioException;
import com.mesafacil.infra.util.MessageService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChecarHorarioConflitanteUseCase {

    private final HorarioFuncionamentoRepository horarioFuncionamentoRepository;
    private final MessageService messageService;

    public void execute(HorarioFuncionamentoDto horarioFuncionamento) {
        boolean existeHorarioConflitante = horarioFuncionamento.listaDiaDaSemana().stream()
                .anyMatch(diaDaSemana -> horarioFuncionamentoRepository.existeHorarioConflitante(
                        horarioFuncionamento.horaAbertura(), diaDaSemana));
        if (existeHorarioConflitante) {
            throw new RegraDeNegocioException(messageService.getMessage("error.horario.conflitante"));
        }
    }

}