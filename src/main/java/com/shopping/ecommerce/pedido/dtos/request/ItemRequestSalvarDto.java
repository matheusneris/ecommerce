package com.shopping.ecommerce.pedido.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record ItemRequestSalvarDto(
        @NotNull
        UUID idProduto,
        @NotNull
        @Positive
        Integer quantidade
) {
}