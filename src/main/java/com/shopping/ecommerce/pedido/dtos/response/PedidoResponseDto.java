package com.shopping.ecommerce.pedido.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDto {
    private UUID            idPedido;
    private LocalDateTime   dataHoraPedido;
    private BigDecimal      valorPedido;
    private List<PedidoItemResponseDto> itens = new ArrayList<>();
}
