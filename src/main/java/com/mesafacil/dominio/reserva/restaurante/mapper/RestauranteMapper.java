package com.mesafacil.dominio.reserva.restaurante.mapper;

import com.mesafacil.dominio.reserva.restaurante.entity.RestauranteDto;
import com.mesafacil.dominio.reserva.restaurante.model.Restaurante;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RestauranteMapper {
//    RestauranteMapper INSTANCE = Mappers.getMapper(RestauranteMapper.class);

    Restaurante dtoToEntity(RestauranteDto dto);

    RestauranteDto entityToDto(Restaurante entity);

    List<RestauranteDto> toDtoList(List<Restaurante> restaurantes);

}
