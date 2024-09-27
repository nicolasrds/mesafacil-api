package com.mesafacil.dominio.reserva.restaurante.model;

import com.mesafacil.dominio.reserva.restaurante.enumeration.DisponibilidadeMesa;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "mesa", schema = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mes_id")
    private Long idMesa;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "res_id", referencedColumnName = "res_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_restaurante"))
    private Restaurante restaurante;

    @NotNull
    @Column(name = "mes_numero", nullable = false)
    private int numeroMesa;

    @NotNull
    @Column(name = "dis_mesa", nullable = false)
    private DisponibilidadeMesa descricao;

//    @AssertTrue(message = "Mesa disponível.")
//    public boolean isDisponivel(){
//        return descricao == DisponibilidadeMesa.DISPONIVEL;
//    }
}
