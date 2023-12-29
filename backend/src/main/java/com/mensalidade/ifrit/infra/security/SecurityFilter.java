package com.mensalidade.ifrit.infra.security;

import com.mensalidade.ifrit.repositories.UsuarioRepository;
import com.mensalidade.ifrit.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final String AUTHRIZATION = "Authorization";
    private final String EMPTY_SPACE = " ";
    private static final Integer TOKEN_INDEX = 1;

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = recoverToken(request);
        if(token != null){
            var login = tokenService.validarToken(token);
            UserDetails user = usuarioRepository.findByLogin(login.toUpperCase());

            if(user  != null) {
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader(AUTHRIZATION);
        if(authHeader  == null) return null;

        if(authHeader.contains(EMPTY_SPACE)){
            return authHeader.split(EMPTY_SPACE)[TOKEN_INDEX];
        }
        return authHeader;
    }
}
