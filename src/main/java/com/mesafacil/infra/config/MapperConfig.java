package com.mesafacil.infra.config;

import com.mesafacil.dominio.reserva.avaliacao.mapper.AvaliacaoMapper;
import com.mesafacil.dominio.reserva.restaurante.mapper.HorarioFuncionamentoMapper;
import com.mesafacil.dominio.reserva.restaurante.mapper.MesaMapper;
import com.mesafacil.dominio.reserva.restaurante.mapper.ReservaMapper;
import com.mesafacil.dominio.reserva.restaurante.mapper.RestauranteMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public RestauranteMapper restauranteMapper() {
        return Mappers.getMapper(RestauranteMapper.class);
    }

    @Bean
    public AvaliacaoMapper avaliacaoMapper() {
        return Mappers.getMapper(AvaliacaoMapper.class);
    }

    @Bean
    public HorarioFuncionamentoMapper horarioFuncionamentoMapper() {
        return Mappers.getMapper(HorarioFuncionamentoMapper.class);
    }

    @Bean
    public MesaMapper mesaMapper() {
        return Mappers.getMapper(MesaMapper.class);
    }

    @Bean
    public ReservaMapper reservaMapper() {
        return Mappers.getMapper(ReservaMapper.class);
    }
}