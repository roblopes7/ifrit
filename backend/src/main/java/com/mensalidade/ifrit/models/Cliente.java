package com.mensalidade.ifrit.models;

import jakarta.persistence.*;

@Entity(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "razao_social")
    private String razaoSocial;
    @Column(name = "nome_fantasia")
    private String nomeFantasia;
    @Column(name = "responsavel")
    private String responsavel;
    @Column(name = "cnpj")
    private String cnpjCpf;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idcliente_contato")
    private ClienteContato contato;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idcliente_endereco")
    private ClienteEndereco endereco;

    public Cliente() {
    }

    public Cliente(String id, String razaoSocial, String nomeFantasia, String responsavel, ClienteContato contato, ClienteEndereco endereco) {
        this.id = id;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
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

    public ClienteContato getContato() {
        return contato;
    }

    public void setContato(ClienteContato contato) {
        this.contato = contato;
    }

    public ClienteEndereco getEndereco() {
        return endereco;
    }

    public void setEndereco(ClienteEndereco endereco) {
        this.endereco = endereco;
    }

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }
}
