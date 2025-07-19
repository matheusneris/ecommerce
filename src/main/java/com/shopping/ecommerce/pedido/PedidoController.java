package com.shopping.ecommerce.pedido;

import com.shopping.ecommerce.pedido.dtos.request.PedidoRequestAlterarDto;
import com.shopping.ecommerce.pedido.dtos.request.PedidoRequestSalvarDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity criarPedido(@RequestBody @Valid PedidoRequestSalvarDto pedidoRequestSalvarDto) {
        pedidoService.criarPedido(pedidoRequestSalvarDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{idPedido}")
    public ResponseEntity alterarPedido(@PathVariable UUID idPedido, @RequestBody @Valid PedidoRequestAlterarDto pedidoRequestAlterarDto){
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.alterarPedidoPorId(idPedido, pedidoRequestAlterarDto));
    }

    @GetMapping("/{idPedido}")
    public ResponseEntity buscarPedido(@PathVariable UUID idPedido) {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.buscarPedidoPorId(idPedido));
    }

    @DeleteMapping("/{idPedido}")
    public ResponseEntity deletarPedido(@PathVariable UUID idPedido) {
        pedidoService.deletarPedidoPorId(idPedido);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}