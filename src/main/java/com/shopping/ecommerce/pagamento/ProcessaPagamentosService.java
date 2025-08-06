package com.shopping.ecommerce.pagamento;

import com.shopping.ecommerce.email.dto.EmailDto;
import com.shopping.ecommerce.email.EmailService;
import com.shopping.ecommerce.email.producer.EnvioEmailProducer;
import com.shopping.ecommerce.exception.FalhaNaTransacaoException;
import com.shopping.ecommerce.exception.PagamentoInvalidoException;
import com.shopping.ecommerce.pagamento.enums.PagamentoStatusEnum;
import com.shopping.ecommerce.pedido.PedidoService;
import com.shopping.ecommerce.pedido.enums.PedidoStatusEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ProcessaPagamentosService {

    private final PedidoService pedidoService;
    private final EnvioEmailProducer emailProducer;
    private final PagamentoRepository repository;

    public ProcessaPagamentosService(PedidoService pedidoService, EmailService emailService, EnvioEmailProducer emailProducer, PagamentoRepository repository) {
        this.pedidoService = pedidoService;
        this.emailProducer = emailProducer;
        this.repository = repository;
    }

    public void processarPagamento(ProcessaPagamentos instrumento) {
        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(instrumento.getPedido());
        pagamento.setDataHoraPagamento(LocalDateTime.now());

        try {
            processaCobranca(instrumento);
            pagamento.setStatusPagamento(PagamentoStatusEnum.PAGAMENTO_RELIZADO_COM_SUCESSO);
            enviarEmailSucesso(instrumento);
        } catch (PagamentoInvalidoException | FalhaNaTransacaoException ex) {
            pagamento.setStatusPagamento(PagamentoStatusEnum.FALHA_NO_PAGAMENTO);
            enviarEmailFalha(instrumento);
        } finally {
            salvarTentativaPagamento(pagamento);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void processaCobranca(ProcessaPagamentos instrumento) {
        instrumento.cobrar();
        pedidoService.alterarStatusPedido(instrumento.getPedido(), PedidoStatusEnum.PAGAMENTO_EFETUADO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void salvarTentativaPagamento(Pagamento pagamento) {
        repository.save(pagamento);
    }

    private void enviarEmailSucesso(ProcessaPagamentos instrumento) {
        EmailDto email = new EmailDto(
                instrumento.getPedido().getEmailCliente(),
                "Pagamento realizado com sucesso",
                "O pedido de id " + instrumento.getPedido().getId() + " teve o pagamento confirmado com sucesso."
        );
        emailProducer.enviarEmail(email);
    }

    private void enviarEmailFalha(ProcessaPagamentos instrumento) {
        EmailDto email = new EmailDto(
                instrumento.getPedido().getEmailCliente(),
                "Falha no pagamento",
                "O pedido de id " + instrumento.getPedido().getId() + " falhou na etapa de pagamento."
        );
        emailProducer.enviarEmail(email);
    }
}
