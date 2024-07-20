package com.mensalidade.ifrit.utils;

import com.mensalidade.ifrit.dto.MensalidadeDto;
import com.mensalidade.ifrit.models.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MensalidadeTest {
    TestsUtil testsUtil = new TestsUtil();
    ClienteTest clienteTest = new ClienteTest();
    EmpresaTest empresaTest = new EmpresaTest();

    private final String DESCRICAO_PADRAO = "Mensalidade Teste 1";
    private final Date DATE_PADRAO = new Date(124, Calendar.JULY, 20); // 20/07/2024
    private final BigDecimal VALOR_PADRAO = new BigDecimal("100.0");

    public MensalidadeDto criarMensalidade() {
        MensalidadeDto dto = new MensalidadeDto();
        dto.setId(testsUtil.getUiidPadrao());
        dto.setCliente(clienteTest.criarCliente());
        dto.setDescricao(DESCRICAO_PADRAO);
        dto.setEmissao(DATE_PADRAO);
        dto.setVencimento(DATE_PADRAO);
        dto.setValor(VALOR_PADRAO);
        return dto;
    }

    public List<Mensalidade> mensalidades() {
        return Arrays.asList(
                new Mensalidade(testsUtil.getUiidPadrao(), VALOR_PADRAO,
                        DESCRICAO_PADRAO, DATE_PADRAO, DATE_PADRAO,
                        empresaTest.empresaPadrao(), clienteTest.clientes().get(0), "obs 1"),
                new Mensalidade(testsUtil.getUiidDiferente(), new BigDecimal("200.0"),
                        "Descrição 2", new Date(), new Date(),
                        empresaTest.empresaPadrao(), clienteTest.clientes().get(1), "obs 2")

        );
    }
}
