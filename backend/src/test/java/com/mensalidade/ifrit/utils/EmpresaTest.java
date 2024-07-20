package com.mensalidade.ifrit.utils;

import com.mensalidade.ifrit.models.Empresa;

public class EmpresaTest {
    TestsUtil testsUtil = new TestsUtil();

    public Empresa empresaPadrao() {
        Empresa e = new Empresa();
        e.setId(testsUtil.getUiidPadrao());
        e.setResponsavel("Responsavel Padrao");
        e.setNomeFantasia("Nome Fantasia Padrao");
        e.setRazaoSocial("Razao Social Padrao");
        e.setCnpjCpf("93963073000160"); //Gerador Aleatorio
        return e;
    }
}
