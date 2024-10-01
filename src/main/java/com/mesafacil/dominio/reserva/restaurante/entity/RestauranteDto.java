package com.mesafacil.dominio.reserva.restaurante.entity;

import com.mesafacil.dominio.reserva.restaurante.enumeration.TipoDeCulinaria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import org.hibernate.validator.constraints.Length;


public record RestauranteDto(

        Long id, // Se o id é parte do DTO


        @NotBlank
        String nome,

        @Length(max = 500)
        @NotBlank
        String localizacao,

        @NotEmpty
        List<TipoDeCulinaria> tiposDeCulinaria

) {
}
