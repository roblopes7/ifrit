package com.mensalidade.ifrit.services;

import com.mensalidade.ifrit.repositories.UsuarioRepository;
import com.mensalidade.ifrit.utils.UsuarioTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    UsuarioTest utilUsuarioTest = new UsuarioTest();

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    @Autowired
    private AuthService authService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthService(usuarioRepository);
    }

    @Test
    @DisplayName("Carregar Usuario com sucesso")
    void loginUsuario() {
        String login = utilUsuarioTest.criarUsuario().getLogin().toUpperCase();
        when(usuarioRepository.findByLogin(login))
                .thenReturn(utilUsuarioTest.usuarios().get(0));
        UserDetails usuario = authService.loadUserByUsername(utilUsuarioTest.criarUsuario().getLogin());

        verify(usuarioRepository, Mockito.times(1)).findByLogin(login);
        Assertions.assertThat(usuario.getUsername()).isEqualTo(login);
    }

    @Test
    @DisplayName("Erro ao carregar Usuario inexistente")
    void loginUsuarioInexistente() {
        when(usuarioRepository.findByLogin(anyString()))
                .thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            authService.loadUserByUsername(anyString());
        });
    }
}
