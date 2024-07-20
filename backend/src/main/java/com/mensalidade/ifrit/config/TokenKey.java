package com.mensalidade.ifrit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenKey {

    @Value("${api.token.secret-key:test}")
    private String KEY;

    public String getKey() {
        return KEY;
    }

    public void setKey(String key) {
        this.KEY = key;
    }
}
