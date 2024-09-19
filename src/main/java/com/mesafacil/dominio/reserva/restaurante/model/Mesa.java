package com.mesafacil.dominio.reserva.restaurante.model;

import com.mesafacil.dominio.reserva.restaurante.entity.MesaDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mesa_id")
    private Long idMesa;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "res_id", referencedColumnName = "res_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_restaurante"))
    private Restaurante restaurante;

    @NotNull
    @Column(name = "mesa_numero", nullable = false)
    private int numeroMesa;

    @NotNull
    @Column(name = "mesa_capacidade", nullable = false)
    private int capacidadeMesa;

    public Mesa(MesaDto mesaDto){

    }

}
