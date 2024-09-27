package com.mesafacil.dominio.reserva.restaurante.mapper;

import com.mesafacil.dominio.reserva.restaurante.entity.MesaDto;
import com.mesafacil.dominio.reserva.restaurante.model.Mesa;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MesaMapper {

//    MesaMapper INSTANCE = Mappers.getMapper(MesaMapper.class);

    Mesa dtoToEntity(MesaDto dto);

    MesaDto entityToDto(Mesa entity);

    List<MesaDto> toDtoList(List<Mesa> mesas);

}
