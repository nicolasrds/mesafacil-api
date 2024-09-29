package com.mesafacil.dominio.reserva.avaliacao.mapper;

import com.mesafacil.dominio.reserva.avaliacao.entity.AvaliacaoDto;
import com.mesafacil.dominio.reserva.avaliacao.model.Avaliacao;
import org.mapstruct.Mapper;

@Mapper
public interface AvaliacaoMapper {

    Avaliacao dtoToEntity(AvaliacaoDto dto);

    AvaliacaoDto entityToDto(Avaliacao avaliacao);

}
