package com.mensalidade.ifrit.services;

import com.mensalidade.ifrit.dto.MensalidadeDto;
import com.mensalidade.ifrit.models.Mensalidade;
import com.mensalidade.ifrit.repositories.MensalidadeRepository;
import com.mensalidade.ifrit.services.exceptions.ObjetoNaoEncontrado;
import com.mensalidade.ifrit.utils.MensalidadeTest;
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

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MensalidadeServiceTest {

    private final String MENSALIDADE_NAO_ENCONTRADA = "Mensalidade não encontrada.";
    private final TestsUtil testsUtil = new TestsUtil();
    private final MensalidadeTest utilMensalidadeTest = new MensalidadeTest();

    @Mock
    private MensalidadeRepository mensalidadeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MensalidadeService mensalidadeService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        mensalidadeService = new MensalidadeService(mensalidadeRepository, modelMapper);
    }

    @Test
    @DisplayName("Cadastro Mensalidade com sucesso")
    void cadastrarMensalidade() {
        when(mensalidadeRepository.save(any(Mensalidade.class)))
                .thenReturn(modelMapper.map(utilMensalidadeTest.criarMensalidade(), Mensalidade.class));
        MensalidadeDto mensalidadeCadastrada = mensalidadeService.cadastrarMensalidade(utilMensalidadeTest.criarMensalidade());

        verify(mensalidadeRepository, Mockito.times(1)).save(any());
        Assertions.assertThat(mensalidadeCadastrada.getId()).isEqualTo(testsUtil.getUiidPadrao());
    }

    @Test
    @DisplayName("Consultar Mensalidade com sucesso")
    void consultarMensalidadePorId() {
        Optional<Mensalidade> mensalidade = Optional.ofNullable(modelMapper.map(utilMensalidadeTest.criarMensalidade(), Mensalidade.class));
        when(mensalidadeRepository.findById(testsUtil.getUiidPadrao())).thenReturn(mensalidade);

        MensalidadeDto resultado = mensalidadeService.consultarMensalidadePorId(testsUtil.getUiidPadrao());

        assertNotNull(resultado);
        verify(mensalidadeRepository, Mockito.times(1)).findById(any());
        Assertions.assertThat(resultado.getId()).isEqualTo(testsUtil.getUiidPadrao());
    }

    @Test
    @DisplayName("Erro ao consultar Mensalidade")
    void consultarMensalidadePorIdInexistente() {
        when(mensalidadeRepository.findById(testsUtil.getUiidPadrao())).thenReturn(null);

        ObjetoNaoEncontrado exception = assertThrows(ObjetoNaoEncontrado.class, this::consultaVazia);

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(MENSALIDADE_NAO_ENCONTRADA));
    }

    private MensalidadeDto consultaVazia() {
        return mensalidadeService.consultarMensalidadePorId("");
    }

    @Test
    @DisplayName("Trazer Mensalidades paginados")
    void carregarTodosMensalidades() {
        Page<Mensalidade> mensalidadePage = new PageImpl<>(utilMensalidadeTest.mensalidades(), testsUtil.getPagePadrao(), utilMensalidadeTest.mensalidades().size());
        when(mensalidadeRepository.findAll(any(Pageable.class))).thenReturn(mensalidadePage);

        Page<MensalidadeDto> result = mensalidadeService.carregarTodosMensalidades(testsUtil.getPagePadrao());


        assertNotNull(result);
        verify(mensalidadeRepository, times(1)).findAll(testsUtil.getPagePadrao());
        Assertions.assertThat(result.getTotalElements()).isEqualTo(mensalidadePage.getTotalElements());
    }

    @Test
    @DisplayName("trazer pagincao vazia")
    void paginacaoMensalidadesVazia() {
        Page<Mensalidade> mensalidadePage = new PageImpl<>(new ArrayList<>(), testsUtil.getPagePadrao(), 0);
        when(mensalidadeRepository.findAll(any(Pageable.class))).thenReturn(mensalidadePage);

        Page<MensalidadeDto> resultado = mensalidadeService.carregarTodosMensalidades(testsUtil.getPagePadrao());


        assertNotNull(resultado);
        verify(mensalidadeRepository, times(1)).findAll(testsUtil.getPagePadrao());
        Assertions.assertThat(resultado.getTotalElements()).isZero();
    }

    @Test
    @DisplayName("Remover Mensalidade não cadastrado")
    void removerMensalidadeInexistente() {
        when(mensalidadeRepository.findById(testsUtil.getUiidPadrao())).thenReturn(null);

        ObjetoNaoEncontrado exception = assertThrows(ObjetoNaoEncontrado.class, () -> {
            mensalidadeService.removerMensalidade(testsUtil.getUiidDiferente());
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(MENSALIDADE_NAO_ENCONTRADA));
    }

    @Test
    @DisplayName("Remover Mensalidade com sucesso")
    void removerMensalidade() {
        when(mensalidadeRepository.save(any(Mensalidade.class)))
                .thenReturn(utilMensalidadeTest.mensalidades().get(0));
        MensalidadeDto mensalidadeCadastrada = mensalidadeService.cadastrarMensalidade(utilMensalidadeTest.criarMensalidade());

        Optional<Mensalidade> mensalidade = Optional.ofNullable(modelMapper.map(utilMensalidadeTest.criarMensalidade(), Mensalidade.class));
        when(mensalidadeRepository.findById(testsUtil.getUiidPadrao())).thenReturn(mensalidade);

        mensalidadeService.removerMensalidade(testsUtil.getUiidPadrao());

        verify(mensalidadeRepository, Mockito.times(1)).delete(any());
    }
}
