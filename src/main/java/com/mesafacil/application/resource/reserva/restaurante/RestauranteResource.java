package com.mesafacil.application.resource.reserva.restaurante;


import com.mesafacil.application.util.UriUtil;
import com.mesafacil.dominio.reserva.restaurante.entity.HorarioFuncionamentoDto;
import com.mesafacil.dominio.reserva.restaurante.entity.MesaDto;
import com.mesafacil.dominio.reserva.restaurante.entity.RestauranteDto;
import com.mesafacil.dominio.reserva.restaurante.mapper.HorarioFuncionamentoMapper;
import com.mesafacil.dominio.reserva.restaurante.mapper.MesaMapper;
import com.mesafacil.dominio.reserva.restaurante.mapper.RestauranteMapper;
import com.mesafacil.dominio.reserva.restaurante.model.HorarioFuncionamento;
import com.mesafacil.dominio.reserva.restaurante.model.Mesa;
import com.mesafacil.dominio.reserva.restaurante.model.Restaurante;
import com.mesafacil.dominio.reserva.restaurante.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/reserva/restaurante")
@Tag(name = "Restaurante", description = "Controle do registro de Restaurantes")
public class RestauranteResource {

    private final RestauranteService restauranteService;
    private final RestauranteMapper restauranteMapper;
    private final HorarioFuncionamentoMapper horarioFuncionamentoMapper;
    private final MesaMapper mesaMapper;


    @GetMapping
    @Operation(summary = "Buscar os restaurantes registrados.", method = "GET")
    public Page<RestauranteDto> consultarTodos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return restauranteService.consultar(paginacao).map(restauranteMapper::entityToDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um novo restaurante.", method = "POST")
    public ResponseEntity<RestauranteDto> cadastrar(@Valid @RequestBody RestauranteDto restauranteDto) {
        Restaurante restaurante = restauranteMapper.dtoToEntity(restauranteDto);
        restauranteService.cadastrar(restaurante);
        return ResponseEntity.created(UriUtil.createUriWithId(restaurante.getId()))
                .body(restauranteMapper.entityToDto(restaurante));
    }

    @PostMapping("/registrar-horario")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar horário de funcionamento.", method = "PUT")
    public ResponseEntity<HorarioFuncionamentoDto> cadastrar(@Valid @RequestBody HorarioFuncionamentoDto horarioFuncionamentoDto) {
        HorarioFuncionamento horarioFuncionamento = restauranteService.registrarHorarioFuncionamento(horarioFuncionamentoDto);
        return ResponseEntity.created(UriUtil.createUriWithId(horarioFuncionamento.getId()))
                .body(horarioFuncionamentoMapper.entityToDto(horarioFuncionamento));
    }

    @PostMapping("/cadastrar-mesa")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra uma nova mesa.", method = "PUT")
    public ResponseEntity<MesaDto> cadastrarMesa(@Valid @RequestBody MesaDto mesaDto) {
        Mesa mesa = restauranteService.cadastrarMesa(mesaDto);
        return ResponseEntity.created(UriUtil.createUriWithId(mesa.getId()))
                .body(mesaMapper.entityToDto(mesa));
    }
}
