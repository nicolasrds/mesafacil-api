package com.mesafacil.dominio.reserva.avaliacao.repository;

import com.mesafacil.dominio.reserva.avaliacao.model.Avaliacao;
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

class AvaliacaoRepositoryTest {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

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
    void devePermitirRegistrarAvaliacao(){
        // Arrange
        var avaliacao = registrarAvaliacao();

        when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(avaliacao);

        // Act
        var avaliacaoRegistrada = avaliacaoRepository.save(avaliacao);

        // Assert
        assertThat(avaliacaoRegistrada)
                .isNotNull()
                .isEqualTo(avaliacao);
        verify(avaliacaoRepository, times(1)).save(any(Avaliacao.class));
    }

    @Test
    void devePermitirBuscarAvaliacao(){
        // Arrange
        var id = 1L;
        var avaliacao = registrarAvaliacao();

        when(avaliacaoRepository.findById(id))
                .thenReturn(Optional.of(avaliacao));

        // Act
        var avaliacaoRecebidaOpcional = avaliacaoRepository.findById(id);

        // Assert
        assertThat(avaliacaoRecebidaOpcional)
                .isPresent()
                .containsSame(avaliacao);
        avaliacaoRecebidaOpcional.ifPresent(avaliacaoRecebida -> {
            assertThat(avaliacaoRecebida.getId()).isEqualTo(avaliacao.getId());
            assertThat(avaliacaoRecebida.getNota()).isEqualTo(avaliacao.getNota());
        });
        verify(avaliacaoRepository, times(1)).findById(id);
    }

    @Test
    void devePermitirRemoverAvaliacao(){
        // Arrange
        var id = 1L;
        doNothing().when(avaliacaoRepository).deleteById(id);

        // Act
        avaliacaoRepository.deleteById(id);

        // Assert
        verify(avaliacaoRepository, times(1)).deleteById(id);
    }

    private Avaliacao registrarAvaliacao() {
        Restaurante restaurante = Restaurante.builder()
                .id(1L)
                .nome("Restaurante")
                .build();
        return Avaliacao.builder()
                .id(1L)
                .nota(4)
                .comentario("Comentário")
                .restaurante(restaurante)
                .build();
    }

}