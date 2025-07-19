package com.shopping.ecommerce.exception;

import java.util.UUID;

public class ProdutoNaoEncontradoException extends RuntimeException {

    public ProdutoNaoEncontradoException(UUID id){
        super("Não existe produto com o ID: " + id);
    }
}
