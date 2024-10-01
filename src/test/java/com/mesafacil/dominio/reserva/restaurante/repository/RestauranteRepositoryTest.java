package com.mesafacil.dominio.reserva.restaurante.repository;

import com.mesafacil.dominio.reserva.restaurante.enumeration.TipoDeCulinaria;
import com.mesafacil.dominio.reserva.restaurante.model.Restaurante;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteRepositoryTest {

    @Mock
    private RestauranteRepository restauranteRepository;


    @Nested
    class RegistrarRestaurante {
        @Test
        void devePermitirRegistrarRestaurante() {
            //ARRANGE
            var restaurante = gerarRestaurante();
            Mockito.when(restauranteRepository.save(Mockito.any(Restaurante.class))).thenReturn(restaurante);

            //ACT
            var restauranteSalvo = restauranteRepository.save(restaurante);

            //ASSERT
            verify(restauranteRepository, times(1)).save(Mockito.any(Restaurante.class));
            assertThat(restauranteSalvo)
                    .isInstanceOf(Restaurante.class)
                    .isNotNull()
                    .isEqualTo(restaurante);
            assertThat(restauranteSalvo)
                    .extracting(Restaurante::getId)
                    .isEqualTo(restaurante.getId());
            assertThat(restauranteSalvo)
                    .extracting(Restaurante::getNome)
                    .isEqualTo(restaurante.getNome());
        }
    }


    @Nested
    class ConsultarRestaurante {
        @Test
        void devePermitirConsultarRestaurante() {
            //ARRANGE
            Long id = 1L;
            var restaurante = gerarRestaurante();
            restaurante.setId(id);

            Mockito.when(restauranteRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(restaurante));

            //ACT
            Optional<Restaurante> restauranteSalvo = restauranteRepository.findById(id);

            //ASSERT
            verify(restauranteRepository, times(1)).findById(id);

            assertThat(restauranteSalvo)
                    .isPresent()
                    //Verifica se o valor contido no Optional é o mesmo objeto (não apenas equivalente) que o objeto restaurante.
                    .containsSame(restaurante);

            restauranteSalvo.ifPresent(restauranteArmazenado -> {
                        assertThat(restauranteArmazenado.getId()).isEqualTo(id);
                        assertThat(restauranteArmazenado.getNome()).isEqualTo(restaurante.getNome());
                    }
            );
        }

        @Test
        void devePermitirConsultarRestaurantes() {
            //ARRANGE
            var restaurante1 = gerarRestaurante();
            var restaurante2 = gerarRestaurante();
            var listaRestaurantes = Arrays.asList(restaurante1, restaurante2);

            Mockito.when(restauranteRepository.findAll()).thenReturn(listaRestaurantes);

            //ACT
            var resultado = restauranteRepository.findAll();

            //ASSERT
            verify(restauranteRepository, times(1)).findAll();
            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(restaurante1, restaurante2);
        }
    }

    @Nested
    class ApagarRestaurante {

        @Test
        void devePermitirApagarRestaurantes() {
            //ARRANGE
            Long id = new Random().nextLong();
            /**
             * doNothing(): Configura o Mockito para não fazer nada quando um método específico for chamado.
             * Isso é útil quando você está testando código que interage com um objeto, mas não quer que o método real
             * seja executado. Em vez disso, ele "finge" que foi chamado sem realizar qualquer ação.
             */
            doNothing().when(restauranteRepository).deleteById(id);

            //ACT
            restauranteRepository.deleteById(id);

            //ASSERT
            verify(restauranteRepository, times(1)).deleteById(id);
        }
    }


    private Restaurante gerarRestaurante() {
        return Restaurante.builder()
                .nome("Restaurante")
                .localizacao("Endereço")
                .tiposDeCulinaria(Arrays.asList(TipoDeCulinaria.values()))
                .build();
    }
}