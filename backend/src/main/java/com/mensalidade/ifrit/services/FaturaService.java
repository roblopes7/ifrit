package com.mensalidade.ifrit.services;

import com.mensalidade.ifrit.dto.FaturaDto;
import com.mensalidade.ifrit.dto.PagamentoFaturaDto;
import com.mensalidade.ifrit.models.Fatura;
import com.mensalidade.ifrit.models.PagamentoFatura;
import com.mensalidade.ifrit.models.enums.StatusFatura;
import com.mensalidade.ifrit.repositories.FaturaRepository;
import com.mensalidade.ifrit.repositories.PagamentoFaturaRepository;
import com.mensalidade.ifrit.services.exceptions.ObjetoNaoEncontrado;
import com.mensalidade.ifrit.services.exceptions.ValorAcimaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FaturaService {

    private final FaturaRepository faturaRepository;
    private final PagamentoFaturaRepository pagamentoFaturaRepository;
    private final ModelMapper modelMapper;
    private final String FATURA_NAO_ENCONTRADA = "Fatura não encontrada.";

    @PersistenceContext
    private EntityManager entityManager;


    public FaturaService(FaturaRepository faturaRepository, PagamentoFaturaRepository pagamentoFaturaRepository, ModelMapper modelMapper) {
        this.faturaRepository = faturaRepository;
        this.pagamentoFaturaRepository = pagamentoFaturaRepository;
        this.modelMapper = modelMapper;
    }


    public FaturaDto criarFatura(FaturaDto faturaDto) {
        Fatura fatura = converterFatura(faturaDto);
        Fatura faturaSalva = faturaRepository.save(fatura);
        return modelMapper.map(faturaSalva, FaturaDto.class);
    }

    private Fatura converterFatura(FaturaDto faturaDto) {
        return modelMapper.map(faturaDto, Fatura.class);
    }

    public FaturaDto consultarFaturaDtoPorId(String id) {
        return faturaRepository
                .findById(id)
                .map(fatura -> modelMapper.map(fatura, FaturaDto.class))
                .orElseThrow(() -> new ObjetoNaoEncontrado(FATURA_NAO_ENCONTRADA));
    }

    public Fatura consultarFaturaPorId(String id) {
        return faturaRepository
                .findById(id)
               .orElseThrow(() -> new ObjetoNaoEncontrado(FATURA_NAO_ENCONTRADA));
    }

    public Page<FaturaDto> carregarTodasFaturas(Pageable pageable) {
        return faturaRepository.findAll(pageable).map(fatura -> modelMapper.map(fatura, FaturaDto.class));
    }

    public void removerFatura(String id) {
        Fatura fatura = faturaRepository
                .findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontrado(FATURA_NAO_ENCONTRADA));

        faturaRepository.delete(fatura);
    }

    @Transactional
    public PagamentoFaturaDto fazerPagamento(String id, PagamentoFaturaDto pagamentoFaturaDto) {
        Fatura fatura = consultarFaturaPorId(id);

        PagamentoFatura pagamentoFatura = modelMapper.map(pagamentoFaturaDto, PagamentoFatura.class);
        pagamentoFatura.setFatura(fatura);

        validarValorPagamento(pagamentoFatura, fatura);

        PagamentoFatura pagamentoSalvo = pagamentoFaturaRepository.save(pagamentoFatura);

        entityManager.flush();

        fatura.getPagamentos().add(pagamentoSalvo);
        validarAtualizarStatusFatura(fatura);

        return modelMapper.map(pagamentoSalvo, PagamentoFaturaDto.class);
    }

    private void validarValorPagamento(PagamentoFatura pagamentoFatura, Fatura fatura) {
        if(StatusFatura.PAGA.equals(fatura.getStatus()) || pagamentoFatura.getValorComDesconto().compareTo(fatura.getRestante()) > 0){
            throw new ValorAcimaException();
        }
    }



    @Transactional
    public void validarAtualizarStatusFatura(Fatura fatura) {
        if(fatura.getRestante().compareTo(fatura.getValor()) == 0){
            atualizarStatusFatura(fatura.getId(), StatusFatura.PAGA);
        } else if(StatusFatura.PAGA.equals(fatura.getStatus())){
            atualizarStatusFatura(fatura.getId(), StatusFatura.PENDENTE);
        }
    }

    public void removerPagamento(String id) {
        PagamentoFatura pagamento = pagamentoFaturaRepository
                .findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontrado(FATURA_NAO_ENCONTRADA));

        Fatura fatura = pagamento.getFatura();
        fatura.getPagamentos().remove(pagamento);
        pagamentoFaturaRepository.delete(pagamento);
        validarAtualizarStatusFatura(fatura);
    }

    public void atualizarStatusFatura(String id, StatusFatura statusFatura){
        faturaRepository.updateStatusFaturaById(statusFatura, id);
    }
}
