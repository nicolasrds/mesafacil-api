package com.mesafacil.application.resource.reserva.restaurante;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mesafacil.dominio.reserva.restaurante.model.Restaurante;
import com.mesafacil.dominio.reserva.restaurante.repository.RestauranteRepository;
import com.mesafacil.infra.exception.RegraDeNegocioException;
import com.mesafacil.util.DadosErro;
import com.mesafacil.util.DadosErroValidacao;
import com.mesafacil.dominio.reserva.restaurante.entity.HorarioFuncionamentoDto;
import com.mesafacil.dominio.reserva.restaurante.entity.RestauranteDto;
import com.mesafacil.dominio.reserva.restaurante.enumeration.TipoDeCulinaria;
import com.mesafacil.dominio.reserva.restaurante.service.RestauranteService;
import com.mesafacil.util.Util;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static com.mesafacil.dominio.reserva.restaurante.enumeration.DiaDaSemana.QUARTA_FEIRA;
import static com.mesafacil.util.Util.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = {"/clean.sql",
        "/data_restaurante.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class RestauranteResourceTest {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void deveConsultarRestaurantes() throws Exception {
        mockMvc.perform(get("/api/v1/reserva/restaurante"))
                .andExpect(status().isOk()) // Verificar status da resposta
                .andExpect(jsonPath("$.content", hasSize(greaterThan(0)))); // Verifica se a lista tem elementos
    }

    @Test
    void devePermitirRegistrarRestaurante() throws Exception {
        RestauranteDto restauranteDto = gerarRestaurante();

        MockHttpServletResponse response = mockMvc.perform(
                post("/api/v1/reserva/restaurante")
                        .content(objectMapper.writeValueAsString(restauranteDto))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void naoDevePermitirRegistrarRestaurante_Codigo400_NomeInvalido() throws Exception {
        RestauranteDto restauranteDto = gerarRestauranteNomeInvalido();

        MockHttpServletResponse response = mockMvc.perform(
                post("/api/v1/reserva/restaurante")
                        .content(objectMapper.writeValueAsString(restauranteDto))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        List<DadosErroValidacao> dadosErroValidacaoList = objectMapper.readValue(response.getContentAsString(),
                new TypeReference<List<DadosErroValidacao>>() {
                });

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("nome", dadosErroValidacaoList.get(0).campo());
    }

    @Test
    void devePermitirRegistrarHorarioFuncionamento() throws Exception {

        var restauramte = registraRestaurante();

        HorarioFuncionamentoDto horarioFuncionamentoDto = Util.preencherHorarioFuncionamentoDtoValido(restauramte.getId());
        MockHttpServletResponse response = mockMvc.perform(
                post("/api/v1/reserva/restaurante/registrar-horario")
                        .content(objectMapper.writeValueAsString(horarioFuncionamentoDto))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();


        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void naoDevePermitirRegistrarHorarioFuncionamento_Codigo400() throws Exception {

        HorarioFuncionamentoDto horarioFuncionamentoDto = Util.preencherHorarioFuncionamentoDtoInvalido();

        MockHttpServletResponse response = mockMvc.perform(
                post("/api/v1/reserva/restaurante/registrar-horario")
                        .content(objectMapper.writeValueAsString(horarioFuncionamentoDto))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();


        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }


    private RestauranteDto gerarRestaurante() {
        return new RestauranteDto(null, "Restaurante", "Endereço",
                Arrays.asList(TipoDeCulinaria.values()));
    }

    private RestauranteDto gerarRestauranteNomeInvalido() {
        return new RestauranteDto(null, "", "Endereço",
                Arrays.asList(TipoDeCulinaria.values()));
    }

    private Restaurante registraRestaurante() {
        Restaurante restaurante = Util.gerarRestaurante();
        return restauranteRepository.save(restaurante);
    }

}