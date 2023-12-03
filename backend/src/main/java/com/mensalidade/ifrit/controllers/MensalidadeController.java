package com.mensalidade.ifrit.controllers;

import com.mensalidade.ifrit.dto.MensalidadeDto;
import com.mensalidade.ifrit.requests.QueryParamRequest;
import com.mensalidade.ifrit.services.MensalidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/mensalidades")
@Tag(name = "Mensalidade")
public class MensalidadeController {

    private final MensalidadeService mensalidadeService;

    public MensalidadeController(MensalidadeService mensalidadeService) {
        this.mensalidadeService = mensalidadeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar uma mensalidade", method = "POST")
    public ResponseEntity<MensalidadeDto> cadastrarMensalidade(@RequestBody MensalidadeDto mensalidadeDto) {
        MensalidadeDto dto = mensalidadeService.cadastrarMensalidade(mensalidadeDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar uma mensalidade", method = "POST")
    public ResponseEntity<MensalidadeDto> consultarMensalidadePorId(@PathVariable(value = "id") String id) {
        MensalidadeDto mensalidadeDto = mensalidadeService.consultarMensalidadePorId(id);
        return new ResponseEntity<>(mensalidadeDto.add(linkTo(methodOn(MensalidadeController.class).consultarMensalidadePorId(id)).withSelfRel()), HttpStatus.OK);
    }

    @GetMapping("/lista")
    @Operation(summary = "Listagem paginada das mensalidades", method = "POST")
    public ResponseEntity<Page<MensalidadeDto>> listarMensalidades(
            QueryParamRequest paramRequest
    ) {
        Page<MensalidadeDto> responsePage;
        try {
            PageRequest pageRequest = PageRequest.of(paramRequest.getPage(), paramRequest.getLinesPerPage(), Sort.Direction.valueOf(paramRequest.getDirection()), paramRequest.getOrderBy());
            responsePage = mensalidadeService.carregarTodosMensalidades(pageRequest);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (responsePage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            for (MensalidadeDto mensalidade :
                    responsePage.getContent()) {
                String id = mensalidade.getId();
                mensalidade.add(linkTo(methodOn(MensalidadeController.class).consultarMensalidadePorId(id)).withSelfRel());
            }
        }

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualizar uma mensalidade", method = "PUT")
    public ResponseEntity<MensalidadeDto> atualizarMensalidade(@PathVariable(value = "id") String id, @RequestBody MensalidadeDto mensalidadeDto){
        mensalidadeDto.setId(id);
        MensalidadeDto dto = mensalidadeService.cadastrarMensalidade(mensalidadeDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Remover uma mensalidade", method = "DELETE")
    public ResponseEntity<Void> removerMensalidade(@PathVariable(value = "id") String id){
        mensalidadeService.removerMensalidade(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
