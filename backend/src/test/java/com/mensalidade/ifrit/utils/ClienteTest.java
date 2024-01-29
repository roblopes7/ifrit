package com.mensalidade.ifrit.utils;

import com.mensalidade.ifrit.dto.ClienteDto;
import com.mensalidade.ifrit.dto.ContatoDto;
import com.mensalidade.ifrit.dto.EnderecoDto;
import com.mensalidade.ifrit.models.*;

import java.util.Arrays;
import java.util.List;

public class ClienteTest {
    Util util = new Util();

    public ClienteDto criarCliente() {
        ClienteDto cliente = new ClienteDto();
        cliente.setContato(new ContatoDto());
        cliente.setResponsavel("Cliente Responsavel");
        cliente.setNomeFantasia("Cliente Nome Fantasia");
        cliente.setEndereco(new EnderecoDto());
        cliente.setCnpjCpf("0123456789012");
        cliente.setId(util.getUiidPadrao());

        return cliente;
    }

    public List<Cliente> clientes() {
        return Arrays.asList(
                new Cliente(util.getUiidPadrao(), "Cliente Teste Razao Social", "Cliente Teste Nome Fantasia", "Cliente Responsavel", new ClienteContato(), new ClienteEndereco()),
                new Cliente(util.getUiidDiferente(), "Cliente Teste Razao Social 2", "Cliente Teste Nome Fantasia 2", "Cliente Responsavel 2", new ClienteContato(), new ClienteEndereco())
                );
    }
}
