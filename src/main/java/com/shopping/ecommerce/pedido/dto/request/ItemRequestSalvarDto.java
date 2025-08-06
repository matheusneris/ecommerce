package com.shopping.ecommerce.pedido.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record ItemRequestSalvarDto(
        @NotNull
        Long idProduto,
        @NotNull
        @Positive
        Integer quantidade
) {
}