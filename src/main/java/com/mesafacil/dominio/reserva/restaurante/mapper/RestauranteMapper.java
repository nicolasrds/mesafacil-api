package com.mesafacil.dominio.reserva.restaurante.mapper;

import com.mesafacil.dominio.reserva.restaurante.entity.RestauranteDto;
import com.mesafacil.dominio.reserva.restaurante.model.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RestauranteMapper {
    RestauranteMapper INSTANCE = Mappers.getMapper(RestauranteMapper.class);

    Restaurante dtoToEntity(RestauranteDto dto);

    RestauranteDto entityToDto(Restaurante entity);
}
