package com.mensalidade.ifrit.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "mensalidade")
public class Mensalidade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "emissao")
    @Temporal(TemporalType.DATE)
    private Date emissao;

    @Column(name = "vencimento")
    @Temporal(TemporalType.DATE)
    private Date vencimento;

    @ManyToOne
    @JoinColumn(name = "idempresa")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "idcliente")
    private Cliente cliente;

    @Column(name = "observacao")
    private String observacao;

    public Mensalidade() {
    }

    public Mensalidade(String id, BigDecimal valor, String descricao, Date emissao, Date vencimento, Empresa empresa, Cliente cliente, String observacao) {
        this.id = id;
        this.valor = valor;
        this.descricao = descricao;
        this.emissao = emissao;
        this.vencimento = vencimento;
        this.empresa = empresa;
        this.cliente = cliente;
        this.observacao = observacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
