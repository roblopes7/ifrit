package com.mensalidade.ifrit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mensalidade.ifrit.models.enums.StatusFatura;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FaturaDto  extends RepresentationModel<FaturaDto> implements Serializable {

    private String id;

    private BigDecimal valor;

    private String descricao;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone = "America/Sao_Paulo")
    private Date emissao;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone = "America/Sao_Paulo")
    private Date vencimento;

    private EmpresaDto empresa;

    private ClienteDto cliente;

    private StatusFatura status;

    private String competencia;

    private String observacao;

    private List<PagamentoFaturaDto> pagamentos = new ArrayList<>();

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

    public EmpresaDto getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDto empresa) {
        this.empresa = empresa;
    }

    public ClienteDto getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDto cliente) {
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

    public List<PagamentoFaturaDto> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<PagamentoFaturaDto> pagamentos) {
        this.pagamentos = pagamentos;
    }
}
