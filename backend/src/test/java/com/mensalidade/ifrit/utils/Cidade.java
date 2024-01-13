package com.mensalidade.ifrit.utils;

import com.mensalidade.ifrit.dto.CidadeDto;

import java.util.Arrays;
import java.util.List;

public class Cidade {
    Util util = new Util();

    public CidadeDto criarCidade(){
        CidadeDto dto = new CidadeDto();
        dto.setId(util.getUiidPadrao());
        dto.setNome("Cidade Teste");
        dto.setPais("Brasil");
        dto.setUf("PR");
        dto.setCodigoIbge("123456789");
        return dto;
    }

    public List<com.mensalidade.ifrit.models.Cidade> cidades(){
        return  Arrays.asList(
                new com.mensalidade.ifrit.models.Cidade(util.getUiidPadrao(), "Cidade1", "Brasil", "PR", "123456789"),
                new com.mensalidade.ifrit.models.Cidade(util.getUiidDiferente(), "Cidade2", "Brasil", "SP", "987654321")
        );
    }
}
