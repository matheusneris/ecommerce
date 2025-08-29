package com.shopping.ecommerce.pedido.dto.response;

import com.shopping.ecommerce.pedido.Pedido;
import com.shopping.ecommerce.pedido.enums.PedidoStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDto {
    private Long                idPedido;
    private LocalDateTime       dataHoraPedido;
    private BigDecimal          valorPedido;
    private PedidoStatusEnum    statusPedido;
    private String              nomeCliente;
    private String              emailCliente;
    private List<PedidoItemResponseDto> itens = new ArrayList<>();

    public PedidoResponseDto(Pedido pedido) {
        setIdPedido(pedido.getId());
        setValorPedido(pedido.getValorPedido());
        setDataHoraPedido(pedido.getDataHoraPedido());
        setItens(PedidoItemResponseDto.fromList(pedido.getItens()));
        setStatusPedido(pedido.getStatusPedido());
        setNomeCliente(pedido.getNomeCliente());
        setEmailCliente(pedido.getNomeCliente());
    }
}
