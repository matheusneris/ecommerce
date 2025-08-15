package com.shopping.ecommerce.exception.pagamentoexception;

import com.shopping.ecommerce.pedido.Pedido;

public class FalhaNaTransacaoException extends RuntimeException{

    public FalhaNaTransacaoException(Pedido pedido) {
        super("Houve uma falha de transação no pagamento do pedido: " + pedido.getId() );
    }
}
