package com.mesafacil.dominio.reserva.restaurante.service;

import com.mesafacil.dominio.reserva.restaurante.entity.HorarioFuncionamentoDto;
import com.mesafacil.dominio.reserva.restaurante.enumeration.DiaDaSemana;
import com.mesafacil.dominio.reserva.restaurante.enumeration.TipoDeCulinaria;
import com.mesafacil.dominio.reserva.restaurante.mapper.HorarioFuncionamentoMapper;
import com.mesafacil.dominio.reserva.restaurante.model.HorarioFuncionamento;
import com.mesafacil.dominio.reserva.restaurante.model.Restaurante;
import com.mesafacil.dominio.reserva.restaurante.repository.HorarioFuncionamentoRepository;
import com.mesafacil.dominio.reserva.restaurante.repository.RestauranteRepository;
import com.mesafacil.dominio.reserva.restaurante.usecase.UseCaseRestauranteHorario;
import com.mesafacil.util.Util;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static com.mesafacil.util.Util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RestauranteServiceIT {

    @InjectMocks
    private RestauranteService service;

    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private HorarioFuncionamentoRepository horarioFuncionamentoRepository;

    @Mock
    private HorarioFuncionamentoMapper horarioFuncionamentoMapper;
    @Mock
    private UseCaseRestauranteHorario useCaseMock;


    @BeforeEach
    void setup() {
        // Inicializar a lista de mocks para useCaseRestauranteHorarios
        service = new RestauranteService(horarioFuncionamentoRepository,
                restauranteRepository, horarioFuncionamentoMapper, Arrays.asList(useCaseMock));
    }

    @Test
    @Order(1)
    void devePermitirCriarTabela() {
        var totalRegistros = restauranteRepository.count();
        assertThat(totalRegistros).isNotNegative();
    }


    @Nested
    class RegistrarRestaurante {

        @Test
        void deveCadastrarRestaurante() {
            // arrange
            Restaurante restaurante = gerarRestaurante();

            // act
            service.cadastrar(restaurante);

            //assert
            assertThat(restauranteRepository.findById(restaurante.getId())).isPresent();
        }

        @Test
        void deveRegistrarHorarioFuncionamento() {
            // arrange
            HorarioFuncionamentoDto dto = criarHorarioFuncionamento();
            HorarioFuncionamento horario = HorarioFuncionamento.builder()
                    .restaurante(restauranteRepository.findById(dto.idRestaurante()).get())
                    .horaAbertura(dto.horaAbertura())
                    .horaFechamento(dto.horaFechamento())
                    .descricao(dto.descricao())
                    .listaDiaDaSemana(dto.listaDiaDaSemana())
                    .build();

            Mockito.when(horarioFuncionamentoMapper.dtoToEntity(dto)).thenReturn(horario);
//        doNothing().when(useCaseMock).validar(dto);

            // act
            horario = service.registrarHorarioFuncionamento(dto);

            //assert
            HorarioFuncionamento savedHorario = horarioFuncionamentoRepository.findById(horario.getId()).orElse(null);
            assertNotNull(savedHorario);  // Verifica se o objeto foi salvo corretamente no banco
            verify(useCaseMock, times(1)).validar(dto);  // Verifica se o caso de uso foi chamad
        }
    }


    @Nested
    class ConsultarRestaurante {
        @Test
        void deveConsultarRestaurantes() {
            Restaurante restaurante = gerarRestaurante();
            restauranteRepository.save(restaurante);
            Pageable pageable = PageRequest.of(0, 10);

            // act
            Page<Restaurante> result = service.consultar(pageable);

            // Assert
            assertFalse(result.getContent().isEmpty()); // Verifica se a quantidade de itens na página está correta
        }

        @Test
        void deveBuscarRestaurantePorId() {
            //
            Restaurante restaurante = gerarRestaurante();
            restauranteRepository.save(restaurante);

            // act
            Restaurante result = service.buscarPor(restaurante.getId());

            // assert
            assertThat(result).isEqualTo(restaurante);
        }

        @Test
        @Sql(scripts = {"/clean.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        void devePermitirListarTodRestaurantes_QuandoNaoExisteRegistro() {
            Page<Restaurante> listaDeRestaurantes = service.consultar(Pageable.unpaged());
            assertThat(listaDeRestaurantes).isEmpty();
        }
    }


    @Nested
    class ApagarRestaurante {
        @Test
        void deveDeletarRestaurante() {
            // arrange
            Restaurante restaurante = gerarRestaurante();
            restauranteRepository.save(restaurante);

            // act
            service.deletar(restaurante.getId());

            // assert
            assertThat(restauranteRepository.findById(restaurante.getId())).isEmpty();
        }
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

    private HorarioFuncionamentoDto criarHorarioFuncionamento() {
        Restaurante restaurante = registraRestaurante();
        var horarioFuncionamento = new HorarioFuncionamentoDto(restaurante.getId(), "teste",
                List.of(DIA_DA_SEMANA), HORA_INICIO, HORA_FIM);
        return horarioFuncionamento;
    }

}
