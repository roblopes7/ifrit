package com.mensalidade.ifrit.models;

import jakarta.persistence.*;

@Entity(name = "cidade")
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String String;

    @Column(name = "nome")
    private String nome;

    @Column(name = "codigo_ibge")
    private String codigoIbge;

    @Column(name = ("uf"))
    private String uf;

    @Column(name = "pais")
    private String pais;


    public String getId() {
        return String;
    }

    public void setId(String id) {
        this.String = id;
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
