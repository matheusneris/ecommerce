package com.shopping.ecommerce.pagamento;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shopping.ecommerce.pagamento.enums.PagamentoStatusEnum;
import com.shopping.ecommerce.pedido.Pedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "TB_PAGAMENTOS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long                    id;
    @OneToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    @JsonManagedReference
    private Pedido                  pedido;
    @Enumerated(EnumType.STRING)
    private PagamentoStatusEnum     statusPagamento;
    private LocalDateTime           dataHoraPagamento;

}
