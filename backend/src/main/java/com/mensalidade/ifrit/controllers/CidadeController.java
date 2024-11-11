package com.mensalidade.ifrit.controllers;


import com.mensalidade.ifrit.services.CidadeService;
import com.mensalidade.ifrit.dto.CidadeDto;
import com.mensalidade.ifrit.requests.QueryParamRequest;
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
@RequestMapping("/cidades")
@Tag(name = "Cidade")
public class CidadeController {

    private final CidadeService cidadeService;

    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra uma cidade", method = "POST")
    public ResponseEntity<CidadeDto> cadastrarCidade(@RequestBody CidadeDto cidadeDto) {
        CidadeDto dto = cidadeService.cadastrarCidade(cidadeDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("/filtro")
    @Operation(summary = "Listar cidades", method = "GET")
    public ResponseEntity<Page<CidadeDto>> listarCidades(
            @RequestParam(value = "filter", required = false) String filtro,
            QueryParamRequest paramRequest
    ) {
        Page<CidadeDto> responsePage;
        try {
            PageRequest pageRequest = PageRequest.of(paramRequest.getPage(), paramRequest.getLinesPerPage(), Sort.Direction.valueOf(paramRequest.getDirection()), paramRequest.getOrderBy());
            responsePage = cidadeService.consultarCidades(filtro, pageRequest);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (responsePage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            for (CidadeDto cidade :
                    responsePage.getContent()) {
                String id = cidade.getId();
                cidade.add(linkTo(methodOn(CidadeController.class).consultarCidadePorId(id)).withSelfRel());
            }
        }

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    //Operação post por buscar na base do IBGE e cadastrar/atualizar cidades
    @PostMapping("/consultar-ibge")
    @Operation(summary = "Consultar cidades pelo IBGE", method = "POST")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> consultarCidadesPeloIbge(){
        cidadeService.consultarCidadesPeloIbge();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma cidade", method = "PUT")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CidadeDto> atualizarCidade(@RequestBody CidadeDto cidadeDto){
        CidadeDto dto = cidadeService.cadastrarCidade(cidadeDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar uma cidade", method = "GET")
    public ResponseEntity<CidadeDto> consultarCidadePorId(@PathVariable(value = "id") String id) {
        CidadeDto cidadeDto = cidadeService.consultarCidadePorId(id);
        return new ResponseEntity<>(cidadeDto.add(linkTo(methodOn(CidadeController.class).consultarCidadePorId(id)).withSelfRel()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover uma cidade", method = "DELETE")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> removerCidade(@PathVariable(value = "id") String id){
        cidadeService.removerCidade(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
