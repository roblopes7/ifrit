package com.mensalidade.ifrit.services;

import com.mensalidade.ifrit.dto.ClienteDto;
import com.mensalidade.ifrit.dto.EnderecoDto;
import com.mensalidade.ifrit.models.Cliente;
import com.mensalidade.ifrit.repositories.ClienteRepository;
import com.mensalidade.ifrit.services.exceptions.ObjetoNaoEncontrado;
import com.mensalidade.ifrit.utils.CidadeTestUtil;
import com.mensalidade.ifrit.utils.ClienteTestUtil;
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
class ClienteServiceTest {

    private final String CLIENTE_NAO_ENCONTRADA = "Cliente não encontrado.";
    private final TestsUtil testsUtil = new TestsUtil();
    private final ClienteTestUtil utilClienteTestUtil = new ClienteTestUtil();
    private final CidadeTestUtil utilCidadeTestUtil = new CidadeTestUtil();


    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    @Autowired
    private ClienteService clienteService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        clienteService = new ClienteService(clienteRepository, modelMapper);
    }


    @Test
    @DisplayName("Cadastro Cliente com sucesso")
    void cadastrarCliente() {
        when(clienteRepository.save(any(Cliente.class)))
                .thenReturn(modelMapper.map(utilClienteTestUtil.criarCliente(), Cliente.class));
        ClienteDto clienteCadastrado = clienteService.cadastrarCliente(utilClienteTestUtil.criarCliente());

        verify(clienteRepository, Mockito.times(1)).save(any());
        Assertions.assertThat(clienteCadastrado.getId()).isEqualTo(testsUtil.getUiidPadrao());
    }

    @Test
    @DisplayName("Consultar Cliente com sucesso")
    void consultarClientePorId() {
        Optional<Cliente> cliente = Optional.ofNullable(modelMapper.map(utilClienteTestUtil.criarCliente(), Cliente.class));
        when(clienteRepository.findById(testsUtil.getUiidPadrao())).thenReturn(cliente);

        ClienteDto resultado = clienteService.consultarClientePorId(testsUtil.getUiidPadrao());

        assertNotNull(resultado);
        verify(clienteRepository, Mockito.times(1)).findById(any());
        Assertions.assertThat(resultado.getId()).isEqualTo(testsUtil.getUiidPadrao());

    }

    @Test
    @DisplayName("Erro ao consultar Cliente")
    void consultarClientePorIdInexistente() {
        when(clienteRepository.findById(testsUtil.getUiidPadrao())).thenReturn(null);

        ObjetoNaoEncontrado exception = assertThrows(ObjetoNaoEncontrado.class, this::consultaVazia);

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(CLIENTE_NAO_ENCONTRADA));
    }

    private ClienteDto consultaVazia() {
        return clienteService.consultarClientePorId("");
    }

    @Test
    @DisplayName("Trazer clientes paginados")
    void carregarTodosClientes() {
        Page<Cliente> clientePage = new PageImpl<>(utilClienteTestUtil.clientes(), testsUtil.getPagePadrao(), utilClienteTestUtil.clientes().size());
        when(clienteRepository.findAll(any(Pageable.class))).thenReturn(clientePage);

        Page<ClienteDto> result = clienteService.carregarTodosClientes(testsUtil.getPagePadrao());


        assertNotNull(result);
        verify(clienteRepository, times(1)).findAll(testsUtil.getPagePadrao());
        Assertions.assertThat(result.getTotalElements()).isEqualTo(clientePage.getTotalElements());
    }

    @Test
    @DisplayName("trazer pagincao vazia")
    void paginacaoClientesVazia() {
        Page<Cliente> clientePage = new PageImpl<>(new ArrayList<>(), testsUtil.getPagePadrao(), 0);
        when(clienteRepository.findAll(any(Pageable.class))).thenReturn(clientePage);

        Page<ClienteDto> resultado = clienteService.carregarTodosClientes(testsUtil.getPagePadrao());


        assertNotNull(resultado);
        verify(clienteRepository, times(1)).findAll(testsUtil.getPagePadrao());
        Assertions.assertThat(resultado.getTotalElements()).isZero();
    }

    @Test
    @DisplayName("Remover Cliente não cadastrado")
    void removerClienteInexistente() {
        when(clienteRepository.findById(testsUtil.getUiidPadrao())).thenReturn(null);

        ObjetoNaoEncontrado exception = assertThrows(ObjetoNaoEncontrado.class, () -> {
            clienteService.removerCliente(testsUtil.getUiidDiferente());
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(CLIENTE_NAO_ENCONTRADA));
    }

    @Test
    @DisplayName("Remover Cliente com sucesso")
    void removerCliente() {
        when(clienteRepository.save(any(Cliente.class)))
                .thenReturn(utilClienteTestUtil.clientes().get(0));
        ClienteDto clienteCadastrada = clienteService.cadastrarCliente(utilClienteTestUtil.criarCliente());

        Optional<Cliente> cliente = Optional.ofNullable(modelMapper.map(utilClienteTestUtil.criarCliente(), Cliente.class));
        when(clienteRepository.findById(testsUtil.getUiidPadrao())).thenReturn(cliente);

        clienteService.removerCliente(testsUtil.getUiidPadrao());

        verify(clienteRepository, Mockito.times(1)).delete(any());
    }

    @Test
    @DisplayName("Cadastro Cliente com cidade")
    void cadastrarClienteComCidade() {
        ClienteDto cliente = utilClienteTestUtil.criarCliente();
        EnderecoDto enderecoDto = new EnderecoDto();
        enderecoDto.setCidade(utilCidadeTestUtil.criarCidade());
        cliente.setEndereco(enderecoDto);

        when(clienteRepository.save(any(Cliente.class)))
                .thenReturn(modelMapper.map(cliente, Cliente.class));

        ClienteDto clienteCadastrado = clienteService.cadastrarCliente(cliente);

        verify(clienteRepository, Mockito.times(1)).save(any());
        Assertions.assertThat(clienteCadastrado.getId()).isEqualTo(testsUtil.getUiidPadrao());
    }
}