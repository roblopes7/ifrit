package com.mensalidade.ifrit.utils;

import com.mensalidade.ifrit.dto.EmpresaDto;
import com.mensalidade.ifrit.models.Empresa;

import java.util.Arrays;
import java.util.List;

public class EmpresaTestUtil {
    TestsUtil testsUtil = new TestsUtil();
    EnderecoTestUtil enderecoTestUtil = new EnderecoTestUtil();
    ContatoTestUtil contatoTestUtil = new ContatoTestUtil();

    public Empresa empresaPadrao() {
        Empresa e = new Empresa();
        e.setId(testsUtil.getUiidPadrao());
        e.setResponsavel("Responsavel Padrao");
        e.setNomeFantasia("Nome Fantasia Padrao");
        e.setRazaoSocial("Razao Social Padrao");
        e.setCnpjCpf("93963073000160"); //Gerador Aleatorio
        return e;
    }

    public EmpresaDto criarEmpresa() {
        EmpresaDto dto = new EmpresaDto();
        dto.setId(testsUtil.getUiidPadrao());
        dto.setResponsavel("Responsavel Padrao");
        dto.setNomeFantasia("Nome Fantasia Padrao");
        dto.setRazaoSocial("Razao Social Padrao");
        dto.setCnpjCpf("93963073000160"); //Gerador Aleatorio
        dto.setEndereco(enderecoTestUtil.criarEndereco());
        dto.setContato(contatoTestUtil.criarContato());
        return dto;
    }

    public List<Empresa> empresas() {
        return Arrays.asList(
                empresaPadrao(),
                new Empresa(testsUtil.getUiidDiferente(), "Razao Social 2", "Nome Fantasia 2",
                        "26959802000117", "Responsavel 2", null, null)
        );
    }
}
