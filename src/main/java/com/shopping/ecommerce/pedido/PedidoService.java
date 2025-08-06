package com.shopping.ecommerce.pedido;

import com.shopping.ecommerce.exception.PedidoNaoEncontradoException;
import com.shopping.ecommerce.exception.ProdutoNaoEncontradoException;
import com.shopping.ecommerce.exception.ProdutoSemEstoqueSuficienteException;
import com.shopping.ecommerce.exception.QuantidadeProdutoNegativaException;
import com.shopping.ecommerce.pagamento.ProcessaPagamentos;
import com.shopping.ecommerce.pagamento.producer.ProcessaPagamentosProducer;
import com.shopping.ecommerce.pedido.dto.request.ItemRequestAlterarDto;
import com.shopping.ecommerce.pedido.dto.request.ItemRequestSalvarDto;
import com.shopping.ecommerce.pedido.dto.request.PedidoRequestAlterarDto;
import com.shopping.ecommerce.pedido.dto.request.PedidoRequestSalvarDto;
import com.shopping.ecommerce.pedido.dto.response.PedidoResponseDto;
import com.shopping.ecommerce.pedido.enums.PedidoStatusEnum;
import com.shopping.ecommerce.produto.Produto;
import com.shopping.ecommerce.produto.ProdutoService;
import com.shopping.ecommerce.produto.dto.request.AdicionarEstoqueDto;
import com.shopping.ecommerce.produto.dto.request.RetirarEstoqueDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ProdutoService produtoService;
    private final ProcessaPagamentosProducer pagamentosProducer;

    public PedidoService(PedidoRepository repository, ProdutoService produtoService, ProcessaPagamentosProducer pagamentosProducer) {
        this.repository = repository;
        this.produtoService = produtoService;
        this.pagamentosProducer = pagamentosProducer;
    }

    public void processarPedido(PedidoRequestSalvarDto pedidoDto, ProcessaPagamentos pagamento) {
        criarPedido(pedidoDto, pagamento);

        pagamentosProducer.enviarPagamento(pagamento);
    }

    @Transactional
    private void criarPedido(PedidoRequestSalvarDto pedidoDto, ProcessaPagamentos pagamento) {
        Pedido pedido = pagamento.getPedido();

        popularItensPedidoSalvar(pedidoDto, pedido);

        pedido.setValorPedido(calcularValorTotalPedido(pedido));
        pedido.setDataHoraPedido(LocalDateTime.now());
        pedido.setStatusPedido(PedidoStatusEnum.PEDIDO_REALIZADO_AGUARDANDO_PAGAMENTO);
        pedido.setNomeCliente(pedidoDto.nomeCliente());
        pedido.setEmailCliente(pedidoDto.emailCliente());

        repository.save(pedido);
    }

    public Pedido buscarPedidoModelPorId(Long idPedido) {
        Pedido pedido = repository.findById(idPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(idPedido));

        return pedido;
    }

    public PedidoResponseDto buscarPedidoResponsePorId(Long idPedido) {
        Pedido pedido = repository.findById(idPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(idPedido));

        return new PedidoResponseDto(pedido);
    }

    @Transactional
    public void deletarPedidoPorId(Long idPedido) {
        repository.findById(idPedido).orElseThrow(() -> new ProdutoNaoEncontradoException(idPedido));
        repository.deleteById(idPedido);
    }

    @Transactional
    public void alterarStatusPedido(Pedido pedido , PedidoStatusEnum statusPedido) {
        pedido.setStatusPedido(statusPedido);
        repository.save(pedido);
    }

    @Transactional
    public PedidoResponseDto alterarPedidoPorId(PedidoRequestAlterarDto pedidoRequestAlterarDto) {
        Pedido pedido = repository.findById(pedidoRequestAlterarDto.idPedido())
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoRequestAlterarDto.idPedido()));

        alteraItensPedido(pedidoRequestAlterarDto, pedido);
        alterarDadosPedido(pedidoRequestAlterarDto, pedido);

        pedido.setValorPedido(calcularValorTotalPedido(pedido));
        repository.save(pedido);

        return new PedidoResponseDto(pedido);
    }

    private void alterarDadosPedido(PedidoRequestAlterarDto pedidoRequestAlterarDto, Pedido pedido) {
        setIfNotNull(pedido::setNomeCliente, pedidoRequestAlterarDto.nomeCliente());
        setIfNotNull(pedido::setEmailCliente, pedidoRequestAlterarDto.emailCliente());
    }

    private void alteraItensPedido(PedidoRequestAlterarDto pedidoRequestAlterarDto, Pedido pedido) {
        List<AdicionarEstoqueDto> produtosAdicionarEstoque = new ArrayList<>();
        List<RetirarEstoqueDto> produtosRetirarEstoque = new ArrayList<>();
        List<PedidoItem> itensPedido = pedido.getItens();

        for(ItemRequestAlterarDto itemDto : pedidoRequestAlterarDto.itens()) {
            Optional<PedidoItem> itemAlteracao = itensPedido.stream()
                    .filter(i -> i.getProduto().getId().equals(itemDto.idProduto())).findFirst();

            if(itemDto.quantidade() < 0) {
                throw new QuantidadeProdutoNegativaException(itemDto.idProduto());
            }

            if(itemAlteracao.isEmpty()){
                Produto produto = produtoService.consultarProdutoPorId(itemDto.idProduto());
                PedidoItem itemNovo = new PedidoItem();
                itemNovo.setPedido(pedido);
                itemNovo.setProduto(produto);
                itemNovo.setPrecoUnitario(produto.getPreco());
                itemNovo.setQuantidade(itemDto.quantidade());

                pedido.getItens().add(itemNovo);
                produtosRetirarEstoque.add(new RetirarEstoqueDto(produto.getId(), itemNovo.getQuantidade()));

            }else {
                if(itemDto.quantidade() == 0){
                    produtosAdicionarEstoque.add(new AdicionarEstoqueDto(itemDto.idProduto() , itemAlteracao.get().getQuantidade()));
                    pedido.getItens().remove(itemAlteracao.get());
                    continue;
                }
                Integer quantidadeAlteracao = itemAlteracao.get().getQuantidade() - itemDto.quantidade();
                if(quantidadeAlteracao < 0) {
                    produtosRetirarEstoque.add(new RetirarEstoqueDto(itemDto.idProduto(), -quantidadeAlteracao));
                }else {
                    produtosAdicionarEstoque.add(new AdicionarEstoqueDto(itemDto.idProduto(), quantidadeAlteracao));
                }
                itemAlteracao.get().setQuantidade(itemDto.quantidade());

            }
        }
        produtoService.retirarItensProdutoEstoque(produtosRetirarEstoque);
        produtoService.adicionarItensProdutoEstoque(produtosAdicionarEstoque);
    }

    private static BigDecimal calcularValorTotalPedido(Pedido pedido) {
        BigDecimal valorTotalPedido = BigDecimal.ZERO;
        
        for(PedidoItem item : pedido.getItens()){
            valorTotalPedido = valorTotalPedido.add(item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())));
        }
        
        return valorTotalPedido;
    }

    private void popularItensPedidoSalvar(PedidoRequestSalvarDto pedidoRequestSalvarDto, Pedido pedido) {
        List<RetirarEstoqueDto> produtosRetirarEstoque = new ArrayList<>();

        for(ItemRequestSalvarDto item : pedidoRequestSalvarDto.itens()){
            PedidoItem itemAdicionado = new PedidoItem();

            Produto produto = produtoService.consultarProdutoPorId(item.idProduto());

            if(item.quantidade() > produto.getQuantidadeEstoque()){
                throw new ProdutoSemEstoqueSuficienteException(produto.getNome(), produto.getId(), produto.getQuantidadeEstoque());
            }

            itemAdicionado.setProduto(produto);
            itemAdicionado.setPrecoUnitario(produto.getPreco());
            itemAdicionado.setQuantidade(item.quantidade());
            itemAdicionado.setPedido(pedido);

            pedido.getItens().add(itemAdicionado);
            produtosRetirarEstoque.add(new RetirarEstoqueDto(produto.getId(), itemAdicionado.getQuantidade()));
        }
        produtoService.retirarItensProdutoEstoque(produtosRetirarEstoque);
    }

    private <T> void setIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
