package com.mesafacil.dominio.reserva.avaliacao.service;

import com.mesafacil.dominio.reserva.avaliacao.model.Avaliacao;
import com.mesafacil.dominio.reserva.avaliacao.repository.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"avaliacaoCache"})
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    @CacheEvict(allEntries = true, cacheNames = "avaliacaoCache")
    public void cadastrar(Avaliacao avaliacao) {
        this.avaliacaoRepository.save(avaliacao);
    }

    @Cacheable( unless = "#result == null ")
    public Page<Avaliacao> consultar(Pageable pageable){
        return this.avaliacaoRepository.findAll(pageable);
    }

    @Cacheable( unless = "#result == null ")
    public Optional<Avaliacao> consultarPorId(Long id) {
        return this.avaliacaoRepository.findById(id);
    }

    @CacheEvict(allEntries = true, cacheNames = "avaliacaoCache")
    public void deletar(Avaliacao avaliacao) {
        this.avaliacaoRepository.delete(avaliacao);
    }

    @CacheEvict(allEntries = true, cacheNames = "avaliacaoCache")
    public Avaliacao atualizar(Avaliacao avaliacao) {
        return this.avaliacaoRepository.save(avaliacao);
    }

}
