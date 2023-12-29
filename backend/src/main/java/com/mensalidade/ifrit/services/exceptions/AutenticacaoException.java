package com.mensalidade.ifrit.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AutenticacaoException  extends RuntimeException{

    public AutenticacaoException(String message) {
        super(message);
    }
}
