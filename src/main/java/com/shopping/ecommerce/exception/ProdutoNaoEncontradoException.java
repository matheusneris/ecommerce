package com.shopping.ecommerce.exception;

import java.util.UUID;

public class ProdutoNaoEncontradoException extends RuntimeException {

    public ProdutoNaoEncontradoException(Long id){
        super("Não existe produto com o ID: " + id);
    }
}
