package com.mensalidade.ifrit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IBGEConfig {

    @Value("${api.ibge:''}")
    private String URI_IBGE;

    public String getUriIBGE() {
        return URI_IBGE;
    }

    public void setUriBGEI(String URI_IBGE) {
        this.URI_IBGE = URI_IBGE;
    }
}
