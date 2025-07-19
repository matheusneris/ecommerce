package com.shopping.ecommerce.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public String produtoNaoEncontrado(ProdutoNaoEncontradoException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ProdutoSemEstoqueSuficienteException.class)
    public String produtoSemEstoque(ProdutoSemEstoqueSuficienteException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    public String pedidoNaoEncontrado(PedidoNaoEncontradoException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(QuantidadeProdutoNegativaException.class)
    public String quantidadeProdutoNegativa(QuantidadeProdutoNegativaException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String recursoNaoEncontrado(EmptyResultDataAccessException ex) {
        return "Recurso não encontrado no sistema.";
    }
}
