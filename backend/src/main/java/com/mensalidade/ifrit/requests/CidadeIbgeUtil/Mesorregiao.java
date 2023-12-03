package com.mensalidade.ifrit.requests.CidadeIbgeUtil;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Mesorregiao {
    private int id;
    private String nome;
    @JsonAlias("UF")
    private UF uf;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UF getUF() {
        return uf;
    }

    public void setUF(UF uf) {
        this.uf = uf;
    }
}
