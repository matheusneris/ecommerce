package com.shopping.ecommerce.produto;

import com.shopping.ecommerce.produto.dto.request.ProdutoRequestAlterarDto;
import com.shopping.ecommerce.produto.dto.request.ProdutoRequestSalvarDto;
import com.shopping.ecommerce.produto.dto.response.ProdutoResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/{idProduto}")
    public ResponseEntity<ProdutoResponseDto> consultarProduto(@PathVariable Long idProduto) {
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
    public ResponseEntity<Produto> alterarProduto(@PathVariable Long idProduto, @RequestBody ProdutoRequestAlterarDto produtoRequestAlterarDto) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.alterarProdutoPorId(idProduto, produtoRequestAlterarDto));
    }

    @DeleteMapping("/{idProduto}")
    public ResponseEntity deletarProduto(@PathVariable Long idProduto) {
        produtoService.deletarProdutoPorId(idProduto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
