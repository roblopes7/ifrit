package com.mensalidade.ifrit.dto;

import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ClienteDto extends RepresentationModel<ClienteDto> implements Serializable {
    private String id;
    @NotBlank
    @Min(3)
    @Max(255)
    private String razaoSocial;
    @Max(255)
    private String nomeFantasia;
    @Max(255)
    private String responsavel;

    private ContatoDto contato;

    private EnderecoDto endereco;

    private String cnpjCpf;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public ContatoDto getContato() {
        return contato;
    }

    public void setContato(ContatoDto contato) {
        this.contato = contato;
    }

    public EnderecoDto getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDto endereco) {
        this.endereco = endereco;
    }

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }
}
