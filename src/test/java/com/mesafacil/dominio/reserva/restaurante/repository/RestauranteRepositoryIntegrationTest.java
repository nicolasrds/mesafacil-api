package com.mesafacil.dominio.reserva.restaurante.repository;

import com.mesafacil.dominio.reserva.restaurante.enumeration.TipoDeCulinaria;
import com.mesafacil.dominio.reserva.restaurante.model.Restaurante;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RestauranteRepositoryIntegrationTest {

    @Autowired
    private RestauranteRepository restauranteRepository;


    @Test
    @Order(1)
    void devePermitirCriarTabela() {
        var totalRegistros = restauranteRepository.count();
        assertThat(totalRegistros).isGreaterThanOrEqualTo(0);
    }

    @Test
    @Order(2)
    void devePermitirRegistrarRestaurante() {
        //ARRANGE
        var restaurante = gerarRestaurante();
        restaurante.setId(1L);

        //ACT
        var restauranteSalvo = restauranteRepository.save(restaurante);

        //ASSERT
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

    @Test
    void devePermitirConsultarRestaurante() {
        //ARRANGE
        var restaurante = registraRestaurante();
        Long id = restaurante.getId();

        //ACT
        Optional<Restaurante> restauranteSalvo = restauranteRepository.findById(id);

        //ASSERT
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
    void devePermitirApagarRestaurantes() {
        //ARRANGE
        Long id = 1L;

        //ACT
        restauranteRepository.deleteById(id);
        Optional<Restaurante> restauranteSalvo = restauranteRepository.findById(id);

        //ASSERT
        assertThat(restauranteSalvo).isEmpty();

    }

    @Test
    void devePermitirConsultarRestaurantes() {

        //ACT
        //ASSERT
        assertThatCode(() -> {
            var resultado = restauranteRepository.findAll();
            assertThat(resultado)
                    .isInstanceOf(List.class);
        }).doesNotThrowAnyException();
    }

    private Restaurante gerarRestaurante() {
        return Restaurante.builder()
                .nome("Restaurante")
                .localizacao("Endereço")
                .tiposDeCulinaria(Arrays.asList(TipoDeCulinaria.values()))
                .build();
    }

    private Restaurante registraRestaurante() {
        Restaurante restaurante = gerarRestaurante();
        return restauranteRepository.save(restaurante);
    }
}