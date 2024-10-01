package com.mesafacil.dominio.reserva.restaurante.service;

import com.mesafacil.dominio.reserva.restaurante.entity.HorarioFuncionamentoDto;
import com.mesafacil.dominio.reserva.restaurante.enumeration.DiaDaSemana;
import com.mesafacil.dominio.reserva.restaurante.mapper.HorarioFuncionamentoMapper;
import com.mesafacil.dominio.reserva.restaurante.model.HorarioFuncionamento;
import com.mesafacil.dominio.reserva.restaurante.model.Restaurante;
import com.mesafacil.dominio.reserva.restaurante.repository.HorarioFuncionamentoRepository;
import com.mesafacil.dominio.reserva.restaurante.repository.RestauranteRepository;
import com.mesafacil.dominio.reserva.restaurante.usecase.UseCaseRestauranteHorario;
import com.mesafacil.infra.exception.RegraDeNegocioException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.mesafacil.dominio.reserva.restaurante.enumeration.DiaDaSemana.QUARTA_FEIRA;
import static com.mesafacil.util.Util.*;
import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RestauranteServiceTest {

    @InjectMocks
    private RestauranteService service;

    @Mock
    private HorarioFuncionamentoRepository horarioFuncionamentoRepository;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private Restaurante restaurante;

    @Mock
    private HorarioFuncionamentoMapper horarioFuncionamentoMapper;

    @Mock
    private Validator validator;

    @Mock
    private List<UseCaseRestauranteHorario> useCaseRestauranteHorarios;


    @BeforeEach
    void setUp() {
        // Inicializa um Validator real, baseado na implementação padrão
        ValidatorFactory factory = buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    class RegistrarRestaurante {
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
        void deveRegistrarHorarioFuncionamento() {

            // arrange
            HorarioFuncionamentoDto dto = preencherHorarioFuncionamentoDto();
            HorarioFuncionamento horarioFuncionamento = new HorarioFuncionamento();
            when(horarioFuncionamentoMapper.dtoToEntity(dto)).thenReturn(horarioFuncionamento);

            // act
            HorarioFuncionamento result = service.registrarHorarioFuncionamento(dto);

            // assert
            assertThat(result).isNotNull();
            verify(horarioFuncionamentoRepository, times(1)).save(horarioFuncionamento);

//        // Simula o comportamento do mock sem lançar exceção
//        Mockito.doNothing().when(checarHorarioConflitante).validar(dto);
//        Mockito.doNothing().when(checarHorarioInvalido).validar(dto);
        }

        @Test
        void naoDeveRegistrarHorarioFuncionamento() {

            HorarioFuncionamentoDto dto = criarHorarioFuncionamento();

            // Simulando que a validação lança uma exceção
            doThrow(new RegraDeNegocioException("Erro de validação")).when(useCaseRestauranteHorarios).forEach(any());

            // Verificação de exceção
            RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> {
                service.registrarHorarioFuncionamento(dto);
            });

            // Verificações
            assertEquals("Erro de validação", exception.getMessage());
            verify(horarioFuncionamentoRepository, never()).save(any(HorarioFuncionamento.class)); // Não deve ser salvo

        }
    }

    @Nested
    class ConsultarRestaurante {
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

        @Test
        void deveGerarExcecaoQuandoConsultarRestaurantes() {
            //ARRANGE
            Mockito.when(restauranteRepository.findById(1L)).thenReturn(Optional.empty());

            //ASSERT
            assertThatThrownBy(() -> service.buscarPor(1L)).isInstanceOf(RegraDeNegocioException.class).hasMessage("Restaurante não encontrada");
            verify(restauranteRepository, Mockito.times(1)).findById(1L);
        }
    }


    private HorarioFuncionamentoDto criarHorarioFuncionamento() {
        HorarioFuncionamentoDto horarioFuncionamento = new HorarioFuncionamentoDto(1L, "",
                List.of(DIA_DA_SEMANA), HORA_INICIO, HORA_FIM);
        return horarioFuncionamento;
    }

    private HorarioFuncionamentoDto preencherHorarioFuncionamentoDto() {
        return new HorarioFuncionamentoDto(1L, "teste", List.of(QUARTA_FEIRA),
                LocalTime.of(3, 30), LocalTime.of(20, 30));
    }

    private boolean checarExceptionNomeCampo(String nomeCampo, Restaurante restaurante) {
        for (ConstraintViolation<Restaurante> violation : validator.validate(restaurante)) {
            if (violation.getPropertyPath().toString().equals(nomeCampo)) {
                return true;
            }
        }
        return false;
    }


}