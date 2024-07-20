package com.mensalidade.ifrit.utils;

import com.mensalidade.ifrit.dto.ClienteDto;
import com.mensalidade.ifrit.dto.ContatoDto;
import com.mensalidade.ifrit.dto.EnderecoDto;
import com.mensalidade.ifrit.models.*;

import java.util.Arrays;
import java.util.List;

public class ClienteTest {
    TestsUtil testsUtil = new TestsUtil();

    public ClienteDto criarCliente() {
        ClienteDto cliente = new ClienteDto();
        cliente.setContato(new ContatoDto());
        cliente.setResponsavel("Cliente Responsavel");
        cliente.setNomeFantasia("Cliente Nome Fantasia");
        cliente.setEndereco(new EnderecoDto());
        cliente.setCnpjCpf("0123456789012");
        cliente.setId(testsUtil.getUiidPadrao());

        return cliente;
    }

    public List<Cliente> clientes() {
        return Arrays.asList(
                new Cliente(testsUtil.getUiidPadrao(), "Cliente Teste Razao Social", "Cliente Teste Nome Fantasia", "Cliente Responsavel", new ClienteContato(), new ClienteEndereco()),
                new Cliente(testsUtil.getUiidDiferente(), "Cliente Teste Razao Social 2", "Cliente Teste Nome Fantasia 2", "Cliente Responsavel 2", new ClienteContato(), new ClienteEndereco())
                );
    }
}
