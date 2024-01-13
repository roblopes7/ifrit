package com.mensalidade.ifrit.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class Util {
    private final String ID = "f4bdd67d-79b4-42a1-9124-3c176cd2575c";
    private final String ID_2 = "7921da3a-63f4-4b2a-9564-0f838b08c71e";

    public String getUiidPadrao(){
        return ID;
    }

    public String getUiidDiferente(){
        return ID_2;
    }

    public PageRequest getPagePadrao(){
        return PageRequest.of(
                0,
                10,
                Sort.Direction.valueOf("ASC"),
                "id");
    }
}
