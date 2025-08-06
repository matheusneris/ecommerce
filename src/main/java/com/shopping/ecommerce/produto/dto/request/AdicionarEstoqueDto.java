package com.shopping.ecommerce.produto.dto.request;

public record AdicionarEstoqueDto(
        Long idProduto,
        Integer quantidade
) {
}