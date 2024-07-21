package com.mensalidade.ifrit.utils;

import com.mensalidade.ifrit.dto.CidadeDto;
import com.mensalidade.ifrit.models.Cidade;
import com.mensalidade.ifrit.requests.CidadeIbgeRequest;
import com.mensalidade.ifrit.requests.CidadeIbgeUtil.Mesorregiao;
import com.mensalidade.ifrit.requests.CidadeIbgeUtil.Microrregiao;
import com.mensalidade.ifrit.requests.CidadeIbgeUtil.UF;

import java.util.Arrays;
import java.util.List;

public class CidadeTestUtil {
    TestsUtil testsUtil = new TestsUtil();

    public CidadeDto criarCidade(){
        CidadeDto dto = new CidadeDto();
        dto.setId(testsUtil.getUiidPadrao());
        dto.setNome("Cidade Teste");
        dto.setPais("Brasil");
        dto.setUf("PR");
        return dto;
    }

    public List<Cidade> cidades(){
        return  Arrays.asList(
                new com.mensalidade.ifrit.models.Cidade(testsUtil.getUiidPadrao(), "Cidade1", "Brasil", "PR"),
                new com.mensalidade.ifrit.models.Cidade(testsUtil.getUiidDiferente(), "Cidade2", "Brasil", "SP")
        );
    }

    public CidadeIbgeRequest[] cidadesIbge(){
        return new CidadeIbgeRequest[]{
                new CidadeIbgeRequest(testsUtil.getUiidPadrao(), "Cidade1", getMicroRegiaoMock()),
                new CidadeIbgeRequest(testsUtil.getUiidDiferente(), "Cidade2", getMicroRegiaoMock())
        };
    }

    public Microrregiao getMicroRegiaoMock() {
        UF uf = new UF();
        uf.setId(1);
        uf.setNome("Parana");
        uf.setSigla("PR");

        Mesorregiao mesorregiao = new Mesorregiao();
        mesorregiao.setUF(uf);
        mesorregiao.setId(1);

        Microrregiao microrregiao = new Microrregiao();
        microrregiao.setId(1);
        microrregiao.setMesorregiao(mesorregiao);
        return microrregiao;
    }


}
