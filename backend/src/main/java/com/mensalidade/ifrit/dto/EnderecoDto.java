package com.mensalidade.ifrit.dto;

import javax.validation.constraints.Max;

public class EnderecoDto {

    private String id;

    @Max(255)
    private String logradouro;

    @Max(20)
    private String numero;

    @Max(255)
    private String bairro;

    @Max(9)
    private String cep;

    private CidadeDto cidade;

    public EnderecoDto(String id, String logradouro, String numero, String bairro, String cep, CidadeDto cidade) {
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
    }

    public EnderecoDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public CidadeDto getCidade() {
        return cidade;
    }

    public void setCidade(CidadeDto cidade) {
        this.cidade = cidade;
    }
}
