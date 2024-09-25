package com.mesafacil.dominio.reserva.restaurante.repository;

import com.mesafacil.dominio.reserva.restaurante.enumeration.Status;
import com.mesafacil.dominio.reserva.restaurante.model.Mesa;
import com.mesafacil.dominio.reserva.restaurante.model.Restaurante;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MesaRepositoryTest {

    @Mock
    private MesaRepository mesaRepository;

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
    void devePermitirRegistrarMesa(){
        // Arrange
        var mesa = registrarMesa();

        when(mesaRepository.save(any(Mesa.class))).thenReturn(mesa);

        // Act
        var mesaRegistrada = mesaRepository.save(mesa);

        // Assert
        assertThat(mesaRegistrada)
                .isNotNull()
                        .isEqualTo(mesa);
        verify(mesaRepository, times(1)).save(any(Mesa.class));
    }

    @Test
    void devePermitirBuscarMesa(){
        // Arrange
        var id = 2222L;
        var mesa = registrarMesa();

        when(mesaRepository.findById(id))
                .thenReturn(Optional.of(mesa));

        // Act
        var mesaRecebidaOpcional = mesaRepository.findById(id);

        // Assert
        assertThat(mesaRecebidaOpcional)
                .isPresent()
                .containsSame(mesa);
        mesaRecebidaOpcional.ifPresent(mesaRecebida ->{
            assertThat(mesaRecebida.getId()).isEqualTo(mesa.getId());
            assertThat(mesaRecebida.getNumeroMesa()).isEqualTo(mesa.getNumeroMesa());
        });
        verify(mesaRepository, times(1)).findById(id);
    }

    @Test
    void devePermitirRemoverMesa(){
        // Arrange
        var id = 2222L;
        doNothing().when(mesaRepository).deleteById(id);
        // Act
        mesaRepository.deleteById(id);
        // Assert
        verify(mesaRepository, times(1)).deleteById(id);
    }

    private Mesa registrarMesa(){
        return Mesa.builder()
                .id(2222L)
                .numeroMesa(1)
                .restaurante(Restaurante.builder()
                            .id(1111L)
                            .nome("Restaurante A")
                            .localizacao("Endereço X, Rua Y, Loja Z")
//                          .tiposDeCulinaria("ITALIANA)
                            .build())
                .status(Status.DISPONIVEL)
                .build();
    }
}
