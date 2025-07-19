package com.shopping.ecommerce.pedido;

import com.shopping.ecommerce.exception.PedidoNaoEncontradoException;
import com.shopping.ecommerce.exception.ProdutoNaoEncontradoException;
import com.shopping.ecommerce.exception.ProdutoSemEstoqueSuficienteException;
import com.shopping.ecommerce.exception.QuantidadeProdutoNegativaException;
import com.shopping.ecommerce.pedido.dtos.request.ItemRequestAlterarDto;
import com.shopping.ecommerce.pedido.dtos.request.ItemRequestSalvarDto;
import com.shopping.ecommerce.pedido.dtos.request.PedidoRequestAlterarDto;
import com.shopping.ecommerce.pedido.dtos.request.PedidoRequestSalvarDto;
import com.shopping.ecommerce.pedido.dtos.response.PedidoItemResponseDto;
import com.shopping.ecommerce.pedido.dtos.response.PedidoResponseDto;
import com.shopping.ecommerce.produto.Produto;
import com.shopping.ecommerce.produto.ProdutoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ProdutoService produtoService;

    public PedidoService(PedidoRepository repository, ProdutoService produtoService) {
        this.repository = repository;
        this.produtoService = produtoService;
    }

    @Transactional
    public void criarPedido(PedidoRequestSalvarDto pedidoRequestSalvarDto) {
        Pedido pedido = new Pedido();

        popularItensPedidoSalvar(pedidoRequestSalvarDto, pedido);

        pedido.setValorPedido(calcularValorTotalPedido(pedido));
        pedido.setDataHoraPedido(LocalDateTime.now());

        repository.save(pedido);
    }

    public PedidoResponseDto buscarPedidoPorId(UUID idPedido) {
        Pedido pedido = repository.findById(idPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(idPedido));
        List<PedidoItem> itensPedido = pedido.getItens();
        PedidoResponseDto pedidoResponse = new PedidoResponseDto();

        popularResponsePedido(pedido, pedidoResponse);
        return pedidoResponse;
    }

    @Transactional
    public void deletarPedidoPorId(UUID idPedido) {
        repository.findById(idPedido).orElseThrow(() -> new ProdutoNaoEncontradoException(idPedido));
        repository.deleteById(idPedido);
    }

    @Transactional
    public PedidoResponseDto alterarPedidoPorId(UUID idPedido, PedidoRequestAlterarDto pedidoRequestAlterarDto) {
        Pedido pedido = repository.findById(idPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(idPedido));

        alteraItensPedido(pedidoRequestAlterarDto, pedido);

        pedido.setValorPedido(calcularValorTotalPedido(pedido));
        repository.save(pedido);

        PedidoResponseDto pedidoResponse = new PedidoResponseDto();
        popularResponsePedido(pedido, pedidoResponse);

        return pedidoResponse;
    }

    private void alteraItensPedido(PedidoRequestAlterarDto pedidoRequestAlterarDto, Pedido pedido) {
        List<PedidoItem> itensPedido = pedido.getItens();
        for(ItemRequestAlterarDto itemDto : pedidoRequestAlterarDto.itens()) {
            Optional<PedidoItem> itemAlteracao = itensPedido.stream()
                    .filter(i -> i.getProduto().getId().equals(itemDto.idProduto())).findFirst();

            if(itemAlteracao.isEmpty()){
                if(itemDto.quantidade() > 0){
                    Produto produto = produtoService.consultarProdutoPorId(itemDto.idProduto());
                    PedidoItem itemNovo = new PedidoItem();
                    itemNovo.setPedido(pedido);
                    itemNovo.setProduto(produto);
                    itemNovo.setPrecoUnitario(produto.getPreco());
                    itemNovo.setQuantidade(itemDto.quantidade());

                    pedido.getItens().add(itemNovo);
                    produtoService.retirarItensProdutoEstoque(produto.getId(), itemNovo.getQuantidade());
                }
            }else {
                atualizarEstoqueAlteracaoPedido(itemAlteracao.get(), itemDto);
                if(itemDto.quantidade() == 0){
                    pedido.getItens().remove(itemAlteracao.get());
                }else {
                    itemAlteracao.get().setQuantidade(itemDto.quantidade());
                }
            }
        }
    }

    private void atualizarEstoqueAlteracaoPedido(PedidoItem itemAtual, ItemRequestAlterarDto itemNovo) {
        if(itemNovo.quantidade() < 0){
            throw new QuantidadeProdutoNegativaException(itemNovo.idProduto());
        }
        Integer quantidadeAlteracao = itemAtual.getQuantidade() - itemNovo.quantidade();
        if(quantidadeAlteracao < 0){
            produtoService.retirarItensProdutoEstoque(itemAtual.getProduto().getId(), quantidadeAlteracao*(-1));
        } else if(quantidadeAlteracao > 0) {
            produtoService.adicionarItensProdutoEstoque(itemAtual.getProduto().getId(), quantidadeAlteracao);
        }
    }

    private static BigDecimal calcularValorTotalPedido(Pedido pedido) {
        BigDecimal valorTotalPedido = BigDecimal.ZERO;
        
        for(PedidoItem item : pedido.getItens()){
            valorTotalPedido = valorTotalPedido.add(item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())));
        }
        
        return valorTotalPedido;
    }

    private static void popularResponsePedido(Pedido pedido, PedidoResponseDto pedidoResponse) {
        popularItensPedidoResponse(pedido.getItens(), pedidoResponse);
        pedidoResponse.setIdPedido(pedido.getId());
        pedidoResponse.setValorPedido(pedido.getValorPedido());
        pedidoResponse.setDataHoraPedido(pedido.getDataHoraPedido());
    }
    private static void popularItensPedidoResponse(List<PedidoItem> itensPedido, PedidoResponseDto pedidoResponse) {
        for(PedidoItem item : itensPedido) {
            PedidoItemResponseDto pedidoItemResponse = new PedidoItemResponseDto();
            pedidoItemResponse.setId(item.getId());
            pedidoItemResponse.setNomeProduto(item.getProduto().getNome());
            pedidoItemResponse.setPrecoUnitario(item.getPrecoUnitario());
            pedidoItemResponse.setQuantidade(item.getQuantidade());

            pedidoResponse.getItens().add(pedidoItemResponse);
        }
    }

    private void popularItensPedidoSalvar(PedidoRequestSalvarDto pedidoRequestSalvarDto, Pedido pedido) {
        for(ItemRequestSalvarDto item : pedidoRequestSalvarDto.itens()){
            PedidoItem itens = new PedidoItem();

            Produto produto = produtoService.consultarProdutoPorId(item.idProduto());

            if(item.quantidade() > produto.getQuantidadeEstoque()){
                throw new ProdutoSemEstoqueSuficienteException(produto.getNome(), produto.getId(), produto.getQuantidadeEstoque());
            }

            itens.setProduto(produto);
            itens.setPrecoUnitario(produto.getPreco());
            itens.setQuantidade(item.quantidade());
            itens.setPedido(pedido);

            pedido.getItens().add(itens);

            produtoService.retirarItensProdutoEstoque(item.idProduto(), item.quantidade());
        }
    }
}
