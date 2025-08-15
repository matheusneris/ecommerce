package com.shopping.ecommerce.produto;

import com.shopping.ecommerce.exception.produtoexception.ProdutoNaoEncontradoException;
import com.shopping.ecommerce.exception.produtoexception.ProdutoSemEstoqueSuficienteException;
import com.shopping.ecommerce.exception.produtoexception.QuantidadeProdutoNegativaException;
import com.shopping.ecommerce.produto.dto.request.AdicionarEstoqueDto;
import com.shopping.ecommerce.produto.dto.request.ProdutoRequestAlterarDto;
import com.shopping.ecommerce.produto.dto.request.ProdutoRequestSalvarDto;
import com.shopping.ecommerce.produto.dto.request.RetirarEstoqueDto;
import com.shopping.ecommerce.produto.dto.response.ProdutoResponseDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public Produto consultarProdutoPorId(Long id){
        Produto produto = repository.findById(id).orElseThrow(
                () -> new ProdutoNaoEncontradoException(id));
        return produto;
    }

    //Usado para consulta externa de produtos
    public ProdutoResponseDto buscarProdutoPorId(Long id) {
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
    public void deletarProdutoPorId(Long id) {
        repository.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
        repository.deleteById(id);
    }

    @Transactional
    public Produto alterarProdutoPorId(Long id, ProdutoRequestAlterarDto produtoRequestAlterarDto) {
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
    public void retirarItensProdutoEstoque(List<RetirarEstoqueDto> produtos) {
        for(RetirarEstoqueDto item : produtos) {
            if(item.quantidade() < 0) {
                throw new QuantidadeProdutoNegativaException(item.idProduto());
            }
            Produto produto = repository.findById(item.idProduto())
                    .orElseThrow(() -> new ProdutoNaoEncontradoException(item.idProduto()));
            if(produto.getQuantidadeEstoque() < item.quantidade()) {
                throw new ProdutoSemEstoqueSuficienteException(produto.getNome(), produto.getId(), produto.getQuantidadeEstoque());
            } else {
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - item.quantidade());
                repository.save(produto);
            }
        }
    }

    @Transactional
    public void adicionarItensProdutoEstoque(List<AdicionarEstoqueDto> produtos) {
        for(AdicionarEstoqueDto item : produtos){
            if(item.quantidade() < 0) {
                throw new QuantidadeProdutoNegativaException(item.idProduto());
            }

            Produto produto = repository.findById(item.idProduto())
                    .orElseThrow(() -> new ProdutoNaoEncontradoException(item.idProduto()));
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + item.quantidade());
            repository.save(produto);
        }
    }

    private <T> void setIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
