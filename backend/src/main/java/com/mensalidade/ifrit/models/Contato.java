package com.mensalidade.ifrit.models;

import jakarta.persistence.Column;

public class Contato {

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "email2", length = 50)
    private String email2;

    @Column(name = "telefone", length = 25)
    private String telefone;

    @Column(name = "telefone2", length = 25)
    private String telefone2;

    @Column(name = "celular", length = 25)
    private String celular;

    @Column(name = "celular2", length = 25)
    private String celular2;

    @Column(name = "responsavel")
    private String responsavel;

    public Contato(String email, String email2, String telefone, String telefone2, String celular, String celular2, String responsavel) {
        this.email = email;
        this.email2 = email2;
        this.telefone = telefone;
        this.telefone2 = telefone2;
        this.celular = celular;
        this.celular2 = celular2;
        this.responsavel = responsavel;
    }

    public Contato() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCelular2() {
        return celular2;
    }

    public void setCelular2(String celular2) {
        this.celular2 = celular2;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
}
