package com.mesafacil.dominio.reserva.restaurante.service;

import com.mesafacil.dominio.reserva.restaurante.mapper.HorarioFuncionamentoMapper;
import com.mesafacil.dominio.reserva.restaurante.model.Restaurante;
import com.mesafacil.dominio.reserva.restaurante.repository.HorarioFuncionamentoRepository;
import com.mesafacil.dominio.reserva.restaurante.repository.RestauranteRepository;
import com.mesafacil.dominio.reserva.restaurante.useCase.UseCaseRestauranteHorario;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class RestauranteServiceTest {

    @InjectMocks
    private RestauranteService service;

    @Mock
    private  HorarioFuncionamentoRepository horarioFuncionamentoRepository;

    @Mock
    private  RestauranteRepository restauranteRepository;

    @Mock
    private  HorarioFuncionamentoMapper horarioFuncionamentoMapper;

    @Mock
    private  List<UseCaseRestauranteHorario> useCaseRestauranteHorarios;

    @Mock
    private Restaurante restaurante;

    @Mock
    private Validator validator;

    @BeforeEach
    void setUp() {
        // Inicializa um Validator real, baseado na implementação padrão
        ValidatorFactory factory = buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void naoDeveCadastrarComNomeInvalido() {
        //ARRANGE
        Restaurante restauranteSemNome = new Restaurante();
        restauranteSemNome.setNome(null);
        restauranteSemNome.setLocalizacao("");
        //ACT+ASSERT
        assertTrue(checarExceptionNomeCampo("nome", restauranteSemNome));
  }

    @Test
    void deveCadastrarRestaurante() {
        assertDoesNotThrow(() -> service.cadastrar(restaurante));
        then(restauranteRepository).should().save(restaurante);
    }

    @Test
    void deveConsultarRestaurantesPaginados() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Restaurante> restaurantes = List.of(new Restaurante(), new Restaurante());
        Page<Restaurante> restaurantePage = new PageImpl<>(restaurantes, pageable, restaurantes.size());

        // Simula o comportamento do repositório
        given(restauranteRepository.findAll(any(Pageable.class))).willReturn(restaurantePage);

        // Act
        Page<Restaurante> result = service.consultar(pageable);

        // Assert
        assertEquals(2, result.getContent().size()); // Verifica se a quantidade de itens na página está correta
        assertEquals(restaurantePage, result); // Verifica se o retorno do serviço é o esperado
        verify(restauranteRepository).findAll(pageable); // Verifica se o método findAll foi chamado com o Pageable correto
    }

    private boolean checarExceptionNomeCampo(String nomeCampo, Restaurante restaurante ){
        for (ConstraintViolation<Restaurante> violation : validator.validate(restaurante)) {
            if (violation.getPropertyPath().toString().equals(nomeCampo)) {
                return true;
            }
        }
        return false;
    }
}