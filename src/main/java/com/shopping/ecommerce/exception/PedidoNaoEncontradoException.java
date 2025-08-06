package com.shopping.ecommerce.exception;

import java.util.UUID;

public class PedidoNaoEncontradoException extends RuntimeException{
    public PedidoNaoEncontradoException(Long id) {
        super("Não existe pedido com o id: " + id);
    }

}
