package com.shopping.ecommerce.produto.dto.request;

public record RetirarEstoqueDto(
        Long idProduto,
        Integer quantidade
) {
}