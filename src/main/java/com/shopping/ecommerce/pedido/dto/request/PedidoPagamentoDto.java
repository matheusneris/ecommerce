package com.shopping.ecommerce.pedido.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.shopping.ecommerce.pagamento.dto.PagamentoRequest;
import com.shopping.ecommerce.pagamento.dto.PagamentoBoletoDto;
import com.shopping.ecommerce.pagamento.dto.PagamentoCartaoCreditoDto;
import jakarta.validation.constraints.NotNull;

public record PedidoPagamentoDto(
        @NotNull
        PedidoRequestSalvarDto pedido,

        @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
        @JsonSubTypes({
                @JsonSubTypes.Type(value = PagamentoCartaoCreditoDto.class, name = "cartaoCredito"),
                @JsonSubTypes.Type(value = PagamentoBoletoDto.class, name = "boleto")
        })
        @NotNull
        PagamentoRequest dadosPagamento
) {
}
