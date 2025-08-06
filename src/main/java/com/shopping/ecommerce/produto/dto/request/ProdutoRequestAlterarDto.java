package com.shopping.ecommerce.produto.dto.request;

import java.math.BigDecimal;

public record ProdutoRequestAlterarDto(
        String nome,
        String fabricante,
        BigDecimal precoDeAquisicao,
        BigDecimal preco,
        Integer quantidadeEstoque
) {
}
