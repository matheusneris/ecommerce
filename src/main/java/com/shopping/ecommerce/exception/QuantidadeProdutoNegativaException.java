package com.shopping.ecommerce.exception;

import java.util.UUID;

public class QuantidadeProdutoNegativaException extends RuntimeException {

    public QuantidadeProdutoNegativaException(Long idProduto) {
        super("Quantidade informada do produto de id: " + idProduto + " é negativa!");
    }

}