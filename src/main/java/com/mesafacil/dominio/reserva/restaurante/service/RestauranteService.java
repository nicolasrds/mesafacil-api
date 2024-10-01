package com.mesafacil.dominio.reserva.restaurante.service;

import com.mesafacil.dominio.reserva.restaurante.entity.HorarioFuncionamentoDto;
import com.mesafacil.dominio.reserva.restaurante.entity.MesaDto;
import com.mesafacil.dominio.reserva.restaurante.mapper.HorarioFuncionamentoMapper;
import com.mesafacil.dominio.reserva.restaurante.mapper.MesaMapper;
import com.mesafacil.dominio.reserva.restaurante.model.HorarioFuncionamento;
import com.mesafacil.dominio.reserva.restaurante.model.Mesa;
import com.mesafacil.dominio.reserva.restaurante.model.Restaurante;
import com.mesafacil.dominio.reserva.restaurante.repository.HorarioFuncionamentoRepository;
import com.mesafacil.dominio.reserva.restaurante.repository.MesaRepository;
import com.mesafacil.dominio.reserva.restaurante.repository.RestauranteRepository;
import com.mesafacil.dominio.reserva.restaurante.usecase.UseCaseRestauranteHorario;
import com.mesafacil.infra.exception.RegraDeNegocioException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = {"restauranteCache"})
public class RestauranteService {

    private final HorarioFuncionamentoRepository horarioFuncionamentoRepository;
    private final RestauranteRepository restauranteRepository;
    private final HorarioFuncionamentoMapper horarioFuncionamentoMapper;
    private final List<UseCaseRestauranteHorario> useCaseRestauranteHorarios;
    private final MesaRepository mesaRepository;
    private final MesaMapper mesaMapper;

    @CacheEvict(allEntries = true, cacheNames = "restauranteCache")
    public Restaurante cadastrar(Restaurante restaurante) {
       return restauranteRepository.save(restaurante);
    }

    @CacheEvict(allEntries = true, cacheNames = "restauranteCache")
    public HorarioFuncionamento registrarHorarioFuncionamento(HorarioFuncionamentoDto horarioFuncionamentoDto) {
        useCaseRestauranteHorarios
                .forEach(validacao -> validacao.validar(horarioFuncionamentoDto));
        HorarioFuncionamento horarioFuncionamento = horarioFuncionamentoMapper.dtoToEntity(horarioFuncionamentoDto);
        horarioFuncionamentoRepository.save(horarioFuncionamento);
        return horarioFuncionamento;
    }

    /**
     * unless = "#result == null": Indica que o resultado não será armazenado no cache se for nulo.
     * Isso é útil para evitar armazenar resultados vazios.
     * @return
     */
    @Cacheable( unless = "#result == null ")
    public Page<Restaurante> consultar(Pageable pageable){
        return restauranteRepository.findAll(pageable);
    }

    @Cacheable(key = "#id", unless = "#result == null ")
    public Restaurante buscarPor(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Restaurante não encontrada"));
    }

    @CacheEvict(allEntries = true, cacheNames = "restauranteCache")
    public void deletar(Long id) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);
        if (restaurante.isPresent()) {
            restauranteRepository.delete(restaurante.get());
        }
    }

}
