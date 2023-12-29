package com.mensalidade.ifrit.controllers.exceptions;

import com.mensalidade.ifrit.services.exceptions.ObjetoCadastradoException;
import com.mensalidade.ifrit.services.exceptions.ObjetoNaoEncontrado;
import com.mensalidade.ifrit.services.exceptions.ValorAcimaException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjetoNaoEncontrado.class)
    public ResponseEntity<StandardError> objectNotFound(ObjetoNaoEncontrado e,
                                                        HttpServletRequest request){
        StandardError se = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(se);
    }

    @ExceptionHandler(ValorAcimaException.class)
    public ResponseEntity<StandardError> valorAcima(ValorAcimaException e,
                                                        HttpServletRequest request){
        StandardError se = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(se);
    }

    @ExceptionHandler(ObjetoCadastradoException.class)
    public ResponseEntity<StandardError> objetoCadastrado(ObjetoCadastradoException e,
                                                    HttpServletRequest request){
        StandardError se = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(se);
    }

}
