package com.mensalidade.ifrit.controllers;

import com.mensalidade.ifrit.dto.CidadeDto;
import com.mensalidade.ifrit.dto.EmpresaDto;
import com.mensalidade.ifrit.dto.request.UsuarioRequest;
import com.mensalidade.ifrit.dto.response.UsuarioCompletoResponse;
import com.mensalidade.ifrit.dto.response.UsuarioResponse;
import com.mensalidade.ifrit.requests.QueryParamRequest;
import com.mensalidade.ifrit.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/usuarios")
@Tag(name = "Usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar um usuário", method = "POST")
    public ResponseEntity<UsuarioResponse> cadastarUsuario(@RequestBody @Valid UsuarioRequest request) {
        UsuarioResponse response = usuarioService.cadastrarUsuario(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Consultar um usuario", method = "GET")
    public ResponseEntity<UsuarioCompletoResponse> consultarUsuario(@PathVariable(value = "id") String id) {
        UsuarioCompletoResponse response = usuarioService.consultarUsuarioCompletoPorID(id);
        return new ResponseEntity<>(response.add(linkTo(methodOn(EmpresaController.class).consultarEmpresaPorId(id)).withSelfRel()), HttpStatus.OK);
    }

    @GetMapping("/lista")
    @Operation(summary = "Listar usuários", method = "GET")
    public ResponseEntity<Page<UsuarioResponse>> listarUsuarios(
            QueryParamRequest paramRequest
    ) {
        Page<UsuarioResponse> responsePage;
        try {
            PageRequest pageRequest = PageRequest.of(paramRequest.getPage(), paramRequest.getLinesPerPage(), Sort.Direction.valueOf(paramRequest.getDirection()), paramRequest.getOrderBy());
            responsePage = usuarioService.listarTodosUsuarios(pageRequest);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (responsePage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um usuário", method = "PUT")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UsuarioCompletoResponse> atualizarUsuario(@RequestBody @Valid UsuarioRequest request){
        UsuarioCompletoResponse response = usuarioService.atualizarUsuario(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }
}
