package com.mensalidade.ifrit.models.enums;

public enum StatusFatura {


    PENDENTE("pendente"),
    CANCELADA("Cancelada"),
    PAGA("Paga");

    private String descricao;

    StatusFatura(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
