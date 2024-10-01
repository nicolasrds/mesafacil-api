package com.mesafacil.dominio.reserva.restaurante.usecase;

import com.mesafacil.dominio.reserva.restaurante.entity.HorarioFuncionamentoDto;
import com.mesafacil.dominio.reserva.restaurante.enumeration.DiaDaSemana;
import com.mesafacil.dominio.reserva.restaurante.repository.HorarioFuncionamentoRepository;
import com.mesafacil.infra.exception.RegraDeNegocioException;
import com.mesafacil.infra.util.MessageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ChecarHorarioConflitanteTest {

    private static final DiaDaSemana DIA_DA_SEMANA = DiaDaSemana.DOMINGO;
    private static final LocalTime HORA_INICIO = LocalTime.of(10, 0);
    private static final LocalTime HORA_FIM = LocalTime.of(11, 0);

    @InjectMocks
    private ChecarHorarioConflitante checarHorarioConflitante;

    @Mock
    private HorarioFuncionamentoRepository horarioFuncionamentoRepository;

    @Mock
    private MessageService messageService;


    @Nested
    class RegistrarRestaurante {
        @Test
        @DisplayName("Deve checar o horário e não permitir registrar")
        void naoDeveriaPermitirCadastrarHorario() {
            // Arrange
            HorarioFuncionamentoDto horarioFuncionamento = criarHorarioFuncionamento(true);

            // Act & Assert
            assertThrows(RegraDeNegocioException.class, () -> checarHorarioConflitante.validar(horarioFuncionamento));
        }

        @Test
        @DisplayName("Não deve lançar exceção quando horário não conflita")
        void deveriaPermitirCadastrarHorario() {
            // Arrange
            HorarioFuncionamentoDto horarioFuncionamento = criarHorarioFuncionamento(false);

            // Act & Assert
            assertDoesNotThrow(() ->
                    checarHorarioConflitante.validar(horarioFuncionamento)
            );

        }
    }

    private HorarioFuncionamentoDto criarHorarioFuncionamento(boolean existeConflitante) {
        HorarioFuncionamentoDto horarioFuncionamento = new HorarioFuncionamentoDto(null, "",
                List.of(DIA_DA_SEMANA), HORA_INICIO, HORA_FIM);
        when(horarioFuncionamentoRepository.existeHorarioConflitante(HORA_INICIO, DIA_DA_SEMANA))
                .thenReturn(existeConflitante);
        return horarioFuncionamento;
    }

}