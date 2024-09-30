package com.mesafacil.dominio.reserva.restaurante.repository;

import com.mesafacil.dominio.reserva.restaurante.enumeration.DisponibilidadeMesa;
import com.mesafacil.dominio.reserva.restaurante.enumeration.StatusReserva;
import com.mesafacil.dominio.reserva.restaurante.enumeration.TipoDeCulinaria;
import com.mesafacil.dominio.reserva.restaurante.model.Mesa;
import com.mesafacil.dominio.reserva.restaurante.model.Reserva;
import com.mesafacil.dominio.reserva.restaurante.model.Restaurante;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservaRepositoryTest {

    @Mock
    private ReservaRepository reservaRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirRegistrarReserva(){

        // Arrange
        var reserva = registrarReserva();

        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        // Act
        var reservaRegistrada = reservaRepository.save(reserva);

        // Assert
        assertThat(reservaRegistrada)
                .isNotNull()
                .isEqualTo(reserva);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void devePermitirBuscarReserva(){
        // Arrange
        var idReserva = 3333L;
        var reserva = registrarReserva();

        when(reservaRepository.findById(idReserva))
                .thenReturn(Optional.of(reserva));

        // Act
        var reservaRecebidaOpcional = reservaRepository.findById(idReserva);

        // Assert
        assertThat(reservaRecebidaOpcional)
                .isPresent()
                .containsSame(reserva);
        reservaRecebidaOpcional.ifPresent(reservaRecebida ->{
            assertThat(reservaRecebida.getIdReserva()).isEqualTo(reserva.getIdReserva());
        });
        verify(reservaRepository, times(1)).findById(idReserva);
    }

    @Test
    void devePermitirRemoverReserva(){
        // Arrange
        var idReserva = 3333L;
        doNothing().when(reservaRepository).deleteById(idReserva);
        // Act
        reservaRepository.deleteById(idReserva);
        // Assert
        verify(reservaRepository, times(1)).deleteById(idReserva);
    }

    private Reserva registrarReserva(){
        return Reserva.builder()
                .idReserva(3333L)
                .nomeCliente("José da Silva")
                .restaurante(Restaurante.builder()
                        .id(1111L)
                        .nome("Restaurante A")
                        .localizacao("Endereço X, Rua Y, Loja Z")
                        .build())
                .data(new Date(2024 - 1900, Calendar.OCTOBER, 10)) // Ano - 1900, Mês (0-11), Dia
                .horario(new Time(20 , 0, 0)) //20:00:00
                .mesa(Mesa.builder()
                        .idMesa(2222L)
                        .numeroMesa(1)
                        .restaurante(Restaurante.builder()
                                .id(1111L)
                                .nome("Restaurante A")
                                .localizacao("Endereço X, Rua Y, Loja Z")
                                .tiposDeCulinaria(Arrays.asList(TipoDeCulinaria.values()))
                                .build())
                        .descricao(DisponibilidadeMesa.DISPONIVEL)
                        .build())
                .descricao(StatusReserva.CONFIRMADA)
                .build();
    }
}
