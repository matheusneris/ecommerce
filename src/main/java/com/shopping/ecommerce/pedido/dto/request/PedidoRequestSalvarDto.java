package com.shopping.ecommerce.pedido.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record PedidoRequestSalvarDto(
        @NotBlank
        String nomeCliente,
        @NotBlank
        @Email
        String emailCliente,
        @NotEmpty
        @Valid
        List<ItemRequestSalvarDto> itens
) {
}
