package com.shopping.ecommerce.exception.pagamentoexception;

public class PagamentoNaoSuportadoException extends RuntimeException {

    public PagamentoNaoSuportadoException() {
        super("Tipo de pagamento não suportado. Tente novamente com outro meio de pagamento.");
    }

}
