package com.shopping.ecommerce.exception;

import com.shopping.ecommerce.pedido.Pedido;

public class PagamentoInvalidoException extends RuntimeException{

    public PagamentoInvalidoException(Pedido pedido) {
        super("Não foi possível validar o pagamento do pedido: " + pedido.getId());
    }
}
