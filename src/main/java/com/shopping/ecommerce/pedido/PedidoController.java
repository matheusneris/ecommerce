package com.shopping.ecommerce.pedido;

import com.shopping.ecommerce.pedido.dto.request.*;
import com.shopping.ecommerce.pedido.producer.PedidoAlteracaoProducer;
import com.shopping.ecommerce.pedido.producer.PedidoCriacaoProducer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoCriacaoProducer pedidoCriacaoProducer;
    private final PedidoAlteracaoProducer pedidoAlteracaoProducer;

    public PedidoController(PedidoService pedidoService, PedidoCriacaoProducer pedidoCriacaoProducer, PedidoAlteracaoProducer pedidoAlteracaoProducer) {
        this.pedidoService = pedidoService;
        this.pedidoCriacaoProducer = pedidoCriacaoProducer;
        this.pedidoAlteracaoProducer = pedidoAlteracaoProducer;
    }

    @PostMapping("/cartao-credito")
    public ResponseEntity criarPedidoCartaoCredito(@RequestBody @Valid PedidoCartaoCreditoRequestDto pedidoDto) {

        PedidoPagamentoDto pedidoPagamento = new PedidoPagamentoDto(pedidoDto.pedido(), pedidoDto.pagamento());
        pedidoCriacaoProducer.criarPedidoQueue(pedidoPagamento);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/boleto")
    public ResponseEntity criarPedidoBoleto(@RequestBody @Valid PedidoBoletoRequestDto pedidoDto) {

        PedidoPagamentoDto pedidoPagamento = new PedidoPagamentoDto(pedidoDto.pedido(), pedidoDto.pagamento());
        pedidoCriacaoProducer.criarPedidoQueue(pedidoPagamento);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PatchMapping("/{idPedido}")
    public ResponseEntity alterarPedido(@PathVariable Long idPedido, @RequestBody @Valid PedidoRequestAlterarDto pedidoRequestAlterarDto){
        PedidoRequestAlterarDto pedidoComId = new PedidoRequestAlterarDto(
                idPedido, pedidoRequestAlterarDto.nomeCliente(),
                pedidoRequestAlterarDto.emailCliente(), pedidoRequestAlterarDto.itens());
        pedidoAlteracaoProducer.alterarPedidoQueue(pedidoComId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/{idPedido}")
    public ResponseEntity buscarPedido(@PathVariable Long idPedido) {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.buscarPedidoResponsePorId(idPedido));
    }

    @DeleteMapping("/{idPedido}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity deletarPedido(@PathVariable Long idPedido) {
        pedidoService.deletarPedidoPorId(idPedido);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}