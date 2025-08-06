package com.shopping.ecommerce.produto.dto.response;

import com.shopping.ecommerce.produto.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponseDto {

    private Long        id;
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
