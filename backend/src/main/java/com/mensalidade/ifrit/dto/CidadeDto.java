package com.mensalidade.ifrit.dto;

import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class CidadeDto extends RepresentationModel<CidadeDto> implements Serializable {

    private String id;
    @NotBlank
    private String nome;
    private String uf;
    @NotBlank
    private String pais;

    public CidadeDto() {
    }

    public CidadeDto(String id, String nome, String pais, String uf) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
        this.pais = pais;
    }

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

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
