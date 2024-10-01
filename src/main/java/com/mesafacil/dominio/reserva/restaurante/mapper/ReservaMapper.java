package com.mesafacil.dominio.reserva.restaurante.mapper;

import com.mesafacil.dominio.reserva.restaurante.entity.ReservaDto;
import com.mesafacil.dominio.reserva.restaurante.model.Reserva;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ReservaMapper {

    //    ReservaMapper INSTANCE = Mappers.getMapper(ReservaMapper.class);

    Reserva dtoToEntity(ReservaDto dto);

    ReservaDto entityToDto(Reserva entity);

    List<ReservaDto> toDtoList(List<Reserva> reservas);

}
