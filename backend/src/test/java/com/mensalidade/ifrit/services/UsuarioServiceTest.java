package com.mensalidade.ifrit.services;

import com.mensalidade.ifrit.dto.response.UsuarioCompletoResponse;
import com.mensalidade.ifrit.dto.response.UsuarioResponse;
import com.mensalidade.ifrit.models.Usuario;
import com.mensalidade.ifrit.repositories.UsuarioRepository;
import com.mensalidade.ifrit.services.exceptions.ObjetoNaoEncontrado;
import com.mensalidade.ifrit.utils.UsuarioTest;
import com.mensalidade.ifrit.utils.Util;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UsuarioServiceTest {

    private final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
    UsuarioTest utiUsuarioTest = new UsuarioTest();
    Util util = new Util();

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    @Autowired
    private UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        usuarioService = new UsuarioService(usuarioRepository, passwordEncoder, modelMapper);
    }


    @Test
    @DisplayName("Cadastro Usuario com sucesso")
    void cadastrarUsuario() {
        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(utiUsuarioTest.usuarios().get(0));
        UsuarioResponse usuarioCadastrado = usuarioService.cadastrarUsuario(utiUsuarioTest.criarUsuario());

        verify(usuarioRepository, Mockito.times(1)).save(any());
        Assertions.assertThat(usuarioCadastrado.getId()).isEqualTo(util.getUiidPadrao());
    }

    @Test
    @DisplayName("Consultar Usuario por ID com sucesso")
    void consultarUsuarioCompletoPorID() {
        Optional<Usuario> usuario = Optional.ofNullable(modelMapper.map(utiUsuarioTest.criarUsuario(), Usuario.class));
        when(usuarioRepository.findById(util.getUiidPadrao())).thenReturn(usuario);

        UsuarioCompletoResponse resultado = usuarioService.consultarUsuarioCompletoPorID(util.getUiidPadrao());

        assertNotNull(resultado);
        verify(usuarioRepository, Mockito.times(1)).findById(any());
        Assertions.assertThat(resultado.getId()).isEqualTo(util.getUiidPadrao());
    }

    @Test
    @DisplayName("Consultar Usuario por ID inexistente")
    void consultarUsuarioCompletoPorIDComErro() {
        when(usuarioRepository.findById(util.getUiidPadrao())).thenReturn(null);

        ObjetoNaoEncontrado exception = assertThrows(ObjetoNaoEncontrado.class, this::consultaVazia);

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(USUARIO_NAO_ENCONTRADO));
    }

    private UsuarioCompletoResponse consultaVazia() {
        return usuarioService.consultarUsuarioCompletoPorID("");
    }

    @Test
    @DisplayName("Consulta por Login com Sucesso")
    void findByLogin() {
        Optional<Usuario> usuario = Optional.ofNullable(modelMapper.map(utiUsuarioTest.criarUsuario(), Usuario.class));
        if (usuario.isPresent()) {
            Usuario u = modelMapper.map(utiUsuarioTest.criarUsuario(), Usuario.class);
            when(usuarioRepository.findByLogin(usuario.get().getLogin())).thenReturn((UserDetails) u);

            UsuarioCompletoResponse resultado = usuarioService.findByLogin(usuario.get().getLogin());

            assertNotNull(resultado);
            verify(usuarioRepository, Mockito.times(1)).findByLogin(any());
            Assertions.assertThat(resultado.getLogin()).isEqualTo(utiUsuarioTest.criarUsuario().getLogin());
        }
    }

    @Test
    @DisplayName("Consulta por Login Vazio")
    void findByLoginEmpty() {
        when(usuarioRepository.findByLogin(utiUsuarioTest.criarUsuario().getLogin())).thenReturn(null);

        UsuarioCompletoResponse resultado = usuarioService.findByLogin(utiUsuarioTest.criarUsuario().getLogin());
        assertNull(resultado);
        verify(usuarioRepository, Mockito.times(1)).findByLogin(any());
    }

    @Test
    void listarTodosUsuarios() {
    }

    @Test
    void atualizarUsuario() {
    }
}