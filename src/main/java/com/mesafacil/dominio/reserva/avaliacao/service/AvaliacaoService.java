package com.mesafacil.dominio.reserva.avaliacao.service;

import com.mesafacil.dominio.reserva.avaliacao.model.Avaliacao;
import com.mesafacil.dominio.reserva.avaliacao.repository.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    public void cadastrar(Avaliacao avaliacao) {
        this.avaliacaoRepository.save(avaliacao);
    }

}
