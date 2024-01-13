package com.mensalidade.ifrit.services;

import com.mensalidade.ifrit.dto.CidadeDto;
import com.mensalidade.ifrit.models.Cidade;
import com.mensalidade.ifrit.repositories.CidadeRepository;
import com.mensalidade.ifrit.services.exceptions.ObjetoNaoEncontrado;
import com.mensalidade.ifrit.utils.Util;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CidadeServiceTest {

    private final String CIDADE_NAO_ENCONTRADA = "Cidade não encontrada.";
    com.mensalidade.ifrit.utils.Cidade utilCidade = new com.mensalidade.ifrit.utils.Cidade();
    Util util = new Util();

    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    @Autowired
    private CidadeService cidadeService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        cidadeService = new CidadeService(cidadeRepository, modelMapper);
    }

    @Test
    @DisplayName("Cadastro Cidade com sucesso")
    void cadastrarCidade() {
        when(cidadeRepository.save(any(Cidade.class)))
                .thenReturn(new Cidade(util.getUiidPadrao(), "Cidade Teste", "Brasil", "PR", "123456789"));
        CidadeDto cidadeCadastrada = cidadeService.cadastrarCidade(utilCidade.criarCidade());

        verify(cidadeRepository, Mockito.times(1)).save(any());
        Assertions.assertThat(cidadeCadastrada.getId()).isEqualTo(util.getUiidPadrao());
    }

    @Test
    void carregarTodasCidades() {
        Page<Cidade> cidadePage = new PageImpl<>(utilCidade.cidades(), util.getPagePadrao(), utilCidade.cidades().size());
        when(cidadeRepository.findAll(any(Pageable.class))).thenReturn(cidadePage);

        Page<CidadeDto> result = cidadeService.carregarTodasCidades(util.getPagePadrao());


        assertNotNull(result);
        verify(cidadeRepository, times(1)).findAll(util.getPagePadrao());
        Assertions.assertThat(result.getTotalElements()).isEqualTo(cidadePage.getTotalElements());
  }

    @Test
    void paginacaoCidadesVazia() {
        Page<Cidade> cidadePage = new PageImpl<>(new ArrayList<>(), util.getPagePadrao(), 0);
        when(cidadeRepository.findAll(any(Pageable.class))).thenReturn(cidadePage);

        Page<CidadeDto> resultado = cidadeService.carregarTodasCidades(util.getPagePadrao());


        assertNotNull(resultado);
        verify(cidadeRepository, times(1)).findAll(util.getPagePadrao());
        Assertions.assertThat(resultado.getTotalElements()).isZero();
    }

    @Test
    void consultarCidadePorId() {
        Optional<Cidade> cidade = Optional.ofNullable(modelMapper.map(utilCidade.criarCidade(), Cidade.class));
        when(cidadeRepository.findById(util.getUiidPadrao())).thenReturn(cidade);

        CidadeDto resultado = cidadeService.consultarCidadePorId(util.getUiidPadrao());

        assertNotNull(resultado);
        verify(cidadeRepository, Mockito.times(1)).findById(any());
        Assertions.assertThat(resultado.getId()).isEqualTo(util.getUiidPadrao());
    }

    @Test

    void consultarCidadeInexistente() {
        when(cidadeRepository.findById(util.getUiidPadrao())).thenReturn(null);

        ObjetoNaoEncontrado exception = assertThrows(ObjetoNaoEncontrado.class, this::consultaVazia);

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(CIDADE_NAO_ENCONTRADA));
    }

    private CidadeDto consultaVazia(){
        return cidadeService.consultarCidadePorId("");
    }

    @Test
    void removerCidadeInexistente() {
        when(cidadeRepository.findById(util.getUiidPadrao())).thenReturn(null);

        ObjetoNaoEncontrado exception = assertThrows(ObjetoNaoEncontrado.class, () -> {
            cidadeService.removerCidade(util.getUiidDiferente());
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(CIDADE_NAO_ENCONTRADA));
    }

    @Test
    void removerCidade() {
        when(cidadeRepository.save(any(Cidade.class)))
                .thenReturn(new Cidade(util.getUiidPadrao(), "Cidade Teste", "Brasil", "PR", "123456789"));
        CidadeDto cidadeCadastrada = cidadeService.cadastrarCidade(utilCidade.criarCidade());

        Optional<Cidade> cidade = Optional.ofNullable(modelMapper.map(utilCidade.criarCidade(), Cidade.class));
        when(cidadeRepository.findById(util.getUiidPadrao())).thenReturn(cidade);

        cidadeService.removerCidade(util.getUiidPadrao());

        verify(cidadeRepository, Mockito.times(1)).delete(any());
    }
}