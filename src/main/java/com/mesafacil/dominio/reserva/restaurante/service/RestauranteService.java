package com.mesafacil.dominio.reserva.restaurante.service;

import com.mesafacil.dominio.reserva.restaurante.entity.HorarioFuncionamentoDto;
import com.mesafacil.dominio.reserva.restaurante.mapper.HorarioFuncionamentoMapper;
import com.mesafacil.dominio.reserva.restaurante.model.HorarioFuncionamento;
import com.mesafacil.dominio.reserva.restaurante.model.Restaurante;
import com.mesafacil.dominio.reserva.restaurante.repository.HorarioFuncionamentoRepository;
import com.mesafacil.dominio.reserva.restaurante.repository.RestauranteRepository;
import com.mesafacil.dominio.reserva.restaurante.useCase.ChecarHorarioConflitanteUseCase;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RestauranteService {

    private final HorarioFuncionamentoRepository horarioFuncionamentoRepository;
    private final RestauranteRepository restauranteRepository;
    private final ChecarHorarioConflitanteUseCase checarHorarioConflitanteUseCase;
    private final HorarioFuncionamentoMapper horarioFuncionamentoMapper;

    public void cadastrar(Restaurante restaurante) {
        restauranteRepository.save(restaurante);
    }

    public HorarioFuncionamento registrarHorarioFuncionamento(HorarioFuncionamentoDto horarioFuncionamentoDto) {
        checarHorarioConflitanteUseCase.execute(horarioFuncionamentoDto);
        HorarioFuncionamento horarioFuncionamento = horarioFuncionamentoMapper.dtoToEntity(horarioFuncionamentoDto);
        horarioFuncionamentoRepository.save(horarioFuncionamento);
        return horarioFuncionamento;
    }




}
