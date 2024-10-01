package com.mesafacil.dominio.reserva.restaurante.usecase;

import com.mesafacil.dominio.reserva.restaurante.entity.HorarioFuncionamentoDto;
import com.mesafacil.infra.exception.RegraDeNegocioException;
import com.mesafacil.infra.util.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ChecarHorarioInvalido implements UseCaseRestauranteHorario {

    private final MessageService messageService;

    @Override
    public void validar(HorarioFuncionamentoDto horarioFuncionamento) {
        if (horarioFuncionamento.isHorarioAberturaValido()) {
            throw new RegraDeNegocioException(messageService.getMessage("error.horario.abertura.invalido" ));
        }
    }
}
