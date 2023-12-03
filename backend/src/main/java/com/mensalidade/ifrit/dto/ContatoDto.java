package com.mensalidade.ifrit.dto;

import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Max;
import java.io.Serializable;

public class ContatoDto extends RepresentationModel<ContatoDto> implements Serializable {

    private String id;

    @Max(50)
    private String email;

    @Max(50)
    private String email2;

    @Max(25)
    private String telefone;

    @Max(25)
    private String telefone2;

    @Max(25)
    private String celular;

    @Max(25)
    private String celular2;

    private String responsavel;

    public ContatoDto(String id, String email, String email2, String telefone, String telefone2, String celular, String celular2, String responsavel) {
        this.id = id;
        this.email = email;
        this.email2 = email2;
        this.telefone = telefone;
        this.telefone2 = telefone2;
        this.celular = celular;
        this.celular2 = celular2;
        this.responsavel = responsavel;
    }

    public ContatoDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
