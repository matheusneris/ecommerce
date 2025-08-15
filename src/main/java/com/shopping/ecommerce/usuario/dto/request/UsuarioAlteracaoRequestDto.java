package com.shopping.ecommerce.usuario.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UsuarioAlteracaoRequestDto(
        @NotBlank
        String usernameAtual,
        String novoUsername,
        String novoPassword
) {
}
