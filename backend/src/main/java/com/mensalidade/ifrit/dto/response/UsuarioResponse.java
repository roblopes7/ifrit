package com.mensalidade.ifrit.dto.response;

import com.mensalidade.ifrit.models.enums.Perfil;

import java.io.Serializable;
import java.util.Objects;

public class UsuarioResponse implements Serializable {

    private String id;

    private String login;

    private String nome;

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

        UsuarioResponse that = (UsuarioResponse) o;

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
