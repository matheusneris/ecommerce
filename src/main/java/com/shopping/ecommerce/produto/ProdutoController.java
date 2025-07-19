package com.shopping.ecommerce.produto;

import com.shopping.ecommerce.produto.dtos.request.ProdutoRequestAlterarDto;
import com.shopping.ecommerce.produto.dtos.request.ProdutoRequestSalvarDto;
import com.shopping.ecommerce.produto.dtos.response.ProdutoResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/{idProduto}")
    public ResponseEntity<ProdutoResponseDto> consultarProduto(@PathVariable UUID idProduto) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.buscarProdutoPorId(idProduto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDto>> listarProdutos() {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.listarTodosProdutos());
    }

    @PostMapping
    public ResponseEntity<Produto> salvarProduto(@RequestBody @Valid ProdutoRequestSalvarDto produtoRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvarProduto(produtoRequestDto));
    }

    @PatchMapping("/alterar-produto/{idProduto}")
    public ResponseEntity<Produto> alterarProduto(@PathVariable UUID idProduto, @RequestBody ProdutoRequestAlterarDto produtoRequestAlterarDto) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.alterarProdutoPorId(idProduto, produtoRequestAlterarDto));
    }

    @DeleteMapping("/{idProduto}")
    public ResponseEntity deletarProduto(@PathVariable UUID idProduto) {
        produtoService.deletarProdutoPorId(idProduto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
