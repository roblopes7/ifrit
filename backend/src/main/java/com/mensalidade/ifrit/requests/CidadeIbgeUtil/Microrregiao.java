package com.mensalidade.ifrit.requests.CidadeIbgeUtil;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Microrregiao {
    private int id;
    private String nome;
    @JsonAlias("mesorregiao")
    private Mesorregiao mesorregiao;

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

    public Mesorregiao getMesorregiao() {
        return mesorregiao;
    }

    public void setMesorregiao(Mesorregiao mesorregiao) {
        this.mesorregiao = mesorregiao;
    }
}
