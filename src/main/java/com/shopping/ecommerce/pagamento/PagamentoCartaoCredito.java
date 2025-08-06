package com.shopping.ecommerce.pagamento;

import com.shopping.ecommerce.exception.FalhaNaTransacaoException;
import com.shopping.ecommerce.exception.PagamentoInvalidoException;
import com.shopping.ecommerce.pagamento.dto.PagamentoCartaoCreditoDto;
import com.shopping.ecommerce.pedido.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoCartaoCredito implements ProcessaPagamentos {

    private String numeroCartao;
    private String titularCartao;
    private String validade;
    private String cvv;
    private Pedido pedido;

    @Override
    public boolean validar() {
        return numeroCartao.length() == 16;
    }

    @Override
    public void cobrar() {
        if(!validar()){
            throw new PagamentoInvalidoException(pedido);
        }

        //Simulação de validação de transação
        double validacaoTransacao = Math.random();
        if (validacaoTransacao >= 0.5) {
            System.out.println("Cobrança efetuada com sucesso. Cartão: " + numeroCartao + " Valor: " + pedido.getValorPedido() + " Pedido: " + pedido.getId());
        } else {
            System.out.println("Falha no pagamento. Cartão: " + numeroCartao + " Valor: " + pedido.getValorPedido() + " Pedido: " + pedido.getId());
            throw new FalhaNaTransacaoException(pedido);
        }
    }

    public PagamentoCartaoCredito(PagamentoCartaoCreditoDto pagamentoDto, Pedido pedido) {
        this.numeroCartao = pagamentoDto.numeroCartao();
        this.titularCartao = pagamentoDto.titularCartao();
        this.validade = pagamentoDto.validade();
        this.cvv = pagamentoDto.cvv();
        this.pedido = pedido;
    }
}
