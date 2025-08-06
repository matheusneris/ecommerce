package com.shopping.ecommerce.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.MailException;
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

    @ExceptionHandler(PagamentoNaoSuportadoException.class)
    public String PagamentoNaoSuportadoException(PagamentoNaoSuportadoException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(PagamentoInvalidoException.class)
    public String pagamentoInvalido(PagamentoInvalidoException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(FalhaNaTransacaoException.class)
    public String falhaNaTransacaoPagamento(FalhaNaTransacaoException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(MailException.class)
    public String falhaAoEnviarEmail(MailException ex) {
        return "Falha ao enviar e-mail. " + ex.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgumentException(IllegalArgumentException ex) {
        return "Argumento não suportado. " + ex.getMessage();
    }
}
