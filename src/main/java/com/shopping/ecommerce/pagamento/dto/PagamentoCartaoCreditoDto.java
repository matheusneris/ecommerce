package com.shopping.ecommerce.pagamento.dto;

import jakarta.validation.constraints.NotBlank;

public record PagamentoCartaoCreditoDto(
        @NotBlank String numeroCartao,
        @NotBlank String titularCartao,
        @NotBlank String validade,
        @NotBlank String cvv
) implements PagamentoRequest {
}
