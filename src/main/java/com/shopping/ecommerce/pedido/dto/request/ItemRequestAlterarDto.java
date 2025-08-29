package com.shopping.ecommerce.pedido.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemRequestAlterarDto (

        @NotNull
        Long idProduto,
        @NotNull
        @Min(0)
        Integer quantidade
){
}
