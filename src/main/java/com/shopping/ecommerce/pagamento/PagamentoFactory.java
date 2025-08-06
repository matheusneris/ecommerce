package com.shopping.ecommerce.pagamento;

import com.shopping.ecommerce.exception.PagamentoNaoSuportadoException;
import com.shopping.ecommerce.pagamento.dto.PagamentoBoletoDto;
import com.shopping.ecommerce.pagamento.dto.PagamentoCartaoCreditoDto;
import com.shopping.ecommerce.pedido.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PagamentoFactory {

    public static ProcessaPagamentos criarProcessador(Object pagamentoDto, Pedido pedido) {
        if (pagamentoDto instanceof PagamentoCartaoCreditoDto dto) {
            return new PagamentoCartaoCredito(dto, pedido);
        } else if (pagamentoDto instanceof PagamentoBoletoDto dto) {
            return new PagamentoBoleto(dto, pedido);
        }
        throw new PagamentoNaoSuportadoException();
    }
}
