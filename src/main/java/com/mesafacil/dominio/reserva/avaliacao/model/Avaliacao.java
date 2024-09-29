package com.mesafacil.dominio.reserva.avaliacao.model;

import com.mesafacil.dominio.reserva.restaurante.model.Restaurante;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "avaliacao", schema = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ava_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // Indica o relacionamento ManyToOne
    @JoinColumn(name = "res_id", nullable = false)
    private Restaurante restaurante;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    @Column(name = "ava_nota", nullable = false)
    private Integer nota;

    @NotBlank
    @Column(name = "ava_comentario", nullable = false)
    private String comentario;
}
