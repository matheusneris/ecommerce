package com.shopping.ecommerce.exception.pedidoexception;

public class PedidoNaoEncontradoException extends RuntimeException{
    public PedidoNaoEncontradoException(Long id) {
        super("Não existe pedido com o id: " + id);
    }

}
