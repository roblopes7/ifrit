package com.mensalidade.ifrit.models;

import jakarta.persistence.*;

public class Endereco {

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "numero", length = 20)
    private String numero;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cep", length = 9)
    private String cep;

    @ManyToOne
    @JoinColumn(name = "idcidade")
    private Cidade cidade;

    public Endereco() {
    }

    public Endereco(String logradouro, String numero, String bairro, String cep, Cidade cidade) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
}
