package com.shopping.ecommerce.exception.usuarioexception;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String username) {
        super("Não existe usuário com o username: " + username);
    }
}
