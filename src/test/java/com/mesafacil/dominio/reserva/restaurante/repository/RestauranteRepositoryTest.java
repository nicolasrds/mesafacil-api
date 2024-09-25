package com.mesafacil.dominio.reserva.restaurante.repository;

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

public class RestauranteRepositoryTest {

    @Mock
    private RestauranteRepository restauranteRepository;

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
    void devePermitirRegistrarRestaurante(){
        // Arrange
        var restaurante = registrarRestaurante();

        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);

        //Act
        var restauranteRegistrado = restauranteRepository.save(restaurante);

        //Assert
        assertThat(restauranteRegistrado)
                .isNotNull()
                .isEqualTo(restaurante);
        verify(restauranteRepository, times(1)).save(any(Restaurante.class));
    }

    @Test
    void devePermitirRemoverRestaurante(){
        // Arrange
        var id = 1111L;
        doNothing().when(restauranteRepository).deleteById(id);
        // Act
        restauranteRepository.deleteById(id);
        // Assert
        verify(restauranteRepository, times(1)).deleteById(id);
    }

    @Test
    void devePermitirBuscarRestaurante(){
        // Arrange
        var id = 1111L;
        var restaurante = registrarRestaurante();

        when(restauranteRepository.findById(id))
                .thenReturn(Optional.of(restaurante));

        // Act
        var mesaRecebidaOpcional = restauranteRepository.findById(id);

        // Assert
        assertThat(mesaRecebidaOpcional)
                .isPresent()
                .containsSame(restaurante);
        mesaRecebidaOpcional.ifPresent(mesaRecebida ->{
            assertThat(mesaRecebida.getId()).isEqualTo(restaurante.getId());
            assertThat(mesaRecebida.getNome()).isEqualTo(restaurante.getNome());
        });
        verify(restauranteRepository, times(1)).findById(id);
    }

    private Restaurante registrarRestaurante() {
        return Restaurante.builder()
                .id(1111L)
                .nome("Restaurante A")
                .localizacao("Endereço X, Rua Y, Loja Z")
//                .tiposDeCulinaria("ITALIANA);
                .build();
    }
}
