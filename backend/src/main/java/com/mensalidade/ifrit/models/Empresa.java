package com.mensalidade.ifrit.models;

import jakarta.persistence.*;

@Entity(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "razao_social")
    private String razaoSocial;
    @Column(name = "nome_fantasia")
    private String nomeFantasia;
    @Column(name = "cnpj")
    private String cnpjCpf;
    @Column(name = "responsavel")
    private String responsavel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idempresa_contato")
    private EmpresaContato contato;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idempresa_endereco")
    private EmpresaEndereco endereco;

    public Empresa() {
    }

    public Empresa(String id, String razaoSocial, String nomeFantasia, String cnpjCpf, String responsavel, EmpresaContato contato, EmpresaEndereco endereco) {
        this.id = id;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.cnpjCpf = cnpjCpf;
        this.responsavel = responsavel;
        this.contato = contato;
        this.endereco = endereco;
    }

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

    public EmpresaContato getContato() {
        return contato;
    }

    public void setContato(EmpresaContato contato) {
        this.contato = contato;
    }

    public EmpresaEndereco getEndereco() {
        return endereco;
    }

    public void setEndereco(EmpresaEndereco endereco) {
        this.endereco = endereco;
    }

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }
}
