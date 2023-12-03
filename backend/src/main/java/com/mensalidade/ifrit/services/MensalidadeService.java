package com.mensalidade.ifrit.services;

import com.mensalidade.ifrit.dto.MensalidadeDto;
import com.mensalidade.ifrit.models.*;
import com.mensalidade.ifrit.repositories.MensalidadeRepository;
import com.mensalidade.ifrit.services.exceptions.ObjetoNaoEncontrado;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MensalidadeService {

    private final MensalidadeRepository mensalidadeRepository;
    private final ModelMapper modelMapper;
    private final String MENSALIDADE_NAO_ENCONTRADA = "Mensalidade não encontrada.";

    public MensalidadeService(MensalidadeRepository mensalidadeRepository, ModelMapper modelMapper) {
        this.mensalidadeRepository = mensalidadeRepository;
        this.modelMapper = modelMapper;
    }

    public MensalidadeDto cadastrarMensalidade(MensalidadeDto mensalidadeDto){
        Mensalidade mensalidade = modelMapper.map(mensalidadeDto, Mensalidade.class);
        Mensalidade mensalidadeSalva = mensalidadeRepository.save(mensalidade);
        return modelMapper.map(mensalidadeSalva, MensalidadeDto.class);
    }


    public MensalidadeDto consultarMensalidadePorId(String id) {
        return mensalidadeRepository
                .findById(id)
                .map(mensalidade -> modelMapper.map(mensalidade, MensalidadeDto.class))
                .orElseThrow(() -> new ObjetoNaoEncontrado(MENSALIDADE_NAO_ENCONTRADA));
    }

    public Page<MensalidadeDto> carregarTodosMensalidades(Pageable pageable) {
        return mensalidadeRepository.findAll(pageable).map(mensalidade -> modelMapper.map(mensalidade, MensalidadeDto.class));
    }

    public void removerMensalidade(String id) {
        Mensalidade mensalidade = mensalidadeRepository
                .findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontrado(MENSALIDADE_NAO_ENCONTRADA));

        mensalidadeRepository.delete(mensalidade);
    }
}
