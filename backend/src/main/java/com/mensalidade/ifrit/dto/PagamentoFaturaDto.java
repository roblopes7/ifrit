package com.mensalidade.ifrit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PagamentoFaturaDto extends RepresentationModel<PagamentoFaturaDto> implements Serializable {

    private String id;

    @NotNull
    private BigDecimal valorPago;

    @NotNull
    private BigDecimal desconto;

    @NotNull
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone = "America/Sao_Paulo")
    private Date dataRecebimento;

    private String observacao;

    @NotNull
    @JsonIgnore
    private FaturaDto fatura;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public BigDecimal getDesconto() {
        return desconto;
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public FaturaDto getFatura() {
        return fatura;
    }

    public void setFatura(FaturaDto fatura) {
        this.fatura = fatura;
    }
}
