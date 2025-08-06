package com.shopping.ecommerce.pagamento;

import com.shopping.ecommerce.pedido.Pedido;

public interface ProcessaPagamentos {

    boolean validar();
    void cobrar();

    Pedido getPedido();

}
