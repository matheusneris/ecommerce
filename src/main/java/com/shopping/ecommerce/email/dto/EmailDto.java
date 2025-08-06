package com.shopping.ecommerce.email.dto;

public record EmailDto (
        String destinatario,
        String assunto,
        String mensagem
) {
}
