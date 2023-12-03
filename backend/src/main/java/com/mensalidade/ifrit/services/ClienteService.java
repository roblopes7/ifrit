package com.mensalidade.ifrit.services;

import com.mensalidade.ifrit.dto.ClienteDto;
import com.mensalidade.ifrit.models.*;
import com.mensalidade.ifrit.repositories.ClienteRepository;
import com.mensalidade.ifrit.services.exceptions.ObjetoNaoEncontrado;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    private final String CLIENTE_NAO_ENCONTRADA = "Cliente não encontrado.";

    public ClienteService(ClienteRepository clienteRepository, ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }

    public ClienteDto cadastrarCliente(ClienteDto clienteDto){
        Cliente cliente = converterCliente(clienteDto);
        Cliente clienteSalva = clienteRepository.save(cliente);
        return modelMapper.map(clienteSalva, ClienteDto.class);
    }

    private Cliente converterCliente(ClienteDto dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setResponsavel(dto.getResponsavel());
        cliente.setRazaoSocial(dto.getRazaoSocial());
        cliente.setNomeFantasia(dto.getNomeFantasia());
        cliente.setCnpjCpf(dto.getCnpjCpf());

        ClienteEndereco clienteEndereco = new ClienteEndereco();
        clienteEndereco.setId(dto.getEndereco().getId());
        Endereco endereco = new Endereco();
        endereco.setBairro(dto.getEndereco().getBairro());
        endereco.setCep(dto.getEndereco().getCep());
        endereco.setLogradouro(dto.getEndereco().getLogradouro());
        endereco.setNumero(dto.getEndereco().getNumero());

        if(dto.getEndereco().getCidade() != null){
            Cidade cidade = modelMapper.map(dto.getEndereco().getCidade(), Cidade.class);
            endereco.setCidade(cidade);
        }
        clienteEndereco.setEndereco(endereco);

        cliente.setEndereco(clienteEndereco);

        ClienteContato clienteContato = new ClienteContato();
        clienteContato.setId(dto.getContato().getId());
        Contato contato = new Contato();
        contato.setCelular(dto.getContato().getCelular());
        contato.setEmail(dto.getContato().getEmail());
        contato.setResponsavel(dto.getContato().getResponsavel());
        contato.setTelefone(dto.getContato().getTelefone());
        contato.setTelefone2(dto.getContato().getTelefone2());
        contato.setCelular2(dto.getContato().getCelular2());
        clienteContato.setContato(contato);

        cliente.setContato(clienteContato);

        return cliente;
    }

    public ClienteDto consultarClientePorId(String id) {
        return clienteRepository
                .findById(id)
                .map(cliente -> modelMapper.map(cliente, ClienteDto.class))
                .orElseThrow(() -> new ObjetoNaoEncontrado(CLIENTE_NAO_ENCONTRADA));
    }

    public Page<ClienteDto> carregarTodosClientes(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(cliente -> modelMapper.map(cliente, ClienteDto.class));
    }

    public void removerCliente(String id) {
        Cliente cliente = clienteRepository
                .findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontrado(CLIENTE_NAO_ENCONTRADA));

        clienteRepository.delete(cliente);
    }
}
