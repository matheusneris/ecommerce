package com.shopping.ecommerce.produto.dtos.response;

import com.shopping.ecommerce.produto.Produto;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponseDto {

    private UUID        id;
    private String      nome;
    private String      fabricante;
    private BigDecimal  preco;
    private Integer     quantidadeEstoque;

    public ProdutoResponseDto(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.fabricante = produto.getFabricante();
        this.preco = produto.getPreco();
        this.quantidadeEstoque = produto.getQuantidadeEstoque();
    }

}
