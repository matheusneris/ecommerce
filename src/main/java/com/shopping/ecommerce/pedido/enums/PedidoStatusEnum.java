package com.shopping.ecommerce.pedido.enums;

public enum PedidoStatusEnum {

    PEDIDO_REALIZADO_AGUARDANDO_PAGAMENTO(1),
    PAGAMENTO_EFETUADO(2),
    PEDIDO_ENVIADO(3);

    PedidoStatusEnum(int codigoStatus) {
    }
}
