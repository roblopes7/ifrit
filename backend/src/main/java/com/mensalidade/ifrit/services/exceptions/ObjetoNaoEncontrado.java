package com.mensalidade.ifrit.services.exceptions;

public class ObjetoNaoEncontrado extends RuntimeException {
    public ObjetoNaoEncontrado(String msg) {
        super(msg);
    }

    public ObjetoNaoEncontrado(String msg, Throwable cause) {
        super(msg, cause);
    }
}
