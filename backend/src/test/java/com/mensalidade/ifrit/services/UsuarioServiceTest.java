package com.mensalidade.ifrit.services;

import com.mensalidade.ifrit.dto.response.UsuarioCompletoResponse;
import com.mensalidade.ifrit.dto.response.UsuarioResponse;
import com.mensalidade.ifrit.models.Usuario;
import com.mensalidade.ifrit.repositories.UsuarioRepository;
import com.mensalidade.ifrit.services.exceptions.ObjetoCadastradoException;
import com.mensalidade.ifrit.services.exceptions.ObjetoNaoEncontrado;
import com.mensalidade.ifrit.utils.UsuarioTestUtil;
import com.mensalidade.ifrit.utils.TestsUtil;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class UsuarioServiceTest {

    private final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
    UsuarioTestUtil utilUsuarioTestUtil = new UsuarioTestUtil();
    TestsUtil testsUtil = new TestsUtil();

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
                .thenReturn(utilUsuarioTestUtil.usuarios().get(0));
        UsuarioResponse usuarioCadastrado = usuarioService.cadastrarUsuario(utilUsuarioTestUtil.criarUsuario());

        verify(usuarioRepository, Mockito.times(1)).save(any());
        Assertions.assertThat(usuarioCadastrado.getId()).isEqualTo(testsUtil.getUiidPadrao());
    }

    @Test
    @DisplayName("Consultar Usuario por ID com sucesso")
    void consultarUsuarioCompletoPorID() {
        Optional<Usuario> usuario = Optional.ofNullable(modelMapper.map(utilUsuarioTestUtil.criarUsuario(), Usuario.class));
        when(usuarioRepository.findById(testsUtil.getUiidPadrao())).thenReturn(usuario);

        UsuarioCompletoResponse resultado = usuarioService.consultarUsuarioCompletoPorID(testsUtil.getUiidPadrao());

        assertNotNull(resultado);
        verify(usuarioRepository, Mockito.times(1)).findById(any());
        Assertions.assertThat(resultado.getId()).isEqualTo(testsUtil.getUiidPadrao());
    }

    @Test
    @DisplayName("Consultar Usuario por ID inexistente")
    void consultarUsuarioCompletoPorIDComErro() {
        when(usuarioRepository.findById(testsUtil.getUiidPadrao())).thenReturn(null);

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
        Optional<Usuario> usuario = Optional.ofNullable(modelMapper.map(utilUsuarioTestUtil.criarUsuario(), Usuario.class));
        if (usuario.isPresent()) {
            Usuario u = modelMapper.map(utilUsuarioTestUtil.criarUsuario(), Usuario.class);
            when(usuarioRepository.findByLogin(usuario.get().getLogin())).thenReturn((UserDetails) u);

            UsuarioCompletoResponse resultado = usuarioService.findByLogin(usuario.get().getLogin());

            assertNotNull(resultado);
            verify(usuarioRepository, Mockito.times(1)).findByLogin(any());
            Assertions.assertThat(resultado.getLogin()).isEqualTo(utilUsuarioTestUtil.criarUsuario().getLogin());
        }
    }

    @Test
    @DisplayName("Consulta por Login Vazio")
    void findByLoginEmpty() {
        when(usuarioRepository.findByLogin(utilUsuarioTestUtil.criarUsuario().getLogin())).thenReturn(null);

        UsuarioCompletoResponse resultado = usuarioService.findByLogin(utilUsuarioTestUtil.criarUsuario().getLogin());
        assertNull(resultado);
        verify(usuarioRepository, Mockito.times(1)).findByLogin(any());
    }

    @Test
    @DisplayName("Trazer usuarios paginados")
    void carregarTodosUsuarios() {
        Page<Usuario> usuarioPage = new PageImpl<>(utilUsuarioTestUtil.usuarios(), testsUtil.getPagePadrao(), utilUsuarioTestUtil.usuarios().size());
        when(usuarioRepository.findAll(any(Pageable.class))).thenReturn(usuarioPage);

        Page<UsuarioCompletoResponse> result = usuarioService.listarTodosUsuarios(true, testsUtil.getPagePadrao());


        assertNotNull(result);
        verify(usuarioRepository, times(1)).findAll(testsUtil.getPagePadrao());
        Assertions.assertThat(result.getTotalElements()).isEqualTo(usuarioPage.getTotalElements());
    }

    @Test
    @DisplayName("trazer pagincao vazia")
    void paginacaoUsuarioVazia() {
        Page<Usuario> usuarioPage = new PageImpl<>(new ArrayList<>(), testsUtil.getPagePadrao(), 0);
        when(usuarioRepository.findAll(any(Pageable.class))).thenReturn(usuarioPage);

        Page<UsuarioCompletoResponse> resultado = usuarioService.listarTodosUsuarios(true, testsUtil.getPagePadrao());


        assertNotNull(resultado);
        verify(usuarioRepository, times(1)).findAll(testsUtil.getPagePadrao());
        Assertions.assertThat(resultado.getTotalElements()).isZero();
    }

    @Test
    @DisplayName("Tentar cadastrar usuário com login já em uso")
    void cadastrarUsuarioLoginInvalido() {
        when(usuarioRepository.findByLogin(anyString()))
                .thenReturn(utilUsuarioTestUtil.usuarios().get(0));

        assertThrows(ObjetoCadastradoException.class, () -> {
            usuarioService.cadastrarUsuario(utilUsuarioTestUtil.criarUsuario());
        });
    }

    @Test
    @DisplayName("Tentar atualizar usuário com login já em uso")
    void atualizarUsuarioLoginInvalido() {
        when(usuarioRepository.findByLogin(anyString()))
                .thenReturn(utilUsuarioTestUtil.usuarios().get(1));

        assertThrows(ObjetoCadastradoException.class, () -> {
            usuarioService.atualizarUsuario(utilUsuarioTestUtil.criarUsuario());
        });
    }

    @Test
    @DisplayName("Atualizar usuário com sucesso")
    void atualizarUsuario() {

        Usuario usuario = utilUsuarioTestUtil.usuarios().get(0);

        when(usuarioRepository.findByLogin(anyString()))
                .thenReturn(usuario);

        usuario.setLogin("Login atualizado");
        usuario.setNome("Usuario atualizado");
        usuario.setEmail("Email@atualizado.com");

        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuario);

        UsuarioCompletoResponse usuarioAtualizado = usuarioService.atualizarUsuario(utilUsuarioTestUtil.criarUsuario());

        verify(usuarioRepository, Mockito.times(1)).save(any());
        Assertions.assertThat(usuarioAtualizado.getId()).isEqualTo(testsUtil.getUiidPadrao());
        Assertions.assertThat(usuarioAtualizado.getLogin()).isEqualTo("LOGIN ATUALIZADO"); //Login sempre Upper
        Assertions.assertThat(usuarioAtualizado.getNome()).isEqualTo("Usuario atualizado");
        Assertions.assertThat(usuarioAtualizado.getEmail()).isEqualTo("Email@atualizado.com");
    }

}