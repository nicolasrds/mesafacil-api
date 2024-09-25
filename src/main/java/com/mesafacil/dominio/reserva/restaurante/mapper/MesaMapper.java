package com.mesafacil.dominio.reserva.restaurante.mapper;

import com.mesafacil.dominio.reserva.restaurante.entity.MesaDto;
import com.mesafacil.dominio.reserva.restaurante.entity.RestauranteDto;
import com.mesafacil.dominio.reserva.restaurante.model.Mesa;
import com.mesafacil.dominio.reserva.restaurante.model.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MesaMapper {

//    MesaMapper INSTANCE = Mappers.getMapper(MesaMapper.class);

    Mesa dtoToEntity(MesaDto dto);

    MesaDto entityToDto(Mesa entity);

    List<MesaDto> toDtoList(List<Mesa> mesas);

}
