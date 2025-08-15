package com.shopping.ecommerce.usuario.dto.request;

import com.shopping.ecommerce.usuario.enums.UsuarioPapelEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioCadastroRequestDto(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotNull
        UsuarioPapelEnum role
) {
}
