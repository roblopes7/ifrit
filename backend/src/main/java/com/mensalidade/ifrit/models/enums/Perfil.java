package com.mensalidade.ifrit.models.enums;

public enum Perfil {
    ADMIN("Admin"),
    FINANCEIRO("Financeiro"),
    COMERCIAL("Comercial");

    private String descricao;

    Perfil(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
