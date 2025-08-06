package com.shopping.ecommerce.produto.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProdutoRequestSalvarDto(
        @NotBlank
        String nome,
        @NotBlank
        String fabricante,
        BigDecimal precoDeAquisicao,
        @Positive
        BigDecimal preco,
        Integer quantidadeEstoque
) {
}
