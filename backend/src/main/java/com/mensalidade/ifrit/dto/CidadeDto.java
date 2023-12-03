package com.mensalidade.ifrit.dto;

import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class CidadeDto extends RepresentationModel<CidadeDto> implements Serializable {

    private String id;
    @NotBlank
    private String nome;
    private String codigoIbge;
    private String uf;
    @NotBlank
    private String pais;

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

    public String getCodigoIbge() {
        return codigoIbge;
    }

    public void setCodigoIbge(String codigoIbge) {
        this.codigoIbge = codigoIbge;
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
