package com.mesafacil.dominio.reserva.restaurante.repository;

import com.mesafacil.dominio.reserva.restaurante.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
