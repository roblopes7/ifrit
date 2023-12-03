package com.mensalidade.ifrit.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;

@Entity(name = "fatura_pagamento")
public class PagamentoFatura {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "valor_pago")
    private BigDecimal valorPago;

    @Column(name = "valor_desconto")
    private BigDecimal desconto;

    @Column(name = "data_recebimento")
    @Temporal(TemporalType.DATE)
    private Date dataRecebimento;

    @Column(name = "observacao")
    private String observacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idfatura")
    private Fatura fatura;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getValorPago() {
        return valorPago != null ? valorPago : BigDecimal.ZERO;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public BigDecimal getDesconto() {
        return desconto != null ? desconto : BigDecimal.ZERO;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public Fatura getFatura() {
        return fatura;
    }

    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public BigDecimal getValorComDesconto() {
        return getValorPago().subtract(getDesconto(), MathContext.DECIMAL64);
    }
}
