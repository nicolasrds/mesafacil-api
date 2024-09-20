package com.mesafacil.dominio.reserva.restaurante.model;

import com.mesafacil.dominio.reserva.restaurante.entity.MesaDto;
import com.mesafacil.dominio.reserva.restaurante.enumeration.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mesa", schema = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mesa_id")
    private Long id;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "res_id", referencedColumnName = "res_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_restaurante"))
    private Restaurante restaurante;

    @NotNull
    @Column(name = "mesa_numero", nullable = false)
    private int numeroMesa;

    @NotNull
    @Column(name = "status", nullable = false)
    private Status status;

    @AssertTrue(message = "Mesa disponível.")
    public boolean isDisponivel(){
        return status == Status.DISPONIVEL;
    }
}
