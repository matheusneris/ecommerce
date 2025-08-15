package com.shopping.ecommerce.exception;

import com.shopping.ecommerce.exception.pagamentoexception.FalhaNaTransacaoException;
import com.shopping.ecommerce.exception.pagamentoexception.PagamentoInvalidoException;
import com.shopping.ecommerce.exception.pagamentoexception.PagamentoNaoSuportadoException;
import com.shopping.ecommerce.exception.pedidoexception.PedidoNaoEncontradoException;
import com.shopping.ecommerce.exception.produtoexception.ProdutoNaoEncontradoException;
import com.shopping.ecommerce.exception.produtoexception.ProdutoSemEstoqueSuficienteException;
import com.shopping.ecommerce.exception.produtoexception.QuantidadeProdutoNegativaException;
import com.shopping.ecommerce.exception.usuarioexception.UsernameExistenteException;
import com.shopping.ecommerce.exception.usuarioexception.UsuarioNaoEncontradoException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public String usuarioNaoEncontrado(UsuarioNaoEncontradoException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(UsernameExistenteException.class)
    public String usernameExistente(UsernameExistenteException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String usernameExistente(AccessDeniedException ex) {
        return ex.getMessage();
    }

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
