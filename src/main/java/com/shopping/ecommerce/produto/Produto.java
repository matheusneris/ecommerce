package com.shopping.ecommerce.produto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "TB_PRODUTOS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long        id;
    private String      nome;
    private String      fabricante;
    private BigDecimal  precoDeAquisicao;
    private BigDecimal  preco;
    private Integer     quantidadeEstoque;

}
