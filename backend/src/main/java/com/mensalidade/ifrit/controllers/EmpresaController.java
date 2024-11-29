package com.mensalidade.ifrit.controllers;

import com.mensalidade.ifrit.dto.EmpresaDto;
import com.mensalidade.ifrit.requests.QueryParamRequest;
import com.mensalidade.ifrit.services.EmpresaService;
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
@RequestMapping("/empresas")
@Tag(name = "Empresa")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar uma empresa", method = "POST")
    public ResponseEntity<EmpresaDto> cadastrarEmpresa(@RequestBody EmpresaDto empresaDto) {
        EmpresaDto dto = empresaService.cadastrarEmpresa(empresaDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar uma empresa", method = "GET")
    public ResponseEntity<EmpresaDto> consultarEmpresaPorId(@PathVariable(value = "id") String id) {
        EmpresaDto empresaDto = empresaService.consultarEmpresaPorId(id);
        return new ResponseEntity<>(empresaDto.add(linkTo(methodOn(EmpresaController.class).consultarEmpresaPorId(id)).withSelfRel()), HttpStatus.OK);
    }

    @GetMapping("/filtro")
    @Operation(summary = "Listagem paginada de empresas", method = "GET")
    public ResponseEntity<Page<EmpresaDto>> listarEmpresas(
            @RequestParam(value = "filter", required = false) String filtro,
            QueryParamRequest paramRequest
    ) {
        Page<EmpresaDto> responsePage;
        try {
            PageRequest pageRequest = PageRequest.of(paramRequest.getPage(), paramRequest.getLinesPerPage(), Sort.Direction.valueOf(paramRequest.getDirection()), paramRequest.getOrderBy());
            responsePage = empresaService.filtrarEmpresas(filtro, pageRequest);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (responsePage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            for (EmpresaDto empresa :
                    responsePage.getContent()) {
                String id = empresa.getId();
                empresa.add(linkTo(methodOn(EmpresaController.class).consultarEmpresaPorId(id)).withSelfRel());
            }
        }

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma empresa", method = "PUT")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmpresaDto> atualizarEmpresa(@PathVariable(value = "id") String id, @RequestBody EmpresaDto empresaDto){
        empresaDto.setId(id);
        EmpresaDto dto = empresaService.cadastrarEmpresa(empresaDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover uma empresa", method = "DELETE")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> removerEmpresa(@PathVariable(value = "id") String id){
        empresaService.removerEmpresa(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
