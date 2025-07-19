package com.shopping.ecommerce.pedido;

import com.shopping.ecommerce.produto.Produto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "TB_PEDIDO_ITEM")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID        id;
    @ManyToOne
    private Pedido      pedido;
    @ManyToOne
    private Produto     produto;
    private BigDecimal  precoUnitario;
    private Integer     quantidade;

}
