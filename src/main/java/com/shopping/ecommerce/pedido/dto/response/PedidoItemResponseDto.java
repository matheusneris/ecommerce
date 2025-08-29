package com.shopping.ecommerce.pedido.dto.response;

import com.shopping.ecommerce.pedido.PedidoItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoItemResponseDto {
    private Long        idProduto;
    private String      nomeProduto;
    private BigDecimal  precoUnitario;
    private Integer     quantidade;

    public PedidoItemResponseDto(PedidoItem item) {
        this.idProduto = item.getProduto().getId();
        this.nomeProduto = item.getProduto().getNome();
        this.precoUnitario = item.getPrecoUnitario();
        this.quantidade = item.getQuantidade();
    }

    public static List<PedidoItemResponseDto> fromList(List<PedidoItem> itens) {
        return itens.stream()
                .map(PedidoItemResponseDto::new)
                .toList();
    }

}
