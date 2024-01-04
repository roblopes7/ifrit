package com.mensalidade.ifrit.repositories;

import com.mensalidade.ifrit.models.Cliente;
import com.mensalidade.ifrit.models.Empresa;
import com.mensalidade.ifrit.models.Fatura;
import com.mensalidade.ifrit.models.enums.StatusFatura;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class FaturaRepositoryTest {

    private final EntityManager entityManager;
    private final FaturaRepository faturaRepository;

    @Autowired
    FaturaRepositoryTest(EntityManager entityManager, FaturaRepository faturaRepository) {
        this.entityManager = entityManager;
        this.faturaRepository = faturaRepository;
    }

    @Test
    @DisplayName("Atualizar fatura para PAGA")
    void updateStatusFaturaByIdSuccess() {
        Fatura fatura = salvarFaturaTest();
        this.faturaRepository.updateStatusFaturaById(StatusFatura.PAGA, fatura.getId());
        Fatura atualizada = this.faturaRepository.getReferenceById(fatura.getId());
        assertThat(atualizada.getStatus()).isEqualTo(StatusFatura.PAGA);
    }

    private Fatura salvarFaturaTest(){
        Fatura fatura = new Fatura();
        fatura.setCliente(salvarClienteTest());
        fatura.setCompetencia("01/2024");
        fatura.setDescricao("Fatura Janeiro");
        fatura.setEmissao(new Date());
        fatura.setEmpresa(salvarEmpresaTest());
        fatura.setStatus(StatusFatura.PENDENTE);
        fatura.setValor(new BigDecimal("100.0"));
        fatura.setVencimento(new Date());

        this.entityManager.persist(fatura);
        return fatura;
    }

    private Cliente salvarClienteTest(){
        Cliente cliente = new Cliente();
        cliente.setCnpjCpf("00000000000");
        cliente.setResponsavel("Responsavel Cliente Teste");
        cliente.setNomeFantasia("Nome Fantasia Cliente Teste");
        cliente.setRazaoSocial("Razao Social Cliente Teste");

        this.entityManager.persist(cliente);
        return cliente;
    }

    private Empresa salvarEmpresaTest(){
        Empresa empresa = new Empresa();
        empresa.setCnpjCpf("11111111111");
        empresa.setResponsavel("Responsavel Empresa Teste");
        empresa.setNomeFantasia("Nome Fantasia Empresa Teste");
        empresa.setRazaoSocial("Razao Social Empresa Teste");

        this.entityManager.persist(empresa);
        return empresa;
    }

}