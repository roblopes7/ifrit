package com.mensalidade.ifrit.services;

import com.mensalidade.ifrit.dto.CidadeDto;
import com.mensalidade.ifrit.dto.EmpresaDto;
import com.mensalidade.ifrit.models.*;
import com.mensalidade.ifrit.repositories.EmpresaRepository;
import com.mensalidade.ifrit.services.exceptions.ObjetoNaoEncontrado;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.mensalidade.ifrit.repositories.specifications.EmpresaSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final ModelMapper modelMapper;
    private final String EMPRESA_NAO_ENCONTRADA = "Empresa não encontrada.";

    public EmpresaService(EmpresaRepository empresaRepository, ModelMapper modelMapper) {
        this.empresaRepository = empresaRepository;
        this.modelMapper = modelMapper;
    }

    public EmpresaDto cadastrarEmpresa(EmpresaDto empresaDto){
        Empresa empresa = converterEmpresa(empresaDto);
        Empresa empresaSalva = empresaRepository.save(empresa);
        return modelMapper.map(empresaSalva, EmpresaDto.class);
    }

    private Empresa converterEmpresa(EmpresaDto dto) {
        Empresa empresa = new Empresa();
        empresa.setId(dto.getId());
        empresa.setResponsavel(dto.getResponsavel());
        empresa.setRazaoSocial(dto.getRazaoSocial());
        empresa.setNomeFantasia(dto.getNomeFantasia());
        empresa.setCnpjCpf(dto.getCnpjCpf());

        EmpresaEndereco empresaEndereco = new EmpresaEndereco();
        empresaEndereco.setId(dto.getEndereco().getId());
        Endereco endereco = new Endereco();
        endereco.setBairro(dto.getEndereco().getBairro());
        endereco.setCep(dto.getEndereco().getCep());
        endereco.setLogradouro(dto.getEndereco().getLogradouro());
        endereco.setNumero(dto.getEndereco().getNumero());

        if(dto.getEndereco().getCidade() != null){
            Cidade cidade = modelMapper.map(dto.getEndereco().getCidade(), Cidade.class);
            endereco.setCidade(cidade);
        }
        empresaEndereco.setEndereco(endereco);

        empresa.setEndereco(empresaEndereco);

        EmpresaContato empresaContato = new EmpresaContato();
        empresaContato.setId(dto.getContato().getId());
        Contato contato = new Contato();
        contato.setCelular(dto.getContato().getCelular());
        contato.setEmail(dto.getContato().getEmail());
        contato.setResponsavel(dto.getContato().getResponsavel());
        contato.setTelefone(dto.getContato().getTelefone());
        contato.setTelefone2(dto.getContato().getTelefone2());
        contato.setCelular2(dto.getContato().getCelular2());
        empresaContato.setContato(contato);

        empresa.setContato(empresaContato);

        return empresa;
    }

    public EmpresaDto consultarEmpresaPorId(String id) {
        return empresaRepository
                .findById(id)
                .map(empresa -> modelMapper.map(empresa, EmpresaDto.class))
                .orElseThrow(() -> new ObjetoNaoEncontrado(EMPRESA_NAO_ENCONTRADA));
    }

    public Page<EmpresaDto> carregarTodasEmpresas(Pageable pageable) {
        return empresaRepository.findAll(pageable).map(empresa -> modelMapper.map(empresa, EmpresaDto.class));
    }

    public void removerEmpresa(String id) {
        Empresa empresa = empresaRepository
                .findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontrado(EMPRESA_NAO_ENCONTRADA));

        empresaRepository.delete(empresa);
    }

    public Page<EmpresaDto> filtrarEmpresas(String filtro,  Pageable pageable) {
        if( filtro == null || filtro.isEmpty()) {
          return this.carregarTodasEmpresas(pageable);
        }

        return empresaRepository.findAll(
                where(isIdEqualsTo(filtro))
                        .or(isRazaoSocialEqualsTo(filtro))
                        .or(isNomeFantasiaEqualsTo(filtro))
                        .or(isCnpjCpfEqualsTo(filtro))
                        .or(isResponsavelEqualsTo(filtro))
                        , pageable)
                .map(empresa -> modelMapper.map(empresa, EmpresaDto.class));
    }
}
