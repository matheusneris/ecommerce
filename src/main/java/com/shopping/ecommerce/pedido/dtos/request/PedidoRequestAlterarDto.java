package com.shopping.ecommerce.pedido.dtos.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PedidoRequestAlterarDto(
        @NotEmpty
        List<ItemRequestAlterarDto> itens
){
}
