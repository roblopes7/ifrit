package com.mensalidade.ifrit.services;

import com.mensalidade.ifrit.dto.ClienteDto;
import com.mensalidade.ifrit.dto.EmpresaDto;
import com.mensalidade.ifrit.dto.EnderecoDto;
import com.mensalidade.ifrit.models.Cliente;
import com.mensalidade.ifrit.models.Empresa;
import com.mensalidade.ifrit.repositories.EmpresaRepository;
import com.mensalidade.ifrit.services.exceptions.ObjetoNaoEncontrado;
import com.mensalidade.ifrit.utils.CidadeTestUtil;
import com.mensalidade.ifrit.utils.EmpresaTestUtil;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmpresaServiceTest {

    private final String EMPRESA_NAO_ENCONTRADA = "Empresa não encontrada.";
    private final TestsUtil testsUtil = new TestsUtil();
    private final EmpresaTestUtil utilEmpresaTestUtil = new EmpresaTestUtil();
    private final CidadeTestUtil utilCidadeTest = new CidadeTestUtil();

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    @Autowired
    private EmpresaService empresaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        empresaService = new EmpresaService(empresaRepository, modelMapper);
    }

    @Test
    @DisplayName("Cadastro Empresa com sucesso")
    void cadastrarEmpresa() {
        when(empresaRepository.save(any(Empresa.class)))
                .thenReturn(modelMapper.map(utilEmpresaTestUtil.criarEmpresa(), Empresa.class));
        EmpresaDto empresaCadastrado = empresaService.cadastrarEmpresa(utilEmpresaTestUtil.criarEmpresa());

        verify(empresaRepository, Mockito.times(1)).save(any());
        Assertions.assertThat(empresaCadastrado.getId()).isEqualTo(testsUtil.getUiidPadrao());
    }

    @Test
    @DisplayName("Consultar Empresa com sucesso")
    void consultarEmpresaPorId() {
        Optional<Empresa> empresa = Optional.ofNullable(modelMapper.map(utilEmpresaTestUtil.criarEmpresa(), Empresa.class));
        when(empresaRepository.findById(testsUtil.getUiidPadrao())).thenReturn(empresa);

        EmpresaDto resultado = empresaService.consultarEmpresaPorId(testsUtil.getUiidPadrao());

        assertNotNull(resultado);
        verify(empresaRepository, Mockito.times(1)).findById(any());
        Assertions.assertThat(resultado.getId()).isEqualTo(testsUtil.getUiidPadrao());

    }

    @Test
    @DisplayName("Erro ao consultar Empresa")
    void consultarEmpresaPorIdInexistente() {
        when(empresaRepository.findById(testsUtil.getUiidPadrao())).thenReturn(null);

        ObjetoNaoEncontrado exception = assertThrows(ObjetoNaoEncontrado.class, this::consultaVazia);

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(EMPRESA_NAO_ENCONTRADA));
    }

    private EmpresaDto consultaVazia() {
        return empresaService.consultarEmpresaPorId("");
    }

    @Test
    @DisplayName("Trazer empresas paginados")
    void carregarTodosEmpresas() {
        Page<Empresa> empresaPage = new PageImpl<>(utilEmpresaTestUtil.empresas(), testsUtil.getPagePadrao(), utilEmpresaTestUtil.empresas().size());
        when(empresaRepository.findAll(any(Pageable.class))).thenReturn(empresaPage);

        Page<EmpresaDto> result = empresaService.carregarTodasEmpresas(testsUtil.getPagePadrao());


        assertNotNull(result);
        verify(empresaRepository, times(1)).findAll(testsUtil.getPagePadrao());
        Assertions.assertThat(result.getTotalElements()).isEqualTo(empresaPage.getTotalElements());
    }

    @Test
    @DisplayName("trazer pagincao vazia")
    void paginacaoEmpresaVazia() {
        Page<Empresa> empresaPage = new PageImpl<>(new ArrayList<>(), testsUtil.getPagePadrao(), 0);
        when(empresaRepository.findAll(any(Pageable.class))).thenReturn(empresaPage);

        Page<EmpresaDto> resultado = empresaService.carregarTodasEmpresas(testsUtil.getPagePadrao());


        assertNotNull(resultado);
        verify(empresaRepository, times(1)).findAll(testsUtil.getPagePadrao());
        Assertions.assertThat(resultado.getTotalElements()).isZero();
    }

    @Test
    @DisplayName("Remover Empresa não cadastrado")
    void removerEmpresaInexistente() {
        when(empresaRepository.findById(testsUtil.getUiidPadrao())).thenReturn(null);

        ObjetoNaoEncontrado exception = assertThrows(ObjetoNaoEncontrado.class, () -> {
            empresaService.removerEmpresa(testsUtil.getUiidDiferente());
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(EMPRESA_NAO_ENCONTRADA));
    }

    @Test
    @DisplayName("Remover Empresa com sucesso")
    void removerEmpresa() {
        when(empresaRepository.save(any(Empresa.class)))
                .thenReturn(utilEmpresaTestUtil.empresas().get(0));
        EmpresaDto empresaCadastrada = empresaService.cadastrarEmpresa(utilEmpresaTestUtil.criarEmpresa());

        Optional<Empresa> empresa = Optional.ofNullable(modelMapper.map(utilEmpresaTestUtil.criarEmpresa(), Empresa.class));
        when(empresaRepository.findById(testsUtil.getUiidPadrao())).thenReturn(empresa);

        empresaService.removerEmpresa(testsUtil.getUiidPadrao());

        verify(empresaRepository, Mockito.times(1)).delete(any());
    }

    @Test
    @DisplayName("Cadastro Cliente com cidade")
    void cadastrarClienteComCidade() {
        EmpresaDto empresa = utilEmpresaTestUtil.criarEmpresa();
        EnderecoDto enderecoDto = new EnderecoDto();
        enderecoDto.setCidade(utilCidadeTest.criarCidade());
        empresa.setEndereco(enderecoDto);

        when(empresaRepository.save(any(Empresa.class)))
                .thenReturn(modelMapper.map(empresa, Empresa.class));

        EmpresaDto empresaCadastrada = empresaService.cadastrarEmpresa(empresa);

        verify(empresaRepository, Mockito.times(1)).save(any());
        Assertions.assertThat(empresaCadastrada.getId()).isEqualTo(testsUtil.getUiidPadrao());
    }
}