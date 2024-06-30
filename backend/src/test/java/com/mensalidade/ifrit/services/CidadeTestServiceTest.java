package com.mensalidade.ifrit.services;

import com.mensalidade.ifrit.config.IBGEConfig;
import com.mensalidade.ifrit.dto.CidadeDto;
import com.mensalidade.ifrit.models.Cidade;
import com.mensalidade.ifrit.repositories.CidadeRepository;
import com.mensalidade.ifrit.requests.CidadeIbgeRequest;
import com.mensalidade.ifrit.services.exceptions.ObjetoNaoEncontrado;
import com.mensalidade.ifrit.utils.CidadeTest;
import com.mensalidade.ifrit.utils.Util;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CidadeTestServiceTest {

    private final String CIDADE_NAO_ENCONTRADA = "Cidade não encontrada.";
    private final String IBGE_URI = "https://servicodados.ibge.gov.br/api/v1/localidades/municipios";
    CidadeTest utilCidadeTest = new CidadeTest();
    Util util = new Util();

    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private IBGEConfig ibgeConfig;

    @InjectMocks
    private CidadeService cidadeService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        cidadeService = new CidadeService(cidadeRepository, modelMapper, ibgeConfig);
    }

    @Test
    @DisplayName("Cadastro Cidade com sucesso")
    void cadastrarCidade() {
        when(cidadeRepository.save(any(Cidade.class)))
                .thenReturn(new Cidade(util.getUiidPadrao(), "Cidade Teste", "Brasil", "PR"));
        CidadeDto cidadeCadastrada = cidadeService.cadastrarCidade(utilCidadeTest.criarCidade());

        verify(cidadeRepository, Mockito.times(1)).save(any());
        Assertions.assertThat(cidadeCadastrada.getId()).isEqualTo(util.getUiidPadrao());
    }

    @Test
    @DisplayName("Trazer cidades paginadas")
    void carregarTodasCidades() {
        Page<Cidade> cidadePage = new PageImpl<>(utilCidadeTest.cidades(), util.getPagePadrao(), utilCidadeTest.cidades().size());
        when(cidadeRepository.findAll(any(Pageable.class))).thenReturn(cidadePage);

        Page<CidadeDto> result = cidadeService.carregarTodasCidades(util.getPagePadrao());


        assertNotNull(result);
        verify(cidadeRepository, times(1)).findAll(util.getPagePadrao());
        Assertions.assertThat(result.getTotalElements()).isEqualTo(cidadePage.getTotalElements());
  }

    @Test
    @DisplayName("trazer pagincao vazia")
    void paginacaoCidadesVazia() {
        Page<Cidade> cidadePage = new PageImpl<>(new ArrayList<>(), util.getPagePadrao(), 0);
        when(cidadeRepository.findAll(any(Pageable.class))).thenReturn(cidadePage);

        Page<CidadeDto> resultado = cidadeService.carregarTodasCidades(util.getPagePadrao());


        assertNotNull(resultado);
        verify(cidadeRepository, times(1)).findAll(util.getPagePadrao());
        Assertions.assertThat(resultado.getTotalElements()).isZero();
    }

    @Test
    @DisplayName("Consultar Cidade por id")
    void consultarCidadePorId() {
        Optional<Cidade> cidade = Optional.ofNullable(modelMapper.map(utilCidadeTest.criarCidade(), Cidade.class));
        when(cidadeRepository.findById(util.getUiidPadrao())).thenReturn(cidade);

        CidadeDto resultado = cidadeService.consultarCidadePorId(util.getUiidPadrao());

        assertNotNull(resultado);
        verify(cidadeRepository, Mockito.times(1)).findById(any());
        Assertions.assertThat(resultado.getId()).isEqualTo(util.getUiidPadrao());
    }

    @Test
    @DisplayName("Consultar Cidade não cadastrada")
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
    @DisplayName("Remover Cidade não cadastrada")
    void removerCidadeInexistente() {
        when(cidadeRepository.findById(util.getUiidPadrao())).thenReturn(null);

        ObjetoNaoEncontrado exception = assertThrows(ObjetoNaoEncontrado.class, () -> {
            cidadeService.removerCidade(util.getUiidDiferente());
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(CIDADE_NAO_ENCONTRADA));
    }

    @Test
    @DisplayName("Remover Cidade com sucesso")
    void removerCidade() {
        when(cidadeRepository.save(any(Cidade.class)))
                .thenReturn(new Cidade(util.getUiidPadrao(), "Cidade Teste", "Brasil", "PR"));
        CidadeDto cidadeCadastrada = cidadeService.cadastrarCidade(utilCidadeTest.criarCidade());

        Optional<Cidade> cidade = Optional.ofNullable(modelMapper.map(utilCidadeTest.criarCidade(), Cidade.class));
        when(cidadeRepository.findById(util.getUiidPadrao())).thenReturn(cidade);

        cidadeService.removerCidade(util.getUiidPadrao());

        verify(cidadeRepository, Mockito.times(1)).delete(any());
    }

    @Test
    @DisplayName("Consultar IBGE")
    public void consultarCidadesPeloIbge() {
        CidadeIbgeRequest[] cidadesIbge = utilCidadeTest.cidadesIbge();

        when(ibgeConfig.getUriIBGE())
                .thenReturn(IBGE_URI);

        when(restTemplate.getForObject(eq(IBGE_URI), eq(CidadeIbgeRequest[].class)))
                .thenReturn(cidadesIbge);

        cidadeService.consultarCidadesPeloIbge();
        verify(cidadeRepository).saveAll(anyList());
    }

}