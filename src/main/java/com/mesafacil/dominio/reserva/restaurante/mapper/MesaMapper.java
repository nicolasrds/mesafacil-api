package com.mesafacil.dominio.reserva.restaurante.mapper;

import com.mesafacil.dominio.reserva.restaurante.entity.MesaDto;
import com.mesafacil.dominio.reserva.restaurante.model.Mesa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MesaMapper {

    MesaMapper INSTANCE = Mappers.getMapper(MesaMapper.class);

    @Mapping(source = "idMesa", target = "mesa.id")
    Mesa dtoToEntity(MesaDto dto);
    @Mapping(source = "mesa.id", target = "idMesa")
    MesaDto entityToDto(Mesa mesa);

}
