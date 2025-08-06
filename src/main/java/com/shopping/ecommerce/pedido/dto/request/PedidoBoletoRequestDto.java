package com.shopping.ecommerce.pedido.dto.request;

import com.shopping.ecommerce.pagamento.dto.PagamentoBoletoDto;
import jakarta.validation.Valid;

public record PedidoBoletoRequestDto(
        @Valid PedidoRequestSalvarDto pedido,
        @Valid PagamentoBoletoDto pagamento
) {
}
