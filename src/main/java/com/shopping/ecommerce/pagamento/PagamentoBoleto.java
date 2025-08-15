package com.shopping.ecommerce.pagamento;

import com.shopping.ecommerce.exception.pagamentoexception.FalhaNaTransacaoException;
import com.shopping.ecommerce.exception.pagamentoexception.PagamentoInvalidoException;
import com.shopping.ecommerce.pagamento.dto.PagamentoBoletoDto;
import com.shopping.ecommerce.pedido.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoBoleto implements ProcessaPagamentos{

    private String      codigoBarras;
    private LocalDate   dataVencimento;
    private Pedido      pedido;

    @Override
    public boolean validar() {
        return codigoBarras != null;
    }

    @Override
    public void cobrar() {
        if(!validar()) {
            throw new PagamentoInvalidoException(pedido);
        }

        //Simulação de validação de transação
        double validacaoTransacao = Math.random();
        if (validacaoTransacao >= 0.5) {
            System.out.println("Pagamento feito com sucesso via boleto. Valor: " + pedido.getValorPedido() + " Pedido: " + pedido.getId());
        } else {
            System.out.println("Falha no pagamento do boleto. Valor: " + pedido.getValorPedido() + " Pedido: " + pedido.getId());
            throw new FalhaNaTransacaoException(pedido);
        }
    }

    public PagamentoBoleto(PagamentoBoletoDto pagamentoBoletoDto, Pedido pedido) {
        this.codigoBarras = pagamentoBoletoDto.codigoBarras();
        this.dataVencimento = pagamentoBoletoDto.dataVencimento();
        this.pedido = pedido;
    }
}
