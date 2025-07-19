package com.shopping.ecommerce.pedido.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoItemResponseDto {
    private UUID        id;
    private String      nomeProduto;
    private BigDecimal  precoUnitario;
    private Integer     quantidade;

}
