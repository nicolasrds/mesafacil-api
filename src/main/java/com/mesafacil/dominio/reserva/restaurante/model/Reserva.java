package com.mesafacil.dominio.reserva.restaurante.model;

import com.mesafacil.dominio.reserva.restaurante.enumeration.StatusReserva;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "reserva", schema = "reserva")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rsv_id")
    private Long idReserva;

    @NotNull
    @Column(name = "nom_cliente")
    private String nomeCliente;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "res_id", referencedColumnName = "res_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_restaurante"))
    private Restaurante restaurante;

    @NotNull
    @Column(name = "dat")
    private Date data;

    @NotNull
    @Column(name = "hor")
    private Time horario;

    @NotNull
    @OneToOne()
    @JoinColumn(name = "mes_id", referencedColumnName = "mes_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_mesa"))
    private Mesa mesa;

    @NotNull
    @Column(name = "sts_reserva")
    private StatusReserva descricao;
}
