package com.shopping.ecommerce.pedido.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


import java.util.UUID;

public record ItemRequestAlterarDto (

        @NotNull
        UUID idProduto,
        @NotNull
        @Min(0)
        Integer quantidade
){
}
