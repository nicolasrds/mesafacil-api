package com.mesafacil.dominio.reserva.restaurante.model;

import com.mesafacil.dominio.reserva.restaurante.enumeration.DiaDaSemana;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "horario_funcionamento", schema = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class HorarioFuncionamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hrf_id")
    private Long id;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "res_id", referencedColumnName = "res_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_restaurante"))
    private Restaurante restaurante;

    @NotBlank
    @Column(name = "hrf_descricao", nullable = false)
    private String descricao;

    @ElementCollection(targetClass = DiaDaSemana.class)
    @CollectionTable(name = "horario_dia", schema = "reserva",
            foreignKey = @ForeignKey(name = "fk_dia_semana"),
            joinColumns = @JoinColumn(name = "hrf_id"))
    @Enumerated(EnumType.STRING) // Armazena como texto no banco
    @Column(name = "hrf_dia_semana", nullable = false)
    private List<DiaDaSemana> listaDiaDaSemana;

    @NotNull
    @Column(name = "hora_abertura")
    private LocalTime horaAbertura;

    @NotNull
    @Column(name = "hora_fechamento")
    private LocalTime horaFechamento;

    @AssertTrue(message = "Hora de abertura deve estar entre 00:00 e 23:59.")
    public boolean isHoraAberturaValida() {
        return horaAbertura != null && !horaAbertura.isBefore(LocalTime.of(6, 0)) && !horaAbertura.isAfter(LocalTime.of(23, 0));
    }

    @AssertTrue(message = "Hora de fechamento deve estar entre 00:00 e 23:59.")
    public boolean isHoraFechamentoValida() {
        return horaFechamento != null && !horaFechamento.isBefore(LocalTime.of(6, 0)) && !horaFechamento.isAfter(LocalTime.of(23, 59));
    }

}
