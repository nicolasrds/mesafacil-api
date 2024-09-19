package com.mesafacil.dominio.reserva.restaurante.mapper;

import com.mesafacil.dominio.reserva.restaurante.entity.MesaDto;
import com.mesafacil.dominio.reserva.restaurante.model.Mesa;

import java.util.List;

public interface MesaMapper {

    Mesa dtoToEntity(MesaDto dto);

    MesaDto entityToDto(Mesa entity);

    List<MesaDto> toDtoList(List<Mesa> mesas);

}
