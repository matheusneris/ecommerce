package com.shopping.ecommerce.produto.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
