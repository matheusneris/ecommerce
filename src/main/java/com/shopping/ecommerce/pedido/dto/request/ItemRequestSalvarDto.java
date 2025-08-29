package com.shopping.ecommerce.pedido.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemRequestSalvarDto(
        @NotNull
        Long idProduto,
        @NotNull
        @Positive
        Integer quantidade
) {
}