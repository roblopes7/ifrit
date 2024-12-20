package com.mensalidade.ifrit.models;

import jakarta.persistence.*;

@Entity(name = "cidade")
public class Cidade {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "nome")
    private String nome;


    @Column(name = ("uf"))
    private String uf;

    @Column(name = "pais")
    private String pais;

    public Cidade() {
    }

    public Cidade( String id, String nome, String pais, String uf) {
        this.nome = nome;
        this.pais = pais;
        this.uf = uf;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return super.toString();
    }
}
