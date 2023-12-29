package com.mensalidade.ifrit.dto.response;

import com.mensalidade.ifrit.models.enums.Perfil;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Objects;

public class UsuarioCompletoResponse extends RepresentationModel<UsuarioCompletoResponse> implements Serializable {

    private String id;

    private String login;

    private String nome;

    private String email;

    private String telefone;

    private String celular;

    private Perfil perfil;

    private boolean ativo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UsuarioCompletoResponse that = (UsuarioCompletoResponse) o;

        if (!Objects.equals(id, that.id)) return false;
        return Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        return result;
    }
}
