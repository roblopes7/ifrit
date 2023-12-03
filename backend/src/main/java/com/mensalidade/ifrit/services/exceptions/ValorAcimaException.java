package com.mensalidade.ifrit.services.exceptions;

public class ValorAcimaException extends RuntimeException {

    public ValorAcimaException() {
        super("Valor de pagamento acima da fatura restante.");
    }

    public ValorAcimaException(String msg) {
        super(msg);
    }

    public ValorAcimaException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
