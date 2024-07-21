package com.mensalidade.ifrit.utils;

import com.mensalidade.ifrit.dto.ContatoDto;
import com.mensalidade.ifrit.models.EmpresaContato;

public class ContatoTestUtil {

    TestsUtil testsUtil = new TestsUtil();
    CidadeTestUtil cidadeTestUtil = new CidadeTestUtil();

    public ContatoDto criarContato() {
        ContatoDto dto = new ContatoDto();
        dto.setId(testsUtil.getUiidPadrao());
        dto.setEmail("email1@mail.com");
        dto.setEmail2("email2@mail.com");
        dto.setTelefone("0123456789");
        dto.setTelefone2("9876543210");
        dto.setCelular("0000111122");
        dto.setResponsavel("Responsavel contato");
        return dto;
    }

    public EmpresaContato ContatoPadrao() {
        EmpresaContato c = new EmpresaContato();
        c.setId(testsUtil.getUiidPadrao());
        c.getContato().setEmail("email1@mail.com");
        c.getContato().setEmail2("email2@mail.com");
        c.getContato().setTelefone("0123456789");
        c.getContato().setTelefone2("9876543210");
        c.getContato().setCelular("0000111122");
        c.getContato().setResponsavel("Responsavel contato");
        return c;
    }
}
