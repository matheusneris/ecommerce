package com.shopping.ecommerce.pedido;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shopping.ecommerce.pagamento.Pagamento;
import com.shopping.ecommerce.pedido.enums.PedidoStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "TB_PEDIDOS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long                id;
    private LocalDateTime       dataHoraPedido;
    private BigDecimal          valorPedido;
    private String              nomeCliente;
    private String              emailCliente;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PedidoItem>    itens = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private PedidoStatusEnum    statusPedido;
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    @JsonBackReference
    private Pagamento           pagamento;
}
