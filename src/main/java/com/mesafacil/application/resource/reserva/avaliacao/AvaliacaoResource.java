package com.mesafacil.application.resource.reserva.avaliacao;

import com.mesafacil.application.util.UriUtil;
import com.mesafacil.dominio.reserva.avaliacao.entity.AvaliacaoDto;
import com.mesafacil.dominio.reserva.avaliacao.mapper.AvaliacaoMapper;
import com.mesafacil.dominio.reserva.avaliacao.model.Avaliacao;
import com.mesafacil.dominio.reserva.avaliacao.service.AvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/reserva/avaliacao")
@Tag(name = "Avaliação", description = "Registro de avaliações dos restaurantes")
public class AvaliacaoResource {

    private final AvaliacaoService avaliacaoService;
    private final AvaliacaoMapper avaliacaoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra uma nova avaliação.", method = "POST")
    public ResponseEntity<AvaliacaoDto> cadastrar(@Valid @RequestBody AvaliacaoDto avaliacaoDto) {
        Avaliacao avaliacao = avaliacaoMapper.dtoToEntity(avaliacaoDto);
        avaliacaoService.cadastrar(avaliacao);
        return ResponseEntity.created(UriUtil.createUriWithId(avaliacao.getId()))
                .body(avaliacaoMapper.entityToDto(avaliacao));
    }

}
