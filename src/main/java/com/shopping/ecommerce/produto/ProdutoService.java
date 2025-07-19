package com.shopping.ecommerce.produto;

import com.shopping.ecommerce.exception.ProdutoNaoEncontradoException;
import com.shopping.ecommerce.exception.ProdutoSemEstoqueSuficienteException;
import com.shopping.ecommerce.exception.QuantidadeProdutoNegativaException;
import com.shopping.ecommerce.produto.dtos.request.ProdutoRequestAlterarDto;
import com.shopping.ecommerce.produto.dtos.request.ProdutoRequestSalvarDto;
import com.shopping.ecommerce.produto.dtos.response.ProdutoResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService (ProdutoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Produto salvarProduto(ProdutoRequestSalvarDto produtoRequestSalvarDto) {
        Produto produto = new Produto();
        produto.setNome(produtoRequestSalvarDto.nome());
        produto.setFabricante(produtoRequestSalvarDto.fabricante());
        produto.setPrecoDeAquisicao(produtoRequestSalvarDto.precoDeAquisicao());
        produto.setPreco(produtoRequestSalvarDto.preco());
        produto.setQuantidadeEstoque(produtoRequestSalvarDto.quantidadeEstoque());

        return repository.save(produto);
    }

    //Usado para criar novos pedidos
    public Produto consultarProdutoPorId(UUID id){
        Produto produto = repository.findById(id).orElseThrow(
                () -> new ProdutoNaoEncontradoException(id));
        return produto;
    }

    //Usado para consulta externa de produtos
    public ProdutoResponseDto buscarProdutoPorId(UUID id) {
        Produto produto = repository.findById(id).orElseThrow(
                () -> new ProdutoNaoEncontradoException(id));

        return new ProdutoResponseDto(produto);
    }

    public List<ProdutoResponseDto> listarTodosProdutos(){
        List<Produto> produtos = repository.findAll();
        List<ProdutoResponseDto> produtosResponse = new ArrayList<>();

        for(Produto produto : produtos){
            ProdutoResponseDto produtoResponse = new ProdutoResponseDto(produto);
            produtosResponse.add(produtoResponse);
        }

        return produtosResponse;
    }

    @Transactional
    public void deletarProdutoPorId(UUID id) {
        repository.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
        repository.deleteById(id);
    }

    @Transactional
    public Produto alterarProdutoPorId(UUID id, ProdutoRequestAlterarDto produtoRequestAlterarDto) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        setIfNotNull(produto::setNome , produtoRequestAlterarDto.nome());
        setIfNotNull(produto::setFabricante , produtoRequestAlterarDto.fabricante());
        setIfNotNull(produto::setPrecoDeAquisicao , produtoRequestAlterarDto.precoDeAquisicao());
        setIfNotNull(produto::setPreco , produtoRequestAlterarDto.preco());
        setIfNotNull(produto::setQuantidadeEstoque , produtoRequestAlterarDto.quantidadeEstoque());
       return repository.save(produto);

    }

    @Transactional
    public void retirarItensProdutoEstoque(UUID id , Integer quantidadeItensProduto) {
        if(quantidadeItensProduto < 0) {
            throw new QuantidadeProdutoNegativaException(id);
        }
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
        if(produto.getQuantidadeEstoque() < quantidadeItensProduto) {
            throw new ProdutoSemEstoqueSuficienteException(produto.getNome(), produto.getId(), produto.getQuantidadeEstoque());
        } else {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidadeItensProduto);
            repository.save(produto);
        }
    }

    @Transactional
    public void adicionarItensProdutoEstoque(UUID id , Integer quantidadeItensProduto) {
        if(quantidadeItensProduto < 0) {
            throw new QuantidadeProdutoNegativaException(id);
        }
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidadeItensProduto);
            repository.save(produto);
    }

    private <T> void setIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
