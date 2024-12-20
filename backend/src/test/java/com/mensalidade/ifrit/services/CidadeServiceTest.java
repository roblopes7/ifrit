package com.mensalidade.ifrit.services;

import com.mensalidade.ifrit.config.IBGEConfig;
import com.mensalidade.ifrit.dto.CidadeDto;
import com.mensalidade.ifrit.models.Cidade;
import com.mensalidade.ifrit.repositories.CidadeRepository;
import com.mensalidade.ifrit.requests.CidadeIbgeRequest;
import com.mensalidade.ifrit.services.exceptions.ObjetoNaoEncontrado;
import com.mensalidade.ifrit.utils.CidadeTestUtil;
import com.mensalidade.ifrit.utils.TestsUtil;
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
class CidadeServiceTest {

    private final String CIDADE_NAO_ENCONTRADA = "Cidade não encontrada.";
    private final String IBGE_URI = "https://servicodados.ibge.gov.br/api/v1/localidades/municipios";
    CidadeTestUtil utilCidadeTestUtil = new CidadeTestUtil();
    TestsUtil testsUtil = new TestsUtil();

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
                .thenReturn(new Cidade(testsUtil.getUiidPadrao(), "Cidade Teste", "Brasil", "PR"));
        CidadeDto cidadeCadastrada = cidadeService.cadastrarCidade(utilCidadeTestUtil.criarCidade());

        verify(cidadeRepository, Mockito.times(1)).save(any());
        Assertions.assertThat(cidadeCadastrada.getId()).isEqualTo(testsUtil.getUiidPadrao());
    }

    @Test
    @DisplayName("Trazer cidades paginadas")
    void carregarTodasCidades() {
        Page<Cidade> cidadePage = new PageImpl<>(utilCidadeTestUtil.cidades(), testsUtil.getPagePadrao(), utilCidadeTestUtil.cidades().size());
        when(cidadeRepository.findAll(any(Pageable.class))).thenReturn(cidadePage);

        Page<CidadeDto> result = cidadeService.carregarTodasCidades(testsUtil.getPagePadrao());


        assertNotNull(result);
        verify(cidadeRepository, times(1)).findAll(testsUtil.getPagePadrao());
        Assertions.assertThat(result.getTotalElements()).isEqualTo(cidadePage.getTotalElements());
  }

    @Test
    @DisplayName("trazer pagincao vazia")
    void paginacaoCidadesVazia() {
        Page<Cidade> cidadePage = new PageImpl<>(new ArrayList<>(), testsUtil.getPagePadrao(), 0);
        when(cidadeRepository.findAll(any(Pageable.class))).thenReturn(cidadePage);

        Page<CidadeDto> resultado = cidadeService.carregarTodasCidades(testsUtil.getPagePadrao());


        assertNotNull(resultado);
        verify(cidadeRepository, times(1)).findAll(testsUtil.getPagePadrao());
        Assertions.assertThat(resultado.getTotalElements()).isZero();
    }

    @Test
    @DisplayName("Consultar Cidade por id")
    void consultarCidadePorId() {
        Optional<Cidade> cidade = Optional.ofNullable(modelMapper.map(utilCidadeTestUtil.criarCidade(), Cidade.class));
        when(cidadeRepository.findById(testsUtil.getUiidPadrao())).thenReturn(cidade);

        CidadeDto resultado = cidadeService.consultarCidadePorId(testsUtil.getUiidPadrao());

        assertNotNull(resultado);
        verify(cidadeRepository, Mockito.times(1)).findById(any());
        Assertions.assertThat(resultado.getId()).isEqualTo(testsUtil.getUiidPadrao());
    }

    @Test
    @DisplayName("Consultar Cidade não cadastrada")
    void consultarCidadeInexistente() {
        when(cidadeRepository.findById(testsUtil.getUiidPadrao())).thenReturn(null);

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
        when(cidadeRepository.findById(testsUtil.getUiidPadrao())).thenReturn(null);

        ObjetoNaoEncontrado exception = assertThrows(ObjetoNaoEncontrado.class, () -> {
            cidadeService.removerCidade(testsUtil.getUiidDiferente());
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(CIDADE_NAO_ENCONTRADA));
    }

    @Test
    @DisplayName("Remover Cidade com sucesso")
    void removerCidade() {
        when(cidadeRepository.save(any(Cidade.class)))
                .thenReturn(new Cidade(testsUtil.getUiidPadrao(), "Cidade Teste", "Brasil", "PR"));
        CidadeDto cidadeCadastrada = cidadeService.cadastrarCidade(utilCidadeTestUtil.criarCidade());

        Optional<Cidade> cidade = Optional.ofNullable(modelMapper.map(utilCidadeTestUtil.criarCidade(), Cidade.class));
        when(cidadeRepository.findById(testsUtil.getUiidPadrao())).thenReturn(cidade);

        cidadeService.removerCidade(testsUtil.getUiidPadrao());

        verify(cidadeRepository, Mockito.times(1)).delete(any(Cidade.class));
    }

    @Test
    @DisplayName("Consultar IBGE")
    public void consultarCidadesPeloIbge() {
        CidadeIbgeRequest[] cidadesIbge = utilCidadeTestUtil.cidadesIbge();

        when(ibgeConfig.getUriIBGE())
                .thenReturn(IBGE_URI);

        when(restTemplate.getForObject(eq(IBGE_URI), eq(CidadeIbgeRequest[].class)))
                .thenReturn(cidadesIbge);

        cidadeService.consultarCidadesPeloIbge();
        verify(cidadeRepository).saveAll(anyList());
    }

}