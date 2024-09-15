package com.mesafacil.dominio.reserva.restaurante.mapper;

import com.mesafacil.dominio.reserva.restaurante.entity.HorarioFuncionamentoDto;
import com.mesafacil.dominio.reserva.restaurante.model.HorarioFuncionamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HorarioFuncionamentoMapper {

    HorarioFuncionamentoMapper INSTANCE = Mappers.getMapper(HorarioFuncionamentoMapper.class);

    @Mapping(source = "idRestaurante", target = "restaurante.id")
    HorarioFuncionamento dtoToEntity(HorarioFuncionamentoDto dto);
    @Mapping(source = "restaurante.id", target = "idRestaurante")
    HorarioFuncionamentoDto entityToDto(HorarioFuncionamento horarioFuncionamento);

}
