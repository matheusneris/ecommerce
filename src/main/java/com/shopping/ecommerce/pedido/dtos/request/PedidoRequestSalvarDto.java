package com.shopping.ecommerce.pedido.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PedidoRequestSalvarDto(
        @NotEmpty
        @Valid
        List<ItemRequestSalvarDto> itens
) {
}
