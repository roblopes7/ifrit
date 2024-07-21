package com.mensalidade.ifrit.utils;

import com.mensalidade.ifrit.dto.EnderecoDto;
import com.mensalidade.ifrit.models.Endereco;

public class EnderecoTestUtil {

    CidadeTestUtil cidadeTestUtil = new CidadeTestUtil();

    public EnderecoDto criarEndereco() {
        EnderecoDto dto = new EnderecoDto();
        dto.setCidade(cidadeTestUtil.criarCidade());
        dto.setNumero("001");
        dto.setCep("12345-000");
        dto.setBairro("Bairro Padrao");
        dto.setLogradouro("Logradouro Padrao");
        return dto;
    }

    public Endereco EnderecoPadrao() {
        Endereco e = new Endereco();
        e.setCidade(cidadeTestUtil.cidades().get(0));
        e.setNumero("001");
        e.setCep("12345-000");
        e.setBairro("Bairro Padrao");
        e.setLogradouro("Logradouro Padrao");
        return e;
    }
}
