package com.shopping.ecommerce.pedido.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoRequestAlterarDto(
        @NotNull
        Long idPedido,
        @NotBlank
        String nomeCliente,
        @NotBlank
        @Email
        String emailCliente,
        @NotEmpty
        List<ItemRequestAlterarDto> itens
){
}
