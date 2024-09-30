package com.mesafacil.dominio.reserva.restaurante.model;

import com.mesafacil.dominio.reserva.restaurante.entity.RestauranteDto;
import com.mesafacil.dominio.reserva.restaurante.enumeration.TipoDeCulinaria;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name = "restaurante", schema = "reserva")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
public class Restaurante  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "res_id")
    private Long id;

    @NotBlank
    @Column(name = "res_nome", nullable = false, unique = true)
    private String nome;

    @Length(max = 500)
    @NotBlank
    @Column(name = "res_localizacao", nullable = false, length = 500)
    private String localizacao;

    @ElementCollection(targetClass = TipoDeCulinaria.class)
    @CollectionTable(name = "restaurante_culinarias", schema = "reserva",
            foreignKey = @ForeignKey(name = "fk_tipo_culinaria"),
            joinColumns = @JoinColumn(name = "res_id"))
    @Enumerated(EnumType.STRING) // Armazena como texto no banco
    @Column(name = "res_tipo_culinaria", nullable = false)
    private List<TipoDeCulinaria> tiposDeCulinaria;


    public Restaurante(RestauranteDto restauranteDto) {

    }
}
