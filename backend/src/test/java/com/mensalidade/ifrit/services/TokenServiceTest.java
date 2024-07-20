package com.mensalidade.ifrit.services;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.mensalidade.ifrit.config.TokenKey;
import com.mensalidade.ifrit.models.Usuario;
import com.mensalidade.ifrit.services.exceptions.ObjetoNaoEncontrado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
public class TokenServiceTest {

    @Mock
    private TokenKey tokenKey;

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    public void setUp() {
        when(tokenKey.getKey()).thenReturn("test");
        tokenService = new TokenService(tokenKey);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGerarToken() {
        Usuario usuario = new Usuario("username");
        String token = tokenService.gerarToken(usuario);
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    public void testValidarToken() {
        String token = tokenService.gerarToken(new Usuario("username"));
        String subject = tokenService.validarToken(token);
        assertEquals("username", subject);
    }

    @Test
    public void testValidarTokenComTokenInvalido() {
        String invalidToken = "invalid_token";
        String subject = tokenService.validarToken(invalidToken);
        assertEquals("", subject);
    }

    @Test
    public void testGerarDataExpiracao() {
        Instant dataExpiracao = tokenService.gerarDataExpiracao();
        assertNotNull(dataExpiracao);
    }

}
