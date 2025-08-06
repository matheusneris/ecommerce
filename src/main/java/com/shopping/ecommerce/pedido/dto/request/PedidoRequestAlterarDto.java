package com.shopping.ecommerce.pedido.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record PedidoRequestAlterarDto(
        Long idPedido,
        String nomeCliente,
        String emailCliente,
        @NotEmpty
        List<ItemRequestAlterarDto> itens
){
}
