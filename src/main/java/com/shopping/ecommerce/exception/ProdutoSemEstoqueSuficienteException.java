package com.shopping.ecommerce.exception;

import java.util.UUID;

public class ProdutoSemEstoqueSuficienteException extends RuntimeException{

    public ProdutoSemEstoqueSuficienteException(String nomeProduto, UUID id, Integer quantidadeEstoque){
        super("O produto " + nomeProduto + " de id: " + id +
                "não está disponível na quantidade solicitada. Quantidade atual em estoque é: " + quantidadeEstoque);
    }

}
