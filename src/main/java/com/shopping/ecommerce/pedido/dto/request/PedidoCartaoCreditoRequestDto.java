package com.shopping.ecommerce.pedido.dto.request;

import com.shopping.ecommerce.pagamento.dto.PagamentoCartaoCreditoDto;
import jakarta.validation.Valid;

public record PedidoCartaoCreditoRequestDto(
        @Valid PedidoRequestSalvarDto pedido,
        @Valid PagamentoCartaoCreditoDto pagamento
) {
}
