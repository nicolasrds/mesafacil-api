package com.mesafacil.dominio.reserva.restaurante.service;

import com.mesafacil.dominio.reserva.restaurante.mapper.ReservaMapper;
import com.mesafacil.dominio.reserva.restaurante.model.Reserva;
import com.mesafacil.dominio.reserva.restaurante.repository.ReservaRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = {"ReservaCache"})
public class ReservaService {

    private ReservaRepository reservaRepository;

    @CacheEvict(allEntries = true, cacheNames = "reservaCache")
    public void cadastrarReserva(Reserva reserva) {
        reservaRepository.save(reserva);
    }

    /**
     * unless = "#result == null": Indica que o resultado não será armazenado no cache se for nulo.
     * Isso é útil para evitar armazenar resultados vazios.
     * @return
     */
    @Cacheable( unless = "#result == null ")
    public Page<Reserva> consultar(Pageable pageable){
        return reservaRepository.findAll(pageable);
    }

}
