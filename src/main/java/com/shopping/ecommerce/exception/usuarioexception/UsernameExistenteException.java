package com.shopping.ecommerce.exception.usuarioexception;

public class UsernameExistenteException extends RuntimeException {
    public UsernameExistenteException(String username) {
        super("O nome de usuário: " + username + " já existe.");
    }
}
