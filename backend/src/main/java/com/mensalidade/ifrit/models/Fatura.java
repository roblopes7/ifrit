package com.mensalidade.ifrit.models;

import com.mensalidade.ifrit.models.enums.StatusFatura;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "fatura")
public class Fatura {

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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusFatura status;

    @Column(name = "competencia")
    private String competencia;

    @Column(name = "observacao")
    private String observacao;

    @OneToMany(targetEntity = PagamentoFatura.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fatura", orphanRemoval = true)
    private List<PagamentoFatura> pagamentos = new ArrayList<>();

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

    public StatusFatura getStatus() {
        return status;
    }

    public void setStatus(StatusFatura status) {
        this.status = status;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<PagamentoFatura> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<PagamentoFatura> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public BigDecimal getRestante() {
        BigDecimal pago = getPagamentos().stream().map(PagamentoFatura::getValorPago).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal desconto = getPagamentos().stream().map(PagamentoFatura::getDesconto).reduce(BigDecimal.ZERO, BigDecimal::add);

        return pago.subtract(desconto, MathContext.DECIMAL64);
    }
}
