package com.mensalidade.ifrit.controllers;

import com.mensalidade.ifrit.dto.ClienteDto;
import com.mensalidade.ifrit.requests.QueryParamRequest;
import com.mensalidade.ifrit.services.ClienteService;
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
@RequestMapping("/clientes")
@Tag(name = "Cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um cliente", method = "POST")
    public ResponseEntity<ClienteDto> cadastrarCliente(@RequestBody ClienteDto clienteDto) {
        ClienteDto dto = clienteService.cadastrarCliente(clienteDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar um cliente", method = "GET")
    public ResponseEntity<ClienteDto> consultarClientePorId(@PathVariable(value = "id") String id) {
        ClienteDto clienteDto = clienteService.consultarClientePorId(id);
        return new ResponseEntity<>(clienteDto.add(linkTo(methodOn(ClienteController.class).consultarClientePorId(id)).withSelfRel()), HttpStatus.OK);
    }

    @GetMapping("/lista")
    @Operation(summary = "Listagem paginada de clientes.", method = "GET")
    public ResponseEntity<Page<ClienteDto>> listarClientes(
            QueryParamRequest paramRequest
    ) {
        Page<ClienteDto> responsePage;
        try {
            PageRequest pageRequest = PageRequest.of(paramRequest.getPage(), paramRequest.getLinesPerPage(), Sort.Direction.valueOf(paramRequest.getDirection()), paramRequest.getOrderBy());
            responsePage = clienteService.carregarTodosClientes(pageRequest);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (responsePage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            for (ClienteDto cliente :
                    responsePage.getContent()) {
                String id = cliente.getId();
                cliente.add(linkTo(methodOn(ClienteController.class).consultarClientePorId(id)).withSelfRel());
            }
        }

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualizar um cliente", method = "PUT")
    public ResponseEntity<ClienteDto> atualizarCliente(@PathVariable(value = "id") String id, @RequestBody ClienteDto clienteDto){
        clienteDto.setId(id);
        ClienteDto dto = clienteService.cadastrarCliente(clienteDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Remover um cliente", method = "DELETE")
    public ResponseEntity<Void> removerCliente(@PathVariable(value = "id") String id){
        clienteService.removerCliente(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
