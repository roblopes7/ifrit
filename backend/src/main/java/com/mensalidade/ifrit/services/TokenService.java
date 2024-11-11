package com.mensalidade.ifrit.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.mensalidade.ifrit.config.TokenKey;
import com.mensalidade.ifrit.models.Usuario;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class TokenService {

    private final TokenKey tokenKey;

    @Autowired
    public TokenService(TokenKey tokenKey) {
        this.tokenKey = tokenKey;
    }

    public String gerarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenKey.getKey());
            return JWT
                    .create()
                    .withIssuer("ifrit")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(gerarDataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao criar token: " + e.getMessage());
        }
    }

    public String validarToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenKey.getKey());
            return JWT
                    .require(algorithm)
                    .withIssuer("ifrit")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException e) {
            return "";
        }
    }

    public Instant gerarDataExpiracao(){
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
    }
}
