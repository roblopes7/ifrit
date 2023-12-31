package com.mensalidade.ifrit.services;

import com.mensalidade.ifrit.dto.CidadeDto;
import com.mensalidade.ifrit.models.Cidade;
import com.mensalidade.ifrit.repositories.CidadeRepository;
import com.mensalidade.ifrit.requests.CidadeIbgeRequest;
import com.mensalidade.ifrit.services.exceptions.ObjetoNaoEncontrado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CidadeService {

    @Value("${api.ibge}")
    private String URI_IBGE;

    private final String CIDADE_NAO_ENCONTRADA = "Cidade não encontrada.";

    private final CidadeRepository cidadeRepository;
    private final ModelMapper modelMapper;

    public CidadeService(CidadeRepository cidadeRepository, ModelMapper modelMapper) {
        this.cidadeRepository = cidadeRepository;
        this.modelMapper = modelMapper;
    }


    public CidadeDto cadastrarCidade(CidadeDto cidadeDto){
        Cidade cidade = modelMapper.map(cidadeDto , Cidade.class);
        cidade = cidadeRepository.save(cidade);
        return modelMapper.map(cidade, CidadeDto.class);
    }

    public void consultarCidadesPeloIbge() {

        //limpar tabela cidades antes de consultar ibge
        cidadeRepository.deleteAll();

        RestTemplate restTemplate = new RestTemplate();
        CidadeIbgeRequest[] cidadesIbge = restTemplate.getForObject(URI_IBGE, CidadeIbgeRequest[].class);
        List<Cidade> cidades = new ArrayList<>();
        if (cidadesIbge != null) {
            for (CidadeIbgeRequest cidadeIbge : cidadesIbge) {
                Cidade cidade = new Cidade();
                cidade.setCodigoIbge(cidadeIbge.getId());
                cidade.setNome(cidadeIbge.getNome());
                cidade.setPais("Brasil");
                cidade.setUf(cidadeIbge.getUf());
                cidades.add(cidade);

            }
            cidadeRepository.saveAll(cidades);
        }
    }

    public Page<CidadeDto> carregarTodasCidades(Pageable pageable) {
        return cidadeRepository.findAll(pageable).map(cidade -> modelMapper.map(cidade, CidadeDto.class));
    }

    public CidadeDto consultarCidadePorId(String id) {
       return cidadeRepository
                .findById(id)
                .map(cidade -> modelMapper.map(cidade, CidadeDto.class))
                .orElseThrow(() -> new ObjetoNaoEncontrado(CIDADE_NAO_ENCONTRADA));
    }

    public void removerCidade(String id) {
        Cidade cidade = cidadeRepository
                .findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontrado(CIDADE_NAO_ENCONTRADA));

        cidadeRepository.delete(cidade);
    }
}
