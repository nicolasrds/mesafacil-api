package com.mesafacil.dominio.reserva.restaurante.usecase;

import com.mesafacil.dominio.reserva.restaurante.entity.HorarioFuncionamentoDto;
import com.mesafacil.dominio.reserva.restaurante.enumeration.DiaDaSemana;
import com.mesafacil.infra.exception.RegraDeNegocioException;
import com.mesafacil.infra.util.MessageService;
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

@ExtendWith(MockitoExtension.class)
class ChecarHorarioInvalidoTest {

    @InjectMocks
    private ChecarHorarioInvalido checarHorarioInvalido;

    @Mock
    private MessageService messageService;


    @Nested
    class RegistrarRestaurante {
        @Test
        void deveLancarExcecaoQuandoHorarioInvalido() {
            // Arrange
            HorarioFuncionamentoDto horarioFuncionamento = new HorarioFuncionamentoDto(null, "",
                    List.of(DiaDaSemana.DOMINGO), LocalTime.of(5, 0), LocalTime.of(11, 0));

            // Act & Assert
            assertThrows(RegraDeNegocioException.class, () -> checarHorarioInvalido.validar(horarioFuncionamento));
        }

        @Test
        void naoDeveLancarExcecaoQuandoHorarioValido() {
            // Arrange
            HorarioFuncionamentoDto horarioFuncionamento = new HorarioFuncionamentoDto(null, "",
                    List.of(DiaDaSemana.DOMINGO), LocalTime.of(6, 0), LocalTime.of(11, 0));

            // Act & Assert
            assertDoesNotThrow(() -> checarHorarioInvalido.validar(horarioFuncionamento));
        }
    }
}