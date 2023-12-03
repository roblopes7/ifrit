package com.mensalidade.ifrit.requests;

import com.mensalidade.ifrit.requests.CidadeIbgeUtil.Microrregiao;

public class CidadeIbgeRequest {
    private String id;
    private String nome;
    private Microrregiao microrregiao;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Microrregiao getMicrorregiao() {
        return microrregiao;
    }

    public void setMicrorregiao(Microrregiao microrregiao) {
        this.microrregiao = microrregiao;
    }

    public String getUf(){
        return this.microrregiao.getMesorregiao().getUF().getSigla();
    }
}
