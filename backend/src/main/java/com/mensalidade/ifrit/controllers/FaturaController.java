package com.mensalidade.ifrit.controllers;

import com.mensalidade.ifrit.dto.FaturaDto;
import com.mensalidade.ifrit.dto.PagamentoFaturaDto;
import com.mensalidade.ifrit.requests.QueryParamRequest;
import com.mensalidade.ifrit.services.FaturaService;
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
@RequestMapping("/faturas")
@Tag(name = "Fatura")
public class FaturaController {

    private final FaturaService faturaService;

    public FaturaController(FaturaService faturaService) {
        this.faturaService = faturaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar uma fatura", method = "POST")
    public ResponseEntity<FaturaDto> cadastrarFatura(@RequestBody FaturaDto faturaDto) {
        FaturaDto dto = faturaService.criarFatura(faturaDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar uma fatura por id", method = "GET")
    public ResponseEntity<FaturaDto> consultarFaturaPorId(@PathVariable(value = "id") String id) {
        FaturaDto dto = faturaService.consultarFaturaDtoPorId(id);
        return new ResponseEntity<>(dto.add(linkTo(methodOn(FaturaController.class).consultarFaturaPorId(id)).withSelfRel()), HttpStatus.OK);
    }

    @GetMapping("/lista")
    @Operation(summary = "Listagem paginada de faturas", method = "GET")
    public ResponseEntity<Page<FaturaDto>> listarFaturas(
            QueryParamRequest paramRequest
    ) {
        Page<FaturaDto> responsePage;
        try {
            PageRequest pageRequest = PageRequest.of(paramRequest.getPage(), paramRequest.getLinesPerPage(), Sort.Direction.valueOf(paramRequest.getDirection()), paramRequest.getOrderBy());
            responsePage = faturaService.carregarTodasFaturas(pageRequest);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (responsePage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            for (FaturaDto fatura :
                    responsePage.getContent()) {
                String id = fatura.getId();
                fatura.add(linkTo(methodOn(FaturaController.class).consultarFaturaPorId(id)).withSelfRel());
            }
        }

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualziar uma fatura", method = "PUT")
    public ResponseEntity<FaturaDto> atualizarFatura(@PathVariable(value = "id") String id, @RequestBody FaturaDto faturaDto){
        faturaDto.setId(id);
        FaturaDto dto = faturaService.criarFatura(faturaDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Remover uma fatura", method = "DELETE")
    public ResponseEntity<Void> removerFatura(@PathVariable(value = "id") String id){
        faturaService.removerFatura(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/pagamento/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Realizar pagamento de uma fatura", method = "POST")
    public ResponseEntity<PagamentoFaturaDto> fazerPagamento(@PathVariable(value = "id") String id, @RequestBody PagamentoFaturaDto pagamentoFaturaDto){
        PagamentoFaturaDto dto = faturaService.fazerPagamento(id, pagamentoFaturaDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("/pagamento/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Remover pagamento de uma fatura", method = "POST")
    public ResponseEntity<Void> removerPagamento(@PathVariable(value = "id") String id){
        faturaService.removerPagamento(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
