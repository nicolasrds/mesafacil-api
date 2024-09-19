package com.mesafacil.dominio.reserva.restaurante.repository;

import com.mesafacil.dominio.reserva.restaurante.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MesaRepository extends JpaRepository<Mesa, Long> {

    // Precisa?
}
