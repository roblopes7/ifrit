package com.mensalidade.ifrit.services;

import com.mensalidade.ifrit.dto.request.UsuarioRequest;
import com.mensalidade.ifrit.dto.response.UsuarioCompletoResponse;
import com.mensalidade.ifrit.dto.response.UsuarioResponse;
import com.mensalidade.ifrit.models.Usuario;
import com.mensalidade.ifrit.repositories.UsuarioRepository;
import com.mensalidade.ifrit.services.exceptions.ObjetoCadastradoException;
import com.mensalidade.ifrit.services.exceptions.ObjetoNaoEncontrado;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public UsuarioResponse cadastrarUsuario(UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findByLogin(request.getLogin());
        if(usuario != null){
            throw new ObjetoCadastradoException("Login já utilizado");
        }

        return modelMapper.map(salvarUsuario(request), UsuarioResponse.class);
    }

    private Usuario salvarUsuario(UsuarioRequest request){
        Usuario usuario = modelMapper.map(request, Usuario.class);
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public UsuarioCompletoResponse findByLogin(String login) {
        Usuario usuario = usuarioRepository.findByLogin(login.toUpperCase());
        if(usuario != null){
            return modelMapper.map(usuario, UsuarioCompletoResponse.class);
        }
        return null;
    }

    public UsuarioCompletoResponse consultarUsuarioCompletoPorID(String id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isPresent()){
            return modelMapper.map(usuario, UsuarioCompletoResponse.class);
        }
        throw new ObjetoNaoEncontrado("Usuário não encontrado");
    }

    public Page<UsuarioResponse> listarTodosUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(usuario -> modelMapper.map(usuario, UsuarioResponse.class));
    }

    public UsuarioCompletoResponse atualizarUsuario(UsuarioRequest request) {
        Usuario usuarioLogin = usuarioRepository.findByLogin(request.getLogin());

        if(usuarioLogin == null || usuarioLogin.getId().equals(request.getId())){
            return modelMapper.map(salvarUsuario(request), UsuarioCompletoResponse.class);
        } else {
            throw new ObjetoCadastradoException("Login já utilizado");
        }
    }
}
